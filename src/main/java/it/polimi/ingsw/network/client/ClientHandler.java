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

    public void setPlayerToProxyModel(String name){
        proxyModel.setPlayer(new Player(name));
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
    public void setChosenPatternCard(WindowPattern w) throws IOException {
        client.validatePatternCard(w);

    }

    public void initPlayer(String name, String windowPatternName) {
        for (Player p : proxyModel.getPlayers()){
            if (p.getName().equals(name)) {
                p.getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(windowPatternName));
            }
        }
        boolean finish= true;
        for (Player p : proxyModel.getPlayers()){
            if(!p.getName().equals(proxyModel.getMyself().getName()) && p.getPlayerWindow().getWindowPattern()==null){
                finish = false;
            }

        }
        if (finish){
            System.out.println("Everybody has chosen theirs patternCards ");
            //TODO:call showOpponentsPatternCards method
        }
    }

    public void initPatternCard(String name){
        proxyModel.getMyself().getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(name));
    }
}

