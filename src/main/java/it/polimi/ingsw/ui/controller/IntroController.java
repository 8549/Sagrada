package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.ui.GameProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

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
    private Toggle rmiToggle;

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

        SocketClient client = null;

        if (socket) {
            //connect with socket
            try {
                client = new SocketClient();
                client.connect(hostName, port, userName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            //Connect RMI
            try {
                //TODO: check all clients different ports
                //client = new RMIClient(userName, hostName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Login procedure
        //try {
            client.login();
            connectBtn.setDisable(true);
            launchBoard(client);
        //} catch (RemoteException e) {
        //   e.printStackTrace();
        //}


    }

    public void initUI(List<String> parameters) {
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            boolean portDisabled = !newValue.equals(socketToggle);
            portField.setDisable(portDisabled);
        });
        Properties props = GameProperties.getFromFileOrCli(parameters);
        if (props.containsKey(GameProperties.USERNAME_KEY)) {
            nameField.setText(props.getProperty(GameProperties.USERNAME_KEY));
        }
        if (props.containsKey(GameProperties.HOSTNAME_KEY)) {
            hostField.setText(props.getProperty(GameProperties.HOSTNAME_KEY));
        }
        if (props.containsKey(GameProperties.PORT_KEY)) {
            portField.setText(props.getProperty(GameProperties.PORT_KEY));
        }
        if (props.containsKey(GameProperties.CONNECTION_KEY)) {
            if (props.getProperty(GameProperties.CONNECTION_KEY).equalsIgnoreCase("socket")) {
                socketToggle.setSelected(true);
            } else if (props.getProperty(GameProperties.CONNECTION_KEY).equalsIgnoreCase("rmi")) {
                rmiToggle.setSelected(true);
            }
        }

    }

    public void setSelfStage(Stage selfStage) {
        this.selfStage = selfStage;
    }

    private void launchBoard(SocketClient client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/main.fxml"));
            Parent root = loader.load();
            BoardController boardController = loader.getController();
            boardController.init(client);
            // boardController.bindUI();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("board.css");
            selfStage.setTitle("Sagrada - " + client.getName());
            selfStage.setScene(scene);
            selfStage.sizeToScene();
            selfStage.centerOnScreen();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("GUI Error");
            alert.setContentText("Couldn't load GUI");
            alert.showAndWait();
        }
    }
}
