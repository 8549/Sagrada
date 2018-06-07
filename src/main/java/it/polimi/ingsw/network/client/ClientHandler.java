package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.CardsDeck;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.ProxyModel;
import it.polimi.ingsw.ui.UI;

import java.io.IOException;
import java.util.List;

public class ClientHandler {
    private ClientInterface client;
    private boolean loginResponse;
    private UI ui;
    ProxyModel proxyModel;

    public ClientHandler(UI ui) {
        this.ui = ui;
        proxyModel = new ProxyModel();
        ui.setProxyModel(this.proxyModel);
    }

    public void handleLogin(String hostname, int port, String username, ConnectionType connectionType) throws IOException {

        if(connectionType.equals(ConnectionType.SOCKET)){
            client = new SocketClient(this);
        }else if(connectionType.equals(ConnectionType.RMI)){
            client = new RMIClient(this);

        }

        client.connect(hostname, port, username);
        client.login();
    }


    public void setLoginResponse(boolean loginResponse){
        this.loginResponse = loginResponse;
    }

    public  void loginSuccessful() {
        ui.showLoggedInUsers();

    }

    public void addPlayersToProxyModel(List<Player> p){
        while(proxyModel==null){}
        proxyModel.addPlayers(p);
    }
    public void addPlayersToProxyModel(Player p){
        while(proxyModel==null){}
        proxyModel.addPlayers(p);

    }

    public void deletePlayerFromProxyModel(Player p){
        ui.playerDisconnected(p);
        proxyModel.removePlayer(p);
    }
    public void patternCardChooser(PatternCard p1, PatternCard p2){
        ui.showPatternCardsChooser(p1,p2);

    }
    public void handleGameStarted(List<Player> players){
        ui.startGame();
        proxyModel.resetPlayers(players);


    }
    public void setChosenPatternCard(WindowPattern w){
        client.validatePatternCard(w);

    }

    public void initPlayer(String name, String windowPatternName) {
        for (Player p : proxyModel.getPlayers()){
            CardsDeck.getWindowPatternByName(windowPatternName);
            //Caricare WindowPattern con nome "patternCard" al player p
        }
    }
}

