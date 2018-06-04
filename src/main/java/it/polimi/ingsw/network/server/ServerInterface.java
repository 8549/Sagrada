package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import javafx.collections.ObservableList;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInterface extends Remote {

    public void start() throws IOException;

}
