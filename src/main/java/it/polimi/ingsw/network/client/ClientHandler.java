package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.ProxyModel;
import it.polimi.ingsw.ui.UI;
import javafx.application.Platform;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ClientHandler implements Serializable {
    private ClientInterface client;
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

    public void handleLogin(String hostname, int port, String username, ConnectionType connectionType)  {
        try {
            if (connectionType.equals(ConnectionType.SOCKET)) {
                client = new SocketClient(this);
                client.connect(hostname, port, username);
                client.login();
            } else if (connectionType.equals(ConnectionType.RMI)) {
                client = new RMIClient(this);
                client.connect(hostname, port, username);
                client.login();
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }


    public void loginFailed() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.failedLogin();
            }
        };
        perform(task);
    }

    public void setPlayerToProxyModel(String name) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.setPlayer(new Player(name));
            }
        };
        perform(task);
    }

    public void loggedUsers() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.showLoggedInUsers();
            }
        };
        perform(task);
    }

    public void addPlayersToProxyModel(List<Player> p) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addPlayers(p);
            }
        };
        perform(task);
    }

    public void addPlayersToProxyModel(Player p) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addPlayers(p);
            }
        };
        perform(task);
    }

    public void deletePlayerFromProxyModel(Player p) {
        ui.playerDisconnected(p);
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.removePlayer(p);
            }
        };
        perform(task);
    }

    public void patternCardChooser(PatternCard p1, PatternCard p2) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.showPatternCardsChooser(p1, p2);
            }
        };
        perform(task);
    }

    public void handleGameStarted(List<Player> players, int timeout) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if(proxyModel.getMyself().getStatus().equals(PlayerStatus.RECONNECTED)){
                    proxyModel.resetPlayers(players);
                    ui.setModelAfterReconnecting(proxyModel);
                }else{
                    ui.startGame();
                    proxyModel.resetPlayers(players);
                    proxyModel.setTimeout(timeout);
//                    System.out.println("[DEBUG] TIMEOUT --> " + timeout);
                }
            }
        };
        perform(task);


    }

    public void setChosenPatternCard(WindowPattern w) {
        try {
            client.validatePatternCard(w);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void initPlayer(String name, String windowPatternName) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (Player p : proxyModel.getPlayers()) {
                    if (p.getName().equals(name)) {
                        p.getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(windowPatternName));
                        p.setInitialTokens();
                    }
                }
                boolean finish = true;
                for (Player p : proxyModel.getPlayers()) {
                    if (!p.getName().equals(proxyModel.getMyself().getName()) && p.getPlayerWindow().getWindowPattern() == null) {
                        finish = false;
                    }

                }
                if (finish && !proxyModel.getMyself().getStatus().equals(PlayerStatus.RECONNECTED)) {
//                    System.out.println("[DEBUG] Everybody has chosen theirs patternCards ");
                    ui.initBoard();
                }
            }
        };
        perform(task);
    }

    public void initPatternCard(String name) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.getMyself().getPlayerWindow().setWindowPattern(CardsDeck.getWindowPatternByName(name));
                proxyModel.getMyself().setInitialTokens();
            }
        };
        perform(task);
    }

    public void setPublicObjCard(List<PublicObjectiveCard> publicObjCards) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addPubObjCards(publicObjCards);
            }
        };
        perform(task);
    }

    public void setTools(List<ToolCard> tools) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addToolCard(tools);
            }
        };
        perform(task);
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
//                            System.out.println("[DEBUG] Player " + player.getName() + " has private " + player.getPrivateObjectiveCard().getName());
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

    public void setDraftPool(List<Die> draft) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.setDraftPool(draft);
//                System.out.println("[DEBUG] Draft updated" );

            }
        };
        perform(task);
    }

    public void handlePlacement(Die d, int row, int column) {
        try {
            client.requestPlacement(d.getNumber(), d.getColor().toString(), row, column);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }

    }

    public void notifyTurnStarted(String name, int round, int turn) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                boolean myTurn = false;

                if (proxyModel.getMyself().getName().equals(name)) {
                    proxyModel.setCurrentPlayer(proxyModel.getMyself());
                    myTurn = true;
                } else {
                    proxyModel.setCurrentPlayer(proxyModel.getByName(name));
//                    System.out.println("[DEBUG] Now it's " + name + " turn");
                }
                proxyModel.setCurrentRound(round);
                proxyModel.setCurrentTurn(turn);
                if (myTurn) {
                    ui.myTurnStarted();
                }


            }
        };
        perform(task);
    }

    public void handleMoveResponse(String name, boolean response, Die d, int row, int column){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                Player player = null;
                if (name.equals(proxyModel.getMyself().getName())) {
                    player = proxyModel.getMyself();
                } else {
                    for (Player p : proxyModel.getPlayers()) {
                        if (p.getName().equals(name)) {
                            player = p;
                        }
                    }
                }
                if (response) {
                    System.out.println("[DEBUG] Server response: Correct move!");
                    player.getPlayerWindow().addDie(d, row, column);
                } else {
                    System.out.println("[DEBUG] Server response: Wrong Move of player : " + name);
                    if(proxyModel.getMyself().getName().equals(name)){
                        ui.wrongMove();
                    }
                }
            }
        };
        perform(task);
    }

    public void moveTimeIsOut(Player p) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.myTurnEnded();
            }
        };
        perform(task);
    }

    public void endTurn(String name){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (proxyModel.getMyself().equals(name)) {
                    System.out.println("[DEBUG] Your turn is ended ");
                    ui.myTurnEnded();
                } else {
                    System.out.println("[DEBUG] Player " + name + " has finished his/her turn");
                }
            }
        };
        perform(task);
    }

    public void passTurn() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                new Thread(){
                    @Override
                    public void run(){
                        try {
                            client.passTurn();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        };
        perform(task);

    }

    public void useTool(ToolCard tool) {
        try {
            client.requestTool(tool);
        } catch (IOException e) {
            e.printStackTrace();
            handleDisconnection();
        }
    }

    public void endRound(List<Die> dice) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.addDiceToRoundTrack(dice);
                ui.update();
            }
        };
        perform(task);
    }

    public void chooseDieFromWindowPattern() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.chooseDieFromWindowPattern();
            }
        };
        perform(task);
    }

    public void sendDieFromWP(Die d, int row, int column) {
        try {
            client.sendDieFromWP(d, row, column);
        } catch (IOException e) {
            e.printStackTrace();
            handleDisconnection();
        }
    }

    public void chooseDieFromDraftPool() {
        perform(() -> ui.chooseDieFromDraftPool());
    }

    public void sendDieFromDP(Die d) {
        try {
            client.sendDieFromDP(d);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void chooseDieFromRoundTrack() {
        perform(() -> ui.chooseDieFromRoundTrack());
    }

    public void sendDieFromRT(Die d, int round) {
        try {
            client.sendDieFromRT(d, round);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void chooseIfDecrease() {
        perform(() -> ui.chooseIfDecrease());
    }

    public void sendDecreaseChoice(boolean choice) {
        try {
            client.sendDecreaseChoice(choice);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void chooseIfPlaceDie(int number) {
        perform(() -> ui.chooseIfPlaceDie(number));
    }

    public void sendPlacementChoice(boolean choice) {
        try {
            client.sendPlacementChoice(choice);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void chooseToMoveOneDie() {
        perform(() -> ui.chooseToMoveOneDie());
    }

    public void sendNumberDiceChoice(boolean choice) {
        try {
            client.sendNumberDiceChoice(choice);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void setValue(SagradaColor color) {
        perform(() -> ui.setValue(color));
    }

    public void sendValue(int value) {
        try {
            client.sendValue(value);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }


    public void setNewCoordinates() {
        perform(() -> ui.setNewCoordinates());
    }

    public void sendNewCoordinates(int row, int column) {
        try {
            client.sendNewCoordinates(row, column);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void chooseTwoDice(){
        perform(() -> ui.chooseTwoDice());
    }

    public void sendTwoDice(int row1, int col1, int row2, int col2){
        try {
            client.sendTwoDice(row1, col1, row2, col2);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void chooseTwoNewCoordinates(){
        perform(() -> ui.chooseTwoCoordinates());
    }

    public void sendTwoNewCoordinates(int row1, int col1, int row2, int col2) {
        try {
            client.sendTwoNewCoordinates(row1, col1, row2, col2);
        } catch (IOException e) {
            e.getMessage();
            handleDisconnection();
        }
    }

    public void nextMove() {
        perform(() -> ui.nextMove());
    }

    public void toolAvailable(boolean isAvailable) {
        perform(() -> ui.toolAvailable(isAvailable));
    }

    public void updateTokens(String name, String tool, int tokens){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                //todo: if name = myself print something like "Tool card successfully used"
                proxyModel.getByName(name).removeTokens(tokens);
                for(ToolCard t : proxyModel.getToolCards()){
                    if(t.getName().equals(tool)){
                        t.addTokens();
                        if(!t.isUsed()){
                            t.setIsUsed(true);
                        }
                    }
                }
                ui.update();
            }
        };
        perform(task);

    }

    public void handleMoveDie(String name, Die d, int row, int column, int newRow, int newColumn){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.getByName(name).getPlayerWindow().getCellAt(row,column).removeDie();
                proxyModel.getByName(name).getPlayerWindow().getCellAt(newRow, newColumn).setDie(d);
//                System.out.println("[DEBUG] Die Moved" );
                ui.update();
            }
        };
        perform(task);


    }

    public void handleAddDie(String name, Die d, int row, int column){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.getByName(name).getPlayerWindow().getCellAt(row,column).setDie(d);
//                System.out.println("[DEBUG] Die Added" );
                ui.update();
            }
        };
        perform(task);


    }

    public void handleChangeTurn(String name){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.turnChanged(proxyModel.getByName(name));
            }
        };
        perform(task);

    }

    public void handleUpdateRoundTrack(Die d, int diePosition, int round){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.getRoundTrack().replaceDie(d, round, diePosition);
//                System.out.println("[DEBUG] RoundTrack updated" );
                ui.update();
            }
        };
        perform(task);

    }

    //todo: print with gui
    public void handleToolEnd(boolean response, String name){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                ui.update();
                if (name.equals(proxyModel.getMyself().getName())) {
                    if (response) {
//                        System.out.println("[DEBUG] Your tool worked correctly!");
                        ui.toolEnded(response);
                    } else {
//                        System.out.println("[DEBUG] Something went wrong with your tool");
                        ui.toolEnded(response);
                    }
                } else {
                    if (response) {
//                        System.out.println("[DEBUG] Player " + name + "'s tool worked correctly!");
                    } else {
//                        System.out.println("[DEBUG] Something went wrong with " + name + "'s tool");
                    }

                }
            }
        };
        perform(task);
    }

    public void handleDisconnection(){
        ui.handleReconnection();

        System.out.println("[DEBUG] You are disconnected! ");
    }

    public void handleUpdateGrid(String name, int row, int column, Die d){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if(proxyModel.getMyself().getName().equals(name)){
                    proxyModel.getMyself().getPlayerWindow().addDie(d, row, column);
                }else{
                    proxyModel.getByName(name).getPlayerWindow().addDie(d, row, column);
                }
            }
        };
        perform(task);

    }

    public void reconnection(){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                proxyModel.getMyself().setStatus(PlayerStatus.RECONNECTED);

            }
        };
        perform(task);
    }

    public void endGame(List<Player> players){
        ui.endGame(players);
    }

    public void finishUpdate(String name){
        Runnable task = new Runnable() {
            @Override
            public void run() {
                if(proxyModel.getMyself().getStatus().equals(PlayerStatus.RECONNECTED)){
                    proxyModel.getMyself().setStatus(PlayerStatus.ACTIVE);
                    proxyModel.setCurrentPlayer(proxyModel.getByName(name));
                    ui.setModelAfterReconnecting(proxyModel);
                    ui.update();
                    ui.initBoard();
                }

            }
        };
        perform(task);

    }

    public void notifyMoveNotAvailable(){}

}

