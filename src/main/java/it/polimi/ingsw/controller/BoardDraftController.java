package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.ClientInterface;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                                    log(client.getName() + " logged in");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (c.wasRemoved()) {
                            for (ClientInterface client : c.getRemoved()) {
                                try {
                                    log(client.getName() + " logged out");
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
            if (client.getClients().size() > 0) {
                log("Already logged in clients:");
            }
            for (ClientInterface c : client.getClients()) {
                log(c.getName());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void log(String msg) {
        String time = DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now());
        txtArea.appendText("[" + time + "] " + msg + "\n");
    }
}
