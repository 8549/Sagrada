package it.polimi.ingsw.network;

import it.polimi.ingsw.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.network.runServer.DEFAULT_SOCKET_PORT;

public class SocketClient implements ClientInterface{
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

            String response;
            try {
                response = in.readLine();
                if (response == null || response.equals("")) {
                    System.exit(0);
                }else{
                    System.out.println("Response from server: " + response);
                }
            } catch (IOException ex) {
                response = "Error: " + ex;
            }



    }
    @Override
    public String getName() throws RemoteException {
        return player.getName();
    }

    @Override
    public void login() throws RemoteException {
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
    public void pushData() throws RemoteException {

    }

    @Override
    public void updatePlayersInfo(ClientInterface c) throws RemoteException {
        clients.add(c);
    }

    @Override
    public ObservableList<ClientInterface> getClients() throws RemoteException {
        return clients;
    }

    @Override
    public void setCurrentLogged(List<ClientInterface> clients) throws RemoteException {
        clients.addAll(clients);
    }
}
