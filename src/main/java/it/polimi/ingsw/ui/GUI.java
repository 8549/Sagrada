package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.ui.controller.IntroController;
import it.polimi.ingsw.ui.controller.MainController;
import it.polimi.ingsw.ui.controller.WindowPatternController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application implements UI {
    public static final double TILE_SIZE = 70;
    private Stage stage;
    private WindowPattern selected;
    private IntroController introController;
    private MainController mainController;
    private ClientHandler handler;
    private ProxyModel model;

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/intro.fxml"));
        try {
            stage = primaryStage;
            handler = new ClientHandler(this);
            //handler = RunClient.getClientHandler();
            primaryStage.setTitle("Sagrada - Connection");
            Parent root = loader.load();
            introController = loader.getController();
            introController.setSelfStage(primaryStage);
            introController.setGui(this);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Couldn't load GUI");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }
    }

    @Override
    public void failedLogin() {
        Platform.runLater(() -> {
            introController.failedLogin();
        });
    }

    @Override
    public void showLogin() {
        // Intentionally left blank
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {
        Platform.runLater(() -> {
            stage.setTitle("Sagrada - Please choose your pattern card");
            Button confirm = new Button("Confirm");
            confirm.setDisable(true);
            VBox main = new VBox();
            main.setAlignment(Pos.CENTER);
            HBox box = new HBox();
            box.setSpacing(10);
            WindowPattern[] patterns = {one.getFront(), one.getBack(), two.getFront(), two.getBack()};
            Parent[] containers = new Parent[patterns.length];
            WindowPatternController[] controllers = new WindowPatternController[patterns.length];
            int i = 0;
            for (WindowPattern p : patterns) {
                FXMLLoader patternLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/windowpattern.fxml"));
                try {
                    containers[i] = patternLoader.load();
                    containers[i].getStylesheets().add(getClass().getClassLoader().getResource("windowpattern.css").toExternalForm());
                    controllers[i] = patternLoader.getController();
                    controllers[i].setWindowPattern(p);
                    box.getChildren().add(containers[i]);
                    int finalI = i;
                    box.getChildren().get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            confirm.setDisable(false);
                            for (Parent c : containers) {
                                c.getStyleClass().remove("chosen");
                            }
                            ((VBox) event.getSource()).getStyleClass().add("chosen");
                            selected = patterns[finalI];
                        }
                    });
                    i++;
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Couldn't load pattern card GUI");
                    alert.setContentText(e.getMessage());
                    alert.showAndWait();
                    Platform.exit();
                }
            }
            main.getChildren().add(box);
            main.getChildren().add(confirm);
            confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (selected != null) {
                        try {
                            handler.setChosenPatternCard(selected);
                        } catch (IOException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText("Error while sending chosen pattern card. Please try again");
                            alert.setContentText(e.getMessage());
                            alert.showAndWait();
                        }
                    }
                }
            });
            stage.setScene(new Scene(main));
            stage.sizeToScene();
            stage.centerOnScreen();
        });
    }

    @Override
    public void showLoggedInUsers() {
        Platform.runLater(() -> {
            stage.setTitle("Sagrada - Welcome to the game room");
            model = handler.getModel();
            introController.showLoggedInUsers();
        });
    }

    @Override
    public void setProxyModel(ProxyModel model) {
        Platform.runLater(() -> {
            this.model = model;
        });
    }

    @Override
    public ClientHandler getClientHandler() {
        return handler;
    }

    @Override
    public ProxyModel getModel() {
        return model;
    }

    @Override
    public void initUI() {
        launch();
    }

    @Override
    public void startGame() {
    }

    @Override
    public void update() {

    }

    @Override
    public void myTurnStarted() {

    }

    @Override
    public void myTurnEnded() {

    }

    @Override
    public void playerDisconnected(Player p) {

    }

    @Override
    public void initBoard() {
        Platform.runLater(() -> {
            stage.setTitle("Sagrada");
            FXMLLoader boardLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/main.fxml"));
            try {
                Parent root = boardLoader.load();
                root.getStylesheets().add(getClass().getClassLoader().getResource("die.css").toExternalForm());
                root.getStylesheets().add(getClass().getClassLoader().getResource("board.css").toExternalForm());
                mainController = boardLoader.getController();
                mainController.setGUI(this);
                mainController.update();
                // Init listeners
                model.getDraftPool().addListener(new WeakListChangeListener<>(new ListChangeListener<Die>() {
                    @Override
                    public void onChanged(Change<? extends Die> c) {
                        while (c.next()) {
                            update();
                        }
                    }
                }));

                stage.setScene(new Scene(root));
                stage.sizeToScene();
                stage.centerOnScreen();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error while loading the main game GUI");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                Platform.exit();
            }
        });
    }

    @Override
    public boolean isGUI() {
        return true;
    }

    @Override
    public void wrongMove() {

    }

    @Override
    public void chooseDieFromWindowPattern() {

    }

    @Override
    public void chooseDieFromDraftPool() {

    }

    @Override
    public void chooseDieFromRoundTrack() {

    }

    @Override
    public void chooseIfDecrease() {

    }

    @Override
    public void chooseIfPlaceDie() {

    }

    @Override
    public void chooseToMoveOneDie() {

    }

    @Override
    public void setValue() {

    }

    @Override
    public void setOldCoordinates() {

    }

    @Override
    public void setNewCoordinates() {

    }

}
