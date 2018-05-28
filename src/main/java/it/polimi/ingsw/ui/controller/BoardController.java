package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.client.ClientInterface;
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
    private ClientInterface client;
    private WindowPattern chosenWindowPattern;

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
                //TODO SEND CHOSEN WINDOW PATTERN
                root.hide();
            }
        });
        bPane.setBottom(btn);

        root.showAndWait();
    }

    /*public void bindUI() {
        try {
            client.getClients().addListener(new WeakListChangeListener<>(new ListChangeListener<ClientInterface>() {
                @Override
                public void onChanged(Change<? extends ClientInterface> c) {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (ClientInterface client : c.getAddedSubList()) {
                                try {
                                    log(client.getName() + " logged in");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (c.wasRemoved()) {
                            for (ClientInterface client : c.getRemoved()) {
                                try {
                                    log(client.getName() + " logged out");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }*/

    public void init(ClientInterface client) {
        this.client = client;

        BorderPane bPane = new BorderPane();
        bPane.setPadding(new Insets(10, 10, 10, 10));

        Label label = new Label("Waiting for players...");
        bPane.setTop(label);

        TextArea txtArea = new TextArea();
        try {
            if (client.getClients().size() > 0) {
                for (ClientInterface c : client.getClients()) {
                    txtArea.appendText(c.getName());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        bPane.setCenter(txtArea);

        Scene scene = new Scene(bPane);
        Stage clientDialog = new Stage(StageStyle.UNDECORATED);
        clientDialog.initModality(Modality.APPLICATION_MODAL);
        clientDialog.setScene(scene);
        clientDialog.showAndWait();
    }
}
