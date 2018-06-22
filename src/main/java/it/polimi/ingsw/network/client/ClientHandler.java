package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.ProxyModel;
import it.polimi.ingsw.ui.UI;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class ClientHandler implements Serializable {
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
            client.connect(hostname, port, username);
            client.login();
        }else if(connectionType.equals(ConnectionType.RMI)){
            client = new RMIClient(this);
            client.connect(hostname, port, username);
            ((RMIClient) client).run();
        }
    }


    public void loginFailed(){
        ui.failedLogin();

    }

    public void setPlayerToProxyModel(String name){
        proxyModel.setPlayer(new Player(name));
    }

    public  void loggedUsers() {
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
            ui.initBoard();
        }
    }

    public void initPatternCard(String name){
        proxyModel.getMyself().getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(name));
    }

    public void setPublicObjCard(List<PublicObjectiveCard> publicObjCards){
        proxyModel.addPubObjCards(publicObjCards);
    }

    public void setPrivateObj(String name, PrivateObjectiveCard p){
        if (name.equals(proxyModel.getMyself().getName())){
            proxyModel.getMyself().setPrivateObjectiveCard(p);
        }else {
            for (Player player : proxyModel.getPlayers()) {
                if (player.getName().equals(name)) {
                    player.setPrivateObjectiveCard(p);
                    System.out.println("[DEBUG] Player " + player.getName() + " has private " + player.getPrivateObjectiveCard().getName());
                }
            }
        }
    }

    public void setDraftPool(List<Die> draft){
        proxyModel.setDraftPool(draft);
    }

    public void handlePlacement(Die d, int row, int column) {
        try {
            client.requestPlacement(d.getNumber(),d.getColor().toString(), row, column);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyTurnStarted(String name) {
        if(proxyModel.getMyself().getName().equals(name)){
            ui.myTurnStarted();
        }else{
            proxyModel.setCurrentPlayer(new Player(name));
            System.out.println("[DEBUG] Now it's " + name + " turn");
        }
    }

    public void handleMoveResponse(boolean response){
        if(response) {
            System.out.println("[DEBUG] Server response: Correct move!");
        }else{
            System.out.println("[DEBUG] Server response: Wrong Move!");
        }
    }
}

