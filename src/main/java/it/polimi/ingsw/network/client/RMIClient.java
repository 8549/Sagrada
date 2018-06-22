package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.server.RMIServerInterface;
import it.polimi.ingsw.network.server.ServerInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static it.polimi.ingsw.network.server.MainServer.DEFAULT_RMI_PORT;


public class RMIClient extends UnicastRemoteObject implements RMIClientInterface, Serializable {
    Player player;
    RMIServerInterface server;
    ClientHandler ch;

    public RMIClient(ClientHandler ch) throws RemoteException{
        this.ch = ch;
    }

    @Override
    public String getName() throws RemoteException {
        return player.getName();
    }

    @Override
    public void login() throws RemoteException {
        server.login(player,this);
    }


    @Override
    public void connect(String serverAddress, int portNumber, String userName) throws RemoteException{
        player = new Player(userName);
        Registry registry = LocateRegistry.getRegistry(serverAddress);
        try {
            server = (RMIServerInterface) registry.lookup("RMIServerInterface");
            System.out.println("[DEBUG] Client connected ");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validatePatternCard(WindowPattern w) throws RemoteException{
        server.patternCardValidation(w.getName(), this);
    }

    @Override
    public void requestPlacement(int number, String color, int row, int column) throws RemoteException{

    }

    @Override
    public void loginResponse(boolean response)throws RemoteException{
        if(response){
            System.out.println("[DEBUG] Login succesful");
            ch.setPlayerToProxyModel(player.getName());
        }else{
            System.out.println("[DEBUG] Login failed");
            ch.setLoginResponse(response);
        }


    }

    @Override
    public void addPlayersToProxy(List<Player> players) {
        ch.addPlayersToProxyModel(players);
        ch.loggedUsers();
    }

    @Override
    public void addPlayerToProxy(Player player) {
        ch.addPlayersToProxyModel(player);
    }

    @Override
    public void initPatternCardChoice(List<PatternCard> choices){
        ch.patternCardChooser(choices.get(0), choices.get(2));
    }

    @Override
    public void initGame(List<Player> p ){
        ch.handleGameStarted(p);

    }

}
