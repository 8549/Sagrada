package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUI extends Application implements UI {

    @Override
    public void start(Stage primaryStage) {
        URL url = getClass().getClassLoader().getResource("views/intro.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = null;
        try {
            root = loader.load();
            primaryStage.setTitle("Sagrada - Connection");
            primaryStage.setScene(new Scene(root));
            loader.setController(this);
            primaryStage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Couldn't load GUI file");
            alert.setContentText(null);
            alert.showAndWait();
            Platform.exit();
        }
    }

    @Override
    public void failedLogin(String msg) {

    }

    @Override
    public void showLogin() {

    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {

    }

    @Override
    public void showLoggedInUsers() {

    }

    @Override
    public void setProxyModel(ProxyModel model) {

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
