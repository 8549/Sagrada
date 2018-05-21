package it.polimi.ingsw.ui.controller;

import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.RMIClient;
import it.polimi.ingsw.network.ServerInterface;
import it.polimi.ingsw.ui.GameProperties;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.Naming;
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

    public void initUI(List<String> parameters) {
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

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            boolean portDisabled = !newValue.equals(socketToggle);
            portField.setDisable(portDisabled);
        });
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
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("GUI Error");
            alert.setContentText("Couldn't load GUI");
            alert.showAndWait();
        }
    }
}
