package it.polimi.ingsw.network.client;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.CardsDeck;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.server.SocketParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.runServer.DEFAULT_SOCKET_PORT;

public class SocketClient implements ClientInterface {
    private Player player;
    ObservableList<ClientInterface> clients = FXCollections.observableArrayList();
    private BufferedReader in;
    private PrintWriter out;
    Socket socket;
    int port;
    SocketParser socketParser;
    ObservableList<PatternCard> patternCards = FXCollections.observableArrayList();

    @Override
    public void connect(String serverAddress, int portNumber, String userName) throws IOException {
        player = new Player(userName);
        port = portNumber;
        // Make connection and initialize streams
        socket = new Socket(serverAddress, DEFAULT_SOCKET_PORT);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        socketParser = new SocketParser();
        listen();

    }
    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void login()  {
        out.println("request-login-" + player.getName() + "-" + port + "-end" );
        try {
            listen();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void pushData() {

    }

    @Override
    public void updatePlayersInfo(ClientInterface c)  {
        clients.add(c);
    }

    @Override
    public ObservableList<ClientInterface> getClients() {
        return clients;
    }

    @Override
    public void setCurrentLogged(List<ClientInterface> clients) {
        clients.addAll(clients);
    }

    public String processInput(String type, String header, String data){
        if (type.equals("response")){
            switch(header) {
                case "login":
                    try {
                    String response = in.readLine();
                    System.out.println(response);
                    if(response.equals("Login Accepted !")){
                        System.out.println("Login successful!");
                        updatePlayersInfo(this);
                        //waiting for data
                        listen();
                    } else {
                        System.out.println("Try with a different username, or maybe the game is already began so... :(");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                    break;
                default:
                    System.out.println("Wrong message!");
            }
        }else if(type.equals("update")){
                switch(header){
                    case "users": ObservableList<String> names = socketParser.parseData(data);
                        for (String s : names) {
                            ClientInterface client = new SocketClient();
                            ((SocketClient) client).setPlayer(s);
                            updatePlayersInfo(client);
                        }
                        break;
                    case "initPattern":
                        ObservableList<String> patternNames = socketParser.parseData(data);
                        CardsDeck deck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
                        }.getType());
                        List<PatternCard> list = new ArrayList<>();
                        list.add((PatternCard) deck.getByName(patternNames.get(0)+patternNames.get(1)));
                        list.add((PatternCard) deck.getByName(patternNames.get(2)+patternNames.get(3)));
                        patternCards.addAll(list);
                        break;
                    default: break;

                }
            }

        return null;
    }
    public void listen() throws IOException {
        // Get messages from the server, line by line;
        boolean end= false;
        while (true) {
            String input = in.readLine();
            System.out.println("Server message: " + input);
            if(input.equals("Hello from server")){
                System.out.println("Connection with server established");
                end=true;
            }else {
                socketParser.parseInput(input);
                String out = processInput(socketParser.getType(), socketParser.getHeader(), socketParser.getData());
                end=true;
            }
        }


    }

    public void setPlayer(String name){
        this.player = new Player(name);

    }

}

