package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.SocketClient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class BoardController {
    private SocketClient client;
    private WindowPattern chosenWindowPattern;
    private Stage currentDialog;

    public void showPatternCardChooser(List<? extends Card> cards) {

        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(10, 10, 10, 10));

        Label title = new Label("Choose one of the following patterns:");
        bPane.setTop(title);

        Button btn = new Button("Confirm choice");
        btn.setDisable(true);

        Parent[][] emptyWindowPatterns = new Parent[2][2];
        FXMLLoader[][] loaders = new FXMLLoader[2][2];
        WindowPatternController[][] controllers = new WindowPatternController[2][2];
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                try {
                    loaders[i][j] = new FXMLLoader(getClass().getClassLoader().getResource("views/windowpattern.fxml"));
                    emptyWindowPatterns[i][j] = loaders[i][j].load();
                    controllers[i][j] = loaders[i][j].getController();
                    if (j == 0) {
                        controllers[i][j].setWindowPattern(((PatternCard) cards.get(i)).getFront());
                    } else {
                        controllers[i][j].setWindowPattern(((PatternCard) cards.get(i)).getBack());
                    }
                    GridPane.setConstraints(emptyWindowPatterns[i][j], j, i);
                    gridPane.add(emptyWindowPatterns[i][j], j, i);
                    emptyWindowPatterns[i][j].setId("fxWindowPattern" + i + j);
                    emptyWindowPatterns[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            for (Parent[] row : emptyWindowPatterns) {
                                for (Parent e : row) {
                                    e.getStyleClass().remove("chosen");
                                }
                            }
                            int i = GridPane.getRowIndex((VBox) event.getSource());
                            int j = GridPane.getColumnIndex((VBox) event.getSource());
                            ((VBox) event.getSource()).getStyleClass().add("chosen");
                            chosenWindowPattern = controllers[i][j].getWindowPattern();
                            btn.setDisable(false);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        bPane.setCenter(gridPane);

        Scene scene = new Scene(bPane);
        scene.getStylesheets().add("windowpattern.css");
        Stage root = new Stage(StageStyle.UNDECORATED);
        root.initModality(Modality.APPLICATION_MODAL);
        root.setScene(scene);

        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                client.validatePatternCard(chosenWindowPattern);
            }
        });
        bPane.setBottom(btn);

        currentDialog = root;
        root.showAndWait();
    }

    private void showPlayersDialog() {
        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Waiting for players...");
        bPane.setTop(label);

        TextArea txtArea = new TextArea();
        try {
            if (client.getClients().size() > 0) {
                for (ClientInterface c : client.getClients()) {
                    txtArea.appendText(c.getName() + "\n");
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        bPane.setCenter(txtArea);

        Scene scene = new Scene(bPane);
        Stage clientsDialog = new Stage(StageStyle.UNDECORATED);
        clientsDialog.initModality(Modality.APPLICATION_MODAL);
        clientsDialog.setScene(scene);

        client.getClients().addListener(new WeakListChangeListener<>(new ListChangeListener<ClientInterface>() {
            @Override
            public void onChanged(Change<? extends ClientInterface> c) {
                while (c.next()) {
                    txtArea.setText("");
                    for (ClientInterface cl : c.getList()) {
                        try {
                            txtArea.appendText(cl.getName() + "\n");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }));

        currentDialog = clientsDialog;
        clientsDialog.showAndWait();
    }

    private void hideCurrentDialog() {
        if (currentDialog != null) {
            currentDialog.hide();
        }
    }

    public void init(SocketClient client) {
        this.client = client;

        client.gameStatusProperty().addListener(new WeakChangeListener<>(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (newValue) {
                    case "WAITING_PLAYERS":
                        hideCurrentDialog();
                        showPlayersDialog();
                        break;
                    case "STARTED":
                        hideCurrentDialog();
                        break;
                    case "WAITING_PATTERNCARD":
                        hideCurrentDialog();
                        showPatternCardChooser(client.getPatternCards());
                        break;
                    default:
                        System.err.println("unrecognized GameStatus: " + newValue);
                }
            }
        }));
    }
}
