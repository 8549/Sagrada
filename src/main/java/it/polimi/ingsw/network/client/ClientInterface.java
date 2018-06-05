package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.WindowPattern;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote {
    String getName() throws RemoteException;

    void login() throws RemoteException;

    void connect(String serverAddress, int portNumber, String userName) throws RemoteException, IOException;
    void validatePatternCard(WindowPattern w);
}
