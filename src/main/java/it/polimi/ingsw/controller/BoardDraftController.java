package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.rmi.RemoteException;

public class BoardDraftController {
    private ClientInterface client;

    @FXML
    private ListView<ClientInterface> playersListView;

    @FXML
    void endTurn(ActionEvent event) {

    }

    @FXML
    void sendMove(ActionEvent event) {

    }

    public void bindUI() {
        try {
            playersListView.itemsProperty().bind(client.getClients());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void init(ClientInterface client) {
        this.client = client;
    }
}
