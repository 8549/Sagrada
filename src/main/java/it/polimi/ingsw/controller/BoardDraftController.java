package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientInterface;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.rmi.RemoteException;

public class BoardDraftController {
    private ClientInterface client;

    @FXML
    private TextArea txtArea;

    @FXML
    void endTurn(ActionEvent event) {

    }

    @FXML
    void sendMove(ActionEvent event) {

    }

    public void bindUI() {
        try {
            client.getClients().addListener(new WeakListChangeListener<>(new ListChangeListener<ClientInterface>() {
                @Override
                public void onChanged(Change<? extends ClientInterface> c) {
                    while (c.next()) {
                        if (c.wasAdded()) {
                            for (ClientInterface client : c.getAddedSubList()) {
                                try {
                                    txtArea.appendText(client.getName() + " logged in\n");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (c.wasRemoved()) {
                            for (ClientInterface client : c.getRemoved()) {
                                try {
                                    txtArea.appendText(client.getName() + " logged out\n");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }));
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void init(ClientInterface client) {
        this.client = client;
        try {
            for (ClientInterface c : client.getClients()) {
                txtArea.appendText(c.getName() + "\n");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
