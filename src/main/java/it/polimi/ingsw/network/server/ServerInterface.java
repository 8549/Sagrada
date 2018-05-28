package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {

    public void start(String[] args) throws IOException;
    public void ping() throws RemoteException;
    public boolean login(ClientWrapper client) throws RemoteException;
    public boolean updateOtherServer() throws RemoteException;
    public void notifyClients() throws RemoteException;
    public void showClients() throws  RemoteException;
    public ObservableList<ClientWrapper> getClientsConnected() throws RemoteException;
    public void removeClient(ClientWrapper c) throws RemoteException;
    public boolean checkTimer() throws RemoteException;
    public void sendPlayers(ObservableList<Player> players) throws RemoteException;
    public void choosePatternCard(List<PatternCard> choices, Player player)throws RemoteException;
}
