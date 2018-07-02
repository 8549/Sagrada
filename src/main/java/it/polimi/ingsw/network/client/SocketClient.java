package it.polimi.ingsw.network.client;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.socket.SocketParser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SocketClient implements ClientInterface {
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
        socketHandlerClient = new SocketHandlerClient(this, socket);
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



    public String getGameStatus() {
        return gameStatus.get();
    }

    public StringProperty gameStatusProperty() {
        return gameStatus;
    }

    public ObservableList<PatternCard> getPatternCards() {
        return patternCards;
    }


    public String processInput(String type, String header, String data){
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
                                            for(int i=0; i<info.size(); i= i+2){
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
                        ch.moveTimeIsOut();
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
                        ch.chooseIfPlaceDie();
                        break;
                    case "chooseToMoveOneDie":
                        ch.chooseToMoveOneDie();
                        break;
                    case "setValue":
                        ch.setValue();
                        break;
                    case "setOldCoordinates":
                        ch.setOldCoordinates();
                        break;
                    case "setNewCoordinates":
                        ch.setNewCoordinates();
                        break;

                    default: break;

                }
        }

        return null;
    }


    public void setPlayer(String name){
        this.player = new Player(name);

    }

    public void validatePatternCard(WindowPattern windowPattern) {
        socketHandlerClient.send("request","patterncard", windowPattern.getName());


    }

    @Override
    public void requestPlacement(int number, String color, int row, int column){
        socketHandlerClient.send("request", "placement", number + "/" + color + "/" + row + "/" + column);
    }

    @Override
    public void sendDieFromWP(Die d, int row, int column) throws IOException {
        socketHandlerClient.send("response", "setDieFromWP", d.getNumber() + "/" + d.getColor() + "/" + row + "/" + column);

    }

    @Override
    public void sendDieFromDP(Die d) throws IOException {
        socketHandlerClient.send("response", "setDieFromDP", d.getNumber() + "/" + d.getColor() );

    }

    @Override
    public void sendDieFromRT(Die d, int round) throws IOException {
        socketHandlerClient.send("response", "setDieFromRT", d.getNumber() + "/" + d.getColor() + "/" + round);

    }

    @Override
    public void sendDecreaseChoice(boolean choice) throws IOException {
        if(choice){
            socketHandlerClient.send("response", "setDecrease","true" );
        }else{
            socketHandlerClient.send("response", "setDecrease","false" );

        }

    }

    @Override
    public void sendPlacementChoice(boolean choice) throws IOException {
        if(choice){
            socketHandlerClient.send("response", "setPlacementChoice","true" );
        }else{
            socketHandlerClient.send("response", "setPlacementChoice","false" );

        }
    }

    @Override
    public void sendNumberDiceChoice(boolean choice) throws IOException {
        if(choice){
            socketHandlerClient.send("response", "setNumberDiceChoice","true" );
        }else{
            socketHandlerClient.send("response", "setNumberDiceChoice","false" );

        }
    }

    @Override
    public void sendValue(int value) throws IOException {
        socketHandlerClient.send("response", "setValue", "" + value );

    }

    @Override
    public void sendOldCoordinates(int row, int column) throws IOException {
        socketHandlerClient.send("response", "setOldCoordinates", row + "/" + column );

    }

    @Override
    public void sendNewCoordinates(int row, int column) throws IOException {
        socketHandlerClient.send("response", "setNewCoordinates", row + "/" + column );

    }

    private class SocketHandlerClient extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        SocketClient client;
        Socket socket;
        SocketParser socketParser;

        private SocketHandlerClient(SocketClient client, Socket socket) throws IOException {
            this.client = client;
            this.socket = socket;
            socketParser = new SocketParser();
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
        }
        public synchronized void run(){
            // Get messages from the server, line by line;
            System.out.println("client is listening");
            try {

                while (true) {
                    String input = in.readLine();
                    if (input != null) {
                        if (input.equals("Hello from server")) {
                            System.out.println("Connection with server established");
                        }else{
                            socketParser.parseInput(input);
                            String out = processInput(socketParser.getType(), socketParser.getHeader(), socketParser.getData());
                        }
                    }
                }
            }catch (IOException e){
                System.out.println("Error while handlig client socket");
            }
        }

        public boolean send(String type, String header, String s){
            out.println(type + "-" + header + "-" + s + "-end");
            return true;
        }
    }
}

