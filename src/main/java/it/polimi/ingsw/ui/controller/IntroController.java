package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IntroController {
    private ClientHandler handler;
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
        String hostName;
        int port;
        String username;
        ConnectionType connType;

        if ("".equals(hostField.getText())) {
            status.setText("Please choose a server");
            return;
        }
        try {
            InetAddress.getByName(hostField.getText());
            hostName = hostField.getText();
        } catch (UnknownHostException e) {
            status.setText("Invalid server address");
            return;
        }

        try {
            port = Integer.valueOf(portField.getText());
        } catch (NumberFormatException e) {
            status.setText("Invalid port number");
            return;
        }

        if ("".equals(nameField.getText())) {
            status.setText("Please choose a username");
            return;
        }
        username = nameField.getText();

        if (toggleGroup.getSelectedToggle() == null) {
            status.setText("Please choose a connection type");
            return;
        }
        connType = (socketToggle.isSelected()) ? ConnectionType.SOCKET : ConnectionType.RMI;

        status.setText("Trying login...");
        try {
            handler.handleLogin(hostName, port, username, connType);
        } catch (IOException e) {
            status.setText("Connection error: " + e.getMessage());
        }
    }

    public void setHandler(ClientHandler handler) {
        this.handler = handler;
    }
}
