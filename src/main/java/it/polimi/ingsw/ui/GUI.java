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
    private Stage stage;
    private IntroController introController;
    private ClientHandler handler;
    private ProxyModel model;

    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/intro.fxml"));
        try {
            stage = primaryStage;
            primaryStage.setTitle("Sagrada - Connection");
            Parent root = loader.load();
            introController = loader.getController();
            introController.setHandler(RunClient.getClientHandler());
            introController.setSelfStage(primaryStage);
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
        introController.failedLogin();
    }

    @Override
    public void showLogin() {
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {

    }

    @Override
    public void showLoggedInUsers() {
        introController.setModel(model);
        introController.showLoggedInUsers();
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
        handler = ch;
    }

}
