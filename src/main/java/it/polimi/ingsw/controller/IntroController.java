package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.ServerInterface;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.rmi.Naming;

public class IntroController {
    private Stage selfStage;

    private StringProperty hostName = new SimpleStringProperty();
    private IntegerProperty port = new SimpleIntegerProperty();
    private BooleanProperty socket = new SimpleBooleanProperty();
    private StringProperty username = new SimpleStringProperty();

    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private TextField hostField;

    @FXML
    private TextField portField;

    @FXML
    private TextField nameField;

    @FXML
    private Toggle socketToggle;

    @FXML
    private Button connectBtn;

    @FXML
    void handleConnect(ActionEvent event) {
        //TODO check fields are not empty
        /*if (hostField.getText().length() == 0 || nameField.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initStyle(StageStyle.UTILITY);
            alert.setTitle("Invalid server hostname");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid server hostname");
            alert.showAndWait();
            return;
        }*/

        ClientInterface client;
        ServerInterface server;

        try {
            //client = new RMIClient();
            server = (ServerInterface) Naming.lookup("rmi://"  + hostName.get() + "/sagrada");
            if(server.login(username.get())){
                System.out.println("Login accepted");
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/boarddraft.fxml"));
                Stage boardStage = new Stage();
                boardStage.setTitle("Board stage");
                boardStage.setScene(new Scene(root));
                boardStage.show();
                selfStage.hide();
            } else {
                System.err.println("Login failed");
            }
        }catch (Exception e ){
            e.printStackTrace();
        }

        /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(String.format("Connecting on %s:%s as %s via socket? %s", hostName.get(), port.get(), username.get(), socket.get()));
        alert.showAndWait();*/
    }

    public void bindUI() {
        nameField.textProperty().bindBidirectional(username);
        hostField.textProperty().bindBidirectional(hostName);
        portField.textProperty().bindBidirectional(port, new NumberStringConverter());
        socket.bindBidirectional(socketToggle.selectedProperty());
    }

    public void setSelfStage(Stage selfStage) {
        this.selfStage = selfStage;
    }
}
