package it.polimi.ingsw.network.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

import static it.polimi.ingsw.network.runServer.DEFAULT_SOCKET_PORT;

public class SocketServer implements ServerInterface {

    ObservableList<ClientWrapper> users;
    ObservableList<ClientWrapper> lobby;
    ObservableList<SocketHandler> socketClients = FXCollections.observableArrayList();
    Timer timer;
    private boolean timerIsRunning=false;

    public SocketServer(ObservableList<ClientWrapper> users, ObservableList<ClientWrapper> lobby){
        this.users = users;
        this.lobby = lobby;
    }

    @Override
    public  void start(String[] args) throws IOException {
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
    public  void ping() {
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
    public  boolean login(ClientWrapper client){
        for (ClientWrapper c : users ){
            if (c.getName().equals(client.getName())){
                System.err.println("User already logged in");
                return false;
            }
        }
        users.add(client);
        if(lobby.size()<5){
            lobby.add(client);

        }
        client.setCurrentLogged(users);
        for (ClientWrapper c : users){
            if (!c.equals(client)) {
                c.updatePlayersInfo(client);
            }
        }
        showClients();
        return checkTimer();

    }

    @Override
    public  boolean updateOtherServer()  {
        return false;
    }

    @Override
    public  void notifyClients()  {

    }

    @Override
    public  void showClients()  {
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
    public  void removeClient(ClientWrapper c) {
        users.remove(c);
    }

    @Override
    public  boolean checkTimer() {
        if (users.size()<2){
            System.out.println("Waiting for more players . . .");
            if (timerIsRunning) {
                timer.cancel();
                timerIsRunning = false;
                System.out.println("Timer stopped");
            }
        }else {
            if (users.size()==2){
                timerIsRunning = true;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        //TODO: Start game code here
                        try {
                            showClients();
                            System.out.println("Time to join the game is out !");
                            if (users.size() > 2) {
                                System.out.println("Let the game begin !");
                            }
                        }catch (Exception e){
                            System.out.println("Exception inside timer!!!!!!!!!!!!!!!!!!!!!!!");
                            e.printStackTrace();
                        }
                    }
                }, 80*1000);

                System.out.println("Timer has started!!" );

            }else if(users.size()==4){
                //TODO: Start game code here
                System.out.println("Let the game begin !");

            } else if(users.size()>4){
                System.out.println("Too many users !");
                return false;
            }
        }
        return true;

    }

    public synchronized String processInput(String header, String data, SocketHandler s) {

        switch(header){
            case "login":   ClientWrapper client = new SocketClientWrapper(data);
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
                log("Connection with " + client.getName() + " closed");
                checkTimer();

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
