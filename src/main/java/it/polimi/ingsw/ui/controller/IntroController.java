package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.GUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IntroController {
    private GUI gui;
    private Stage selfStage;

    @FXML
    private BorderPane bordPane;

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

    /**
     * This method checks and validates all the user inputs before trying to connect via the network layer
     *
     * @param event mouse event
     */
    @FXML
    void handleConnect(ActionEvent event) {
        String hostName;
        int port = 0;
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

        if (socketToggle.isSelected()) {
            try {
                port = Integer.valueOf(portField.getText());
            } catch (NumberFormatException e) {
                status.setText("Invalid port number");
                return;
            }
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
        connectBtn.setDisable(true);
        status.setText("Trying login...");
        gui.getClientHandler().handleLogin(hostName, port, username, connType);

    }

    /**
     * Initializes the required listeners for this window to work
     */
    public void initListeners() {
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.equals(rmiToggle)) {
                    portField.setDisable(true);
                } else {
                    portField.setDisable(false);
                }
            }
        });
    }

    public void setSelfStage(Stage selfStage) {
        this.selfStage = selfStage;
    }

    public void setGui(GUI gui) {
        this.gui = gui;
    }

    /**
     * Handles a failed login
     */
    public void failedLogin() {
        status.setText("Login failed, please retry!");
        connectBtn.setDisable(false);
    }

    /**
     * This method loads the new scene with a self-updating {@link ListView} containing all the currently logged in players
     */
    public void showLoggedInUsers() {
        status.setText("Login successful!");
        connectBtn.setVisible(false);
        ListView<Player> listView = new ListView<>(gui.getModel().getPlayers());
        listView.setPrefWidth(200);
        listView.setPrefHeight(200);
        listView.setEditable(false);
        listView.setOrientation(Orientation.VERTICAL);
        bordPane.setCenter(listView);
        selfStage.sizeToScene();
        selfStage.centerOnScreen();
    }
}
