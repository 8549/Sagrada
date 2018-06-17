package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.WindowPattern;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface  {
    String getName() throws IOException;

    void login() throws IOException;

    void connect(String serverAddress, int portNumber, String userName) throws IOException;
    void validatePatternCard(WindowPattern w) throws IOException;

    void requestPlacement(int number, String color, int row, int column);
}
