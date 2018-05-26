package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import static it.polimi.ingsw.network.runServer.DEFAULT_SOCKET_PORT;

public class SocketClient implements ClientInterface {
    private Player player;
    ObservableList<ClientInterface> clients = FXCollections.observableArrayList();
    private BufferedReader in;
    private PrintWriter out;
    Socket socket;
    int port;

    public SocketClient(String serverAddress, int portNumber, String userName) throws IOException {
        player = new Player(userName);
        port = portNumber;
        // Make connection and initialize streams
        socket = new Socket(serverAddress, DEFAULT_SOCKET_PORT);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        String response = waitResponse();

        System.out.println("Response from server: " + response);


    }
    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void login()  {
        out.println("request-login-" + player.getName() + "-" + port + "-end" );
        updatePlayersInfo(this);
        try {
            String response= in.readLine();
            System.out.println(response);

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

    public String waitResponse(){
        String response="";
        while (response == null || response.equals("")){
            try {
                response= in.readLine();
            } catch (IOException e) {
                System.err.println("Socket problem occured");
                e.printStackTrace();
            }
        }
        return response;
    }
}
