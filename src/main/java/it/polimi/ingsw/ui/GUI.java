package it.polimi.ingsw.ui;

import it.polimi.ingsw.RunClient;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.ui.controller.IntroController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application implements UI {
    private Stage currentStage;
    private IntroController controller;
    private ClientHandler handler;
    private ProxyModel model;

    @Override
    public void init() {
        handler = RunClient.getClientHandler();
    }

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/intro.fxml"));
        try {
            Parent root = loader.load();
            primaryStage.setTitle("Sagrada - Connection");
            primaryStage.setScene(new Scene(root));
            controller = loader.getController();
            controller.setHandler(handler);
            currentStage = primaryStage;
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

    }

    @Override
    public void showLogin() {
        // Intentionally left blank
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {

    }

    @Override
    public void showLoggedInUsers() {

    }

    @Override
    public void setProxyModel(ProxyModel model) {
        this.model = model;
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

    }

    @Override
    public void setHandler(ClientHandler ch) {
    }

}
