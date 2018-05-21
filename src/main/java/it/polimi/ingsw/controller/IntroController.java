package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.RMIClient;
import it.polimi.ingsw.network.ServerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;

public class IntroController {
    private Stage selfStage;
    private int port;
    private String userName;
    private String hostName;
    private boolean socket;

    @FXML
    private Label status;

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

        // Fields validation
        userName = nameField.getText();
        if (userName.equals("")) {
            status.setText("Please enter a username");
            return;
        }
        hostName = hostField.getText();
        if (hostName.equals("")) {
            status.setText("Please enter a hostname/IP address");
            return;
        }
        if (toggleGroup.getSelectedToggle() == null) {
            status.setText("Please choose a connection type");
            return;
        }
        try {
            port = Integer.valueOf(portField.getText());
        } catch (NumberFormatException e) {
            status.setText("Invalid port number");
            return;
        }
        socket = socketToggle.isSelected();

        ClientInterface client;
        ServerInterface server;

        if (socket) {
            //connect with socket
        } else {
            try {

                server = (ServerInterface) Naming.lookup("rmi://" + hostName + ":" + port + "/sagrada");
                client = new RMIClient(userName);

                if (server.login(client)) {
                    System.out.println("Login accepted");
                    launchBoard(client);
                } else {
                    System.err.println("Login failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void initUI() {
        //TODO load settings from file or from cli options

    }

    public void setSelfStage(Stage selfStage) {
        this.selfStage = selfStage;
        //selfStage.setOnCloseRequest(e -> Platform.exit());
    }

    private void launchBoard(ClientInterface client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/boarddraft.fxml"));
            Parent root = loader.load();
            BoardDraftController boardController = loader.getController();
            boardController.init(client);
            boardController.bindUI();
            selfStage.setScene(new Scene(root));
            selfStage.sizeToScene();
            /*Stage boardStage = new Stage();
            boardStage.setTitle("Board stage");
            boardStage.setScene(new Scene(root));
            boardStage.show();
            selfStage.hide();*/
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("GUI Error");
            alert.setContentText("Couldn't load GUI");
            alert.showAndWait();
        }
    }
}
