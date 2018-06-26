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

import static it.polimi.ingsw.network.server.MainServer.DEFAULT_SOCKET_PORT;


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
                            String response=data;
                            if(data.equals("Login Accepted !")){
                                ch.setPlayerToProxyModel(player.getName());

                            } else {
                                ch.loginFailed();
                            }
                            break;

                case "moveResponse":
                            if (data.equals("Move accepted"))
                                    ch.handleMoveResponse(true);
                            else if (data.equals("Wrong move")){
                                ch.handleMoveResponse(false);
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
                                        for (String s : n){
                                            p.add(new Player(s));
                                        }
                                        ch.handleGameStarted(p);
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
                                            //ch.setPrivateObj(priv.get(i), new BlankObjectiveCard());
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
                        int turn = Integer.parseInt(value.get(1));
                        ch.notifyTurnStarted(value.get(0), round, turn);
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

