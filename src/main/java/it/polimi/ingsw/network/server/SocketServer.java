package it.polimi.ingsw.network.server;

import it.polimi.ingsw.model.Player;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.network.server.MainServer.DEFAULT_SOCKET_PORT;;

public class SocketServer implements ServerInterface {

    private List<ClientObject> users;
    private List<SocketHandler> socketClients = new ArrayList<>();

    MainServer server;

    public SocketServer(List<ClientObject> users, MainServer server){
        this.users = users;
        this.server = server;
    }

    @Override
    public  void start() throws IOException {
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
        } catch (IOException e){
            System.err.println("[System]Socket server failed " + e);
            e.printStackTrace();
        }finally {
            listener.close();
            System.out.println("Socket server closed");
        }
    }



    public synchronized String processInput(String type, String header, String data, SocketHandler s) throws IOException {
    if (type.equals("request")){
        switch(header) {
            case "login":
                        SocketClientObject client = new SocketClientObject(new Player(data),this, s);
                        if(server.addClient(client)){
                            s.send("response","login","Login Accepted !");
                            server.addAlreadyLoogedPlayers(client);
                            server.addLoggedPlayer(client.getPlayer());
                            s.setClient(client);

                        }
                    break;

            case "patterncard":
                        System.out.println("Player" + s.getClient() + " has chosen " + data);


                break;

            default:
                System.out.println("Wrong message!");
        }
    }else{
        System.out.println("Nothing for this command yet");
    }

        return null;
    }

    private void removeClient(ClientObject client) {
        server.disconnect(client);
    }



    /**
     * This thread handles socket requests
     */
    public class SocketHandler extends Thread {
        private Socket socket;
        private SocketParser socketParser;
        private BufferedReader in;
        private PrintWriter out;
        private ClientObject client;
        private SocketServer server;

        public SocketHandler(Socket socket, SocketServer server) throws IOException {
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

                // Get messages from the client, line by line;
                while (true) {
                    String input = in.readLine();
                    if (input == null){
                        break;
                    }else{
                        System.out.println("Client message: " + input);
                        socketParser.parseInput(input);
                        processInput(socketParser.getType(), socketParser.getHeader(), socketParser.getData(), this);
                    }
                }
            } catch (IOException e) {
                log("Error handling client");
                e.printStackTrace();
            } finally {
                log("Connection with " + client.getPlayer().getName() + " closed");
                server.removeClient(client);
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }

            }
        }

        /**
         * Log writes the message on server's standard output
         */
        private void log(String message) {
            System.out.println(message);
        }

        public synchronized boolean send(String type, String header, String s){
            out.println(type + "-" + header + "-" + s + "-end");
            return true;
        }

        private synchronized void setClient(ClientObject client){
            this.client = client;
        }
        private synchronized String getClient(){return this.client.getPlayer().getName();}


    }


}
