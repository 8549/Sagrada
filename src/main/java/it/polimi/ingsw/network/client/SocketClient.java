package it.polimi.ingsw.network.client;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.SocketInterface;
import it.polimi.ingsw.network.SocketParser;
import it.polimi.ingsw.network.SoxketHandlerInterface;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SocketClient implements ClientInterface, SocketInterface {
    private Player player;
    Socket socket;
    int port;
    SocketParser socketParserClient = new SocketParser();
    ObservableList<PatternCard> patternCards = FXCollections.observableArrayList();
    SocketHandlerClient socketHandlerClient;
    StringProperty gameStatus = new SimpleStringProperty("WAITING_PLAYERS");
    ClientHandler ch;


    public SocketClient(ClientHandler ch){
        this.ch = ch;
    }

    @Override
    public void connect(String serverAddress, int portNumber, String userName) throws IOException {
        player = new Player(userName);
        port = portNumber;
        // Make connection and initialize streams
        socket = new Socket(serverAddress, port);
        socketHandlerClient = new SocketHandlerClient(this, socket, ch);
        socketHandlerClient.start();

    }
    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void login()  {
        socketHandlerClient.send("request","login", player.getName());
    }


    public ObservableList<PatternCard> getPatternCards() {
        return patternCards;
    }


    @Override
    public void processInput(String type, String header, String data, SoxketHandlerInterface socketHandler) throws IOException{
        if (type.equals("response")){
            switch(header) {
                case "login":
                            if(data.equals("true")){
                                ch.setPlayerToProxyModel(player.getName());

                            } else {
                                ch.loginFailed();
                            }
                            break;

                case "moveResponse":
                            List<String> move = socketParserClient.parseData(data);
                            Die d;
                            if(!move.get(1).equals("false")){
                                d = new Die(SagradaColor.valueOf(move.get(2)));
                                d.setNumber(Integer.valueOf(move.get(1)));
                                ch.handleMoveResponse(move.get(0),true, d, Integer.valueOf(move.get(3)), Integer.valueOf(move.get(4)));
                            }else{
                                ch.handleMoveResponse(move.get(0),false, null, -1 , -1);
                            }

                    break;

                default:
                            System.out.println("Wrong message!");
                            break;
            }
        }else if(type.equals("update")){
                switch(header){
                    case "start":
                                gameStatus.set("STARTED");
                        break;
                    case "users":   List<String> names = socketParserClient.parseData(data);
                                    System.out.println("You are playing against");
                                    for (String s : names) {
                                        System.out.println(s);

                                    }
                        break;

                    case "loggedPlayer": String name = data;
                                        Player player = new Player(name);
                                        ch.addPlayersToProxyModel(player);
                        break;

                    case "loggedPlayers":
                                        if (data.equals("You are the first player!")){
                                            ch.loggedUsers();
                                        }else{
                                                List<String> playersNames = socketParserClient.parseData(data);
                                                List<Player> players = new ArrayList<>();
                                                for (String s : playersNames) {
                                                    players.add(new Player(s));
                                                }
                                                ch.addPlayersToProxyModel(players);
                                                ch.loggedUsers();
                                            }
                        break;

                    case "disconnectedPlayer":
                                        ch.deletePlayerFromProxyModel(new Player(data));
                        break;

                    case "patterncardValidation":
                                                    ch.initPatternCard(data);
                        break;

                    case "patterncard": List<String> tokens = socketParserClient.parseData(data);
                                        //System.out.println("Player " + tokens.get(1) + " choose pattern card" + tokens.get(0));

                        break;

                    case "gameStarted":
                                        List<String> n = socketParserClient.parseData(data);
                                        List<Player> p = new ArrayList<>();
                                        int k=0;
                                        for (int i=0; i<n.size()-1; i++){
                                            p.add(new Player(n.get(i)));

                                            k=i;
                                        }
                                        ch.handleGameStarted(p, Integer.valueOf(n.get(k+1)));
                        break;

                    case "opponentsInfo": List<String> info = socketParserClient.parseData(data);
                                            for(int i=0; i<info.size(); i= i+3){
                                                ch.initPlayer(info.get(i), info.get(i+1));
                                            }
                        break;

                    case "publicObj": List<String> obj = socketParserClient.parseData(data);
                                    CardsDeck objDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
                                    }.getType());
                                    List<PublicObjectiveCard> publicObjectiveCards = new ArrayList<>();
                                    for (String o : obj){
                                        publicObjectiveCards.add((PublicObjectiveCard) objDeck.getByName(o));
                                    }
                                    if(publicObjectiveCards!=null) {
                                        ch.setPublicObjCard(publicObjectiveCards);
                                    }else{
                                        System.out.println("Public obj cards error");
                                    }
                        break;

                    case "privObj": List<String> priv = socketParserClient.parseData(data);
                                    CardsDeck objDeckpriv = new CardsDeck("PrivateObjectiveCards.json", new TypeToken<List<PrivateObjectiveCard>>() {
                                    }.getType());
                                    CardsDeck blankDeck = new CardsDeck("BlankObjectiveCard.json", new TypeToken<List<PrivateObjectiveCard>>() {
                                    }.getType());
                                    PrivateObjectiveCard blankCard = (PrivateObjectiveCard) blankDeck.getRandomCard();
                                    for (int i =0; i<priv.size(); i = i+2){
                                        if (priv.get(i+1).equals("blank")){
                                            ch.setPrivateObj(priv.get(i), blankCard);
                                        }else{
                                            ch.setPrivateObj(priv.get(i), (PrivateObjectiveCard) objDeckpriv.getByName(priv.get(i + 1)));
                                        }
                                    }
                        break;

                    case "draftPool": List<String> dice = socketParserClient.parseData(data);
                                        List<Die> draft = new ArrayList<>();
                                        for (int i =0; i<dice.size();i=i+2) {
                                            Die die = new Die(SagradaColor.valueOf(dice.get(i).toUpperCase()));
                                            die.setNumber(Integer.valueOf(dice.get(i + 1)));
                                            draft.add(die);
                                        }
                                        ch.setDraftPool(draft);

                        break;

                    case "turnStarted":
                        List<String> value = socketParserClient.parseData(data);
                        int round = Integer.parseInt(value.get(1));
                        int turn = Integer.parseInt(value.get(2));
                        ch.notifyTurnStarted(value.get(0), round, turn);
                        break;

                    case "moveTimer":
                        ch.moveTimeIsOut(ch.getModel().getByName(data));
                        break;

                    case "endTurn": ch.endTurn(data);
                        break;

                    case "tools": List<String> toolsnames = socketParserClient.parseData(data);
                        List<ToolCard> tools = new ArrayList<>();
                        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
                        }.getType());
                        for(String t : toolsnames){
                            tools.add((ToolCard) toolDeck.getByName(t));
                        }
                        ch.setTools(tools);

                        break;

                    case "endRound": List<String> roundTrackDice = socketParserClient.parseData(data);
                            List<Die> d = new ArrayList<>();
                            for (int i=0; i<roundTrackDice.size(); i= i+2){
                                Die die = new Die(SagradaColor.valueOf(roundTrackDice.get(i).toUpperCase()));
                                die.setNumber(Integer.valueOf(roundTrackDice.get(i + 1)));
                                d.add(die);
                            }
                            ch.endRound(d);
                        break;

                    case "nextMove":
                            ch.nextMove();
                        break;

                    case "toolTokens": List<String> tokensUsed = socketParserClient.parseData(data);
                                    ch.updateTokens(tokensUsed.get(0), tokensUsed.get(1), Integer.valueOf(tokensUsed.get(2)));
                        break;
                    case "moveDieTool": List<String> dieMove = socketParserClient.parseData(data);
                                        String playerMove = dieMove.get(0);
                                        Die dieMoved = new Die(SagradaColor.valueOf(dieMove.get(1)));
                                        dieMoved.setNumber(Integer.valueOf(dieMove.get(2)));
                                        int oldRow = Integer.valueOf(dieMove.get(3));
                                        int oldColumn = Integer.valueOf(dieMove.get(4));
                                        int newRow = Integer.valueOf(dieMove.get(5));
                                        int newColumn = Integer.valueOf(dieMove.get(6));
                                        ch.handleMoveDie(playerMove, dieMoved, oldRow, oldColumn, newRow, newColumn);

                        break;
                    case "addDieTool": List<String> addDie = socketParserClient.parseData(data);
                                        Die dieAdded = new Die(SagradaColor.valueOf(addDie.get(1)));
                                        dieAdded.setNumber(Integer.valueOf(addDie.get(2)));
                                        ch.handleAddDie(addDie.get(0), dieAdded,Integer.valueOf(addDie.get(3)), Integer.valueOf(addDie.get(4)));

                        break;
                    case "changeTurn": ch.handleChangeTurn(data);

                        break;
                    case "updateRoundTrack": List<String> newRoundTrack = socketParserClient.parseData(data);
                                        Die dieRound = new Die(SagradaColor.valueOf(newRoundTrack.get(1)));
                                        dieRound.setNumber(Integer.valueOf(newRoundTrack.get(2)));
                                        ch.handleUpdateRoundTrack(dieRound, Integer.valueOf(newRoundTrack.get(3)), Integer.valueOf(newRoundTrack.get(0)));
                        break;

                    case "toolEnd": List<String> toolresult = socketParserClient.parseData(data);
                                    if(toolresult.get(0).equals("true")){
                                        ch.handleToolEnd(true, toolresult.get(1));
                                    }else{
                                        ch.handleToolEnd(false, toolresult.get(1));
                                    }
                        break;

                    case "updateGrid": List<String> dieToAdd = socketParserClient.parseData(data);
                                    Die die = new Die(SagradaColor.valueOf(dieToAdd.get(3)));
                                    die.setNumber(Integer.valueOf(dieToAdd.get(4)));
                                    ch.handleUpdateGrid(dieToAdd.get(0), Integer.valueOf(dieToAdd.get(1)), Integer.valueOf(dieToAdd.get(2)), die);
                        break;

                    case "reconnection": ch.reconnection();
                        break;

                    case "endGame": List<String> finallyFinished = socketParserClient.parseData(data);
                                    List<Player> goHome = new ArrayList<>();
                                    for (int i = 0; i<finallyFinished.size(); i++){
                                        Player player1 = new Player(finallyFinished.get(i));
                                        i++;
                                        String negOrPos = finallyFinished.get(i);
                                        i++;
                                        int points = Integer.valueOf(finallyFinished.get(i));
                                        if (negOrPos.equals("n")) {
                                            points *= -1;
                                        }
                                        player1.addPoints(points);
                                        goHome.add(player1);
                                    }
                                    ch.endGame(goHome);
                        break;

                    case "finishUpdate": ch.finishUpdate(data);
                        break;

                    case "moveNotAvailable": ch.notifyMoveNotAvailable();
                        break;

                    default: break;


                }
        }else if(type.equals("request")){
                switch(header){
                    case "initPattern":
                        List<String> patternNames = socketParserClient.parseData(data);
                        CardsDeck deck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {}.getType());
                        List<PatternCard> list = new ArrayList<>();
                        list.add((PatternCard) deck.getByName(patternNames.get(0)+"/"+patternNames.get(1)));
                        list.add((PatternCard) deck.getByName(patternNames.get(2)+"/"+patternNames.get(3)));
                        if (list.size()==2) {
                            ch.patternCardChooser(list.get(0), list.get(1));
                        }
                        break;

                    case "chooseDieFromWindowPattern":
                        ch.chooseDieFromWindowPattern();
                        break;
                    case "chooseDieFromDraftPool":
                        ch.chooseDieFromDraftPool();
                        break;
                    case "chooseDieFromRoundTrack":
                        ch.chooseDieFromRoundTrack();
                        break;
                    case "chooseIfDecrease":
                        ch.chooseIfDecrease();
                        break;
                    case "chooseIfPlaceDie":
                        ch.chooseIfPlaceDie(Integer.valueOf(data));
                        break;
                    case "chooseToMoveOneDie":
                        ch.chooseToMoveOneDie();
                        break;
                    case "setValue": List<String> color = socketParserClient.parseData(data);
                        ch.setValue(SagradaColor.valueOf(color.get(0)));
                        break;
                    case "setNewCoordinates":
                        ch.setNewCoordinates();
                        break;
                    case "choooseTwoDice":
                        ch.chooseTwoDice();
                        break;
                    case "chooseTwoNewCoordinates":
                        ch.chooseTwoNewCoordinates();
                        break;

                    default: break;

                }
        }

    }


    public void setPlayer(String name){
        this.player = new Player(name);

    }

    @Override
    public void validatePatternCard(WindowPattern windowPattern) {
        socketHandlerClient.send("request","patterncard", windowPattern.getName());


    }

    @Override
    public void requestPlacement(int number, String color, int row, int column){
        socketHandlerClient.send("request", "placement", number + "/" + color + "/" + row + "/" + column);
    }

    @Override
    public void passTurn() {
        socketHandlerClient.send("request", "passTurn","");
    }

    @Override
    public void sendDieFromWP(Die d, int row, int column)  {
        socketHandlerClient.send("response", "setDieFromWP", d.getNumber() + "/" + d.getColor() + "/" + row + "/" + column);

    }

    @Override
    public void sendDieFromDP(Die d)  {
        socketHandlerClient.send("response", "setDieFromDP", d.getNumber() + "/" + d.getColor() );

    }

    @Override
    public void sendDieFromRT(Die d, int round)  {
        socketHandlerClient.send("response", "setDieFromRT", d.getNumber() + "/" + d.getColor() + "/" + round);

    }

    @Override
    public void sendDecreaseChoice(boolean choice)  {
        if(choice){
            socketHandlerClient.send("response", "setDecrease","true" );
        }else{
            socketHandlerClient.send("response", "setDecrease","false" );

        }

    }

    @Override
    public void sendPlacementChoice(boolean choice) {
        if(choice){
            socketHandlerClient.send("response", "setPlacementChoice","true" );
        }else{
            socketHandlerClient.send("response", "setPlacementChoice","false" );

        }
    }

    @Override
    public void sendNumberDiceChoice(boolean choice)  {
        if(choice){
            socketHandlerClient.send("response", "setNumberDiceChoice","true" );
        }else{
            socketHandlerClient.send("response", "setNumberDiceChoice","false" );

        }
    }

    @Override
    public void sendValue(int value){
        socketHandlerClient.send("response", "setValue", "" + value );

    }

    @Override
    public void sendNewCoordinates(int row, int column) {
        socketHandlerClient.send("response", "setNewCoordinates", row + "/" + column );

    }

    @Override
    public void sendTwoDice(int row1, int col1, int row2, int col2) {
        socketHandlerClient.send("response", "setTwoDice", row1 + "/" + col1 + "/" + row2 + "/" + col2 );
    }

    @Override
    public void sendTwoNewCoordinates(int row1, int col1, int row2, int col2) {
        socketHandlerClient.send("response", "sendTwoNewCoordinates", row1 + "/" + col1 + "/" + row2 + "/" + col2);
    }

    @Override
    public void requestTool(ToolCard tool) {
        socketHandlerClient.send("request","tool", tool.getName());
    }




}

