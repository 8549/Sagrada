package it.polimi.ingsw.network.serverside;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static it.polimi.ingsw.network.runServer.DEFAULT_SOCKET_PORT;

public class SocketServer implements ServerInterface {

    ObservableList<ClientWrapper> users;
    ObservableList<ClientWrapper> lobby;
    ObservableList<SocketHandler> socketClients = FXCollections.observableArrayList();

    public SocketServer(ObservableList<ClientWrapper> users, ObservableList<ClientWrapper> lobby){
        this.users = users;
        this.lobby = lobby;
    }

    @Override
    public void start(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(DEFAULT_SOCKET_PORT);
        System.out.println("[System] Socket server is listening on port " + DEFAULT_SOCKET_PORT);

        try {
            while (true) {
                SocketHandler socketHandler= new SocketHandler(listener.accept(), this);
                System.out.println("New Connection detected");
                if(!socketClients.contains(socketHandler)){
                    socketClients.add(socketHandler);
                }
                socketHandler.start();

            }
        } catch (Exception e){
            System.err.println("[System]Socket server failed " + e);
            e.printStackTrace();
        }finally {
            listener.close();
            System.out.println("Socket server closed");
        }
    }

    @Override
    public void ping() {
        for (SocketHandler s : socketClients){
            try {
                if(!s.ping()){
                    System.out.println("Connection with client " + s.getClient().getName() + " lost ");
                }

            } catch (IOException e) {
                System.out.println("Connection with client " + s.getClient().getName() + " lost ");
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean login(ClientWrapper client){
        for (ClientWrapper c : users ){
            if (c.getName().equals(client.getName())){
                System.err.println("User already logged in");
                return false;
            }
        }
        users.add(client);
        lobby.add(client);
        client.setCurrentLogged(users);
        for (ClientWrapper c : lobby){
            if (!c.equals(client)) {
                c.updatePlayersInfo(client);
            }
        }
        showClients();

        return true;
    }

    @Override
    public boolean updateOtherServer()  {
        return false;
    }

    @Override
    public void notifyClients()  {

    }

    @Override
    public void showClients()  {
        System.out.println("Current Players");
        for (ClientWrapper c : users){
            System.out.println("Player: " + c.getName());
        }

    }

    @Override
    public ObservableList<ClientWrapper> getClientsConnected() {
        return null;
    }

    @Override
    public void removeClient(ClientWrapper c) {
        users.remove(c);
    }

    public String processInput(String header, String data, SocketHandler s) {

        switch(header){
            case "login":   ClientWrapper client = new SocketClientWrapper(data, this);
                            s.setClient(client);
                            boolean response = login(client);
                            return client.loginResponse(response);

            default : System.out.println("Wrong message!");
        }

        return null;
    }



    /**
     * This thread handles socket requests
     */
    public class SocketHandler extends Thread {
        private Socket socket;
        private SocketParser socketParser;
        private BufferedReader in;
        private PrintWriter out;
        private ClientWrapper client;
        private SocketServer server;

        public SocketHandler(Socket socket, SocketServer server) {
            this.server = server;
            this.socket = socket;
            log("New connection at " + socket);
            socketParser = new SocketParser();
        }

        public void run() {
            try {

                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello from server");

                String outputLine;

                // Get messages from the client, line by line;
                while (true) {
                    String input = in.readLine();
                    System.out.println("Client message: " + input);
                    socketParser.parseInput(input);
                    outputLine = processInput(socketParser.getHeader(), socketParser.getData(), this);

                    out.println(outputLine);
                }
            } catch (IOException e) {
                log("Error handling client");
            } finally {
                server.removeClient(client);
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client closed");


            }
        }

        /**
         * Log writes the message on server's standard output
         */
        private void log(String message) {
            System.out.println(message);
        }

        public synchronized boolean sendResponse(String s){
            out.println(s);
            return true;
        }

        public synchronized boolean ping() throws IOException {
            out.println("ping");
            String r = in.readLine();
            if (r==null){
                return false;
            }else{
                return true;
            }

        }

        public ClientWrapper getClient(){
            return this.client;
        }

        public void setClient(ClientWrapper c){
            this.client = c;
        }

    }
}
