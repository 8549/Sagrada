package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import javafx.application.Application;
import javafx.stage.Stage;

public class GUI extends Application implements UI {

    @Override
    public void start(Stage primaryStage) {
        /*URL url = getClass().getClassLoader().getResource("views/intro.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        primaryStage.setTitle("Sagrada - Connection");
        primaryStage.setScene(new Scene(root));
        IntroController controller = loader.getController();
        controller.setSelfStage(primaryStage);
        controller.initUI(getParameters().getRaw());
        primaryStage.show();*/
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
    public void launch() {

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
}
