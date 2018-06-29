package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.ProxyModel;
import it.polimi.ingsw.ui.UI;
import javafx.application.Platform;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.List;

public class ClientHandler implements Serializable {
    private ClientInterface client;
    private boolean loginResponse;
    private UI ui;
    private ProxyModel proxyModel;

    public ClientHandler(UI ui) {
        this.ui = ui;
        proxyModel = new ProxyModel();
        //ui.setProxyModel(this.proxyModel);
    }

    private void perform(Runnable r) {
        if (ui.isGUI()) {
            Platform.runLater(r);
        } else {
            r.run();
        }
    }

    public void handleLogin(String hostname, int port, String username, ConnectionType connectionType) throws IOException {

        if(connectionType.equals(ConnectionType.SOCKET)){
            client = new SocketClient(this);
            client.connect(hostname, port, username);
            client.login();
        }else if(connectionType.equals(ConnectionType.RMI)){
            client = new RMIClient(this);
            client.connect(hostname, port, username);
            client.login();
        }
    }


    public void loginFailed(){
        ui.failedLogin();

    }

    public void setPlayerToProxyModel(String name){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.setPlayer(new Player(name));
            }
        };
        perform(task);
    }

    public  void loggedUsers() {
        ui.showLoggedInUsers();

    }

    public void addPlayersToProxyModel(List<Player> p){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addPlayers(p);
            }
        };
        perform(task);
    }

    public void addPlayersToProxyModel(Player p){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addPlayers(p);
            }
        };
        perform(task);
    }

    public void deletePlayerFromProxyModel(Player p){
        ui.playerDisconnected(p);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.removePlayer(p);
            }
        };
        perform(task);
    }
    public void patternCardChooser(PatternCard p1, PatternCard p2){
        ui.showPatternCardsChooser(p1,p2);

    }
    public void handleGameStarted(List<Player> players, int timeout){
        ui.startGame();
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.resetPlayers(players);
                proxyModel.setTimeout(timeout);
                System.out.println("[DEBUG] TIMEOUT --> " + timeout);
            }
        };
        perform(task);
    }

    public void setChosenPatternCard(WindowPattern w) throws IOException {
        client.validatePatternCard(w);
    }

    public void initPlayer(String name, String windowPatternName) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (Player p : proxyModel.getPlayers()) {
                    if (p.getName().equals(name)) {
                        p.getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(windowPatternName));
                    }
                }
                boolean finish = true;
                for (Player p : proxyModel.getPlayers()) {
                    if (!p.getName().equals(proxyModel.getMyself().getName()) && p.getPlayerWindow().getWindowPattern() == null) {
                        finish = false;
                    }

                }
                if (finish) {
                    System.out.println("Everybody has chosen theirs patternCards ");
                    ui.initBoard();
                }
            }
        };
        perform(task);
    }

    public void initPatternCard(String name){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.getMyself().getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(name));
            }
        };
        perform(task);
    }

    public void setPublicObjCard(List<PublicObjectiveCard> publicObjCards){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addPubObjCards(publicObjCards);
            }
        };
        perform(task);
    }

    public void setTools(List<ToolCard> tools){
        proxyModel.addToolCard(tools);
    }
    public void setPrivateObj(String name, ObjCard p) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (name.equals(proxyModel.getMyself().getName())) {
                    proxyModel.getMyself().setPrivateObjectiveCard(p);
                } else {
                    for (Player player : proxyModel.getPlayers()) {
                        if (player.getName().equals(name)) {
                            player.setPrivateObjectiveCard(p);
                            System.out.println("[DEBUG] Player " + player.getName() + " has private " + player.getPrivateObjectiveCard().getName());
                        }
                    }
                }
            }
        };
        perform(task);
    }

    public ProxyModel getModel() {
        return proxyModel;
    }

    public void setDraftPool(List<Die> draft){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.setDraftPool(draft);
            }
        };
        perform(task);
    }

    public void handlePlacement(Die d, int row, int column) {
        try {
            client.requestPlacement(d.getNumber(),d.getColor().toString(), row, column);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyTurnStarted(String name, int round, int turn) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                boolean myTurn= false;
                if (proxyModel.getMyself().getName().equals(name)) {
                    proxyModel.setCurrentPlayer(proxyModel.getMyself());
                    myTurn = true;
                } else {
                    proxyModel.setCurrentPlayer(proxyModel.getByName(name));
                    System.out.println("[DEBUG] Now it's " + name + " turn");
                }
                proxyModel.setCurrentRound(round);
                proxyModel.setCurrentTurn(turn);
                if(myTurn){
                    ui.myTurnStarted();
                }

            }
        };
        perform(task);
    }

    public void handleMoveResponse(boolean response){
        if(response) {
            System.out.println("[DEBUG] Server response: Correct move!");
        }else{
            System.out.println("[DEBUG] Server response: Wrong Move!");
        }
    }

    public void moveTimeIsOut(){

        ui.myTurnEnded();
    }

    public void endTurn(String name){
        if(proxyModel.getMyself().equals(name)){
            //code to stop selection if client is doing stuff
            System.out.println("Your turn is ended ");
        }else{
            System.out.println("Player " + name + " has finished his/her turn");
        }
    }
}

