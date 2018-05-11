package it.polimi.ingsw;

import it.polimi.ingsw.controller.IntroController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class IntroLoader extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = getClass().getClassLoader().getResource("views/intro.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        primaryStage.setTitle("Sagrada - Connection");
        primaryStage.setScene(new Scene(root));
        IntroController controller = loader.getController();
        controller.bindUI();
        primaryStage.show();
    }
}
