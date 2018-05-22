package it.polimi.ingsw.network;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static it.polimi.ingsw.network.runServer.DEFAULT_SOCKET_PORT;

public class SocketServer implements ServerInterface {
    public static final int COMMAND_LENGHT = 5;
    public static final String HOSTNAME = "127.0.0.1";
    ObservableList<ClientInterface> users = FXCollections.observableArrayList();
    ObservableList<ClientInterface> lobby = FXCollections.observableArrayList();

    @Override
    public boolean start(String[] args) throws IOException {
        ServerSocket listener = new ServerSocket(DEFAULT_SOCKET_PORT);
        try {
            while (true) {
                new mySocketHandler(listener.accept()).start();
            }
        } finally {
            listener.close();
        }
    }

    @Override
    public void startGame() throws RemoteException {

    }

    @Override
    public boolean login(ClientInterface client) throws RemoteException {
        if(!users.isEmpty() && users.contains(client)){
            System.err.println("User already logged in");
            return false;
        } else {
            users.add(client);
            lobby.add(client);
            System.out.println("Current Players");
            try {
                client.setCurrentLogged(new ArrayList(users));
                for (ClientInterface c : lobby){
                    System.out.println("Player: " + c.getName());
                    if (!c.equals(client)) {
                        //Socket updatePlayers
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }



            return true;
        }
    }

    @Override
    public boolean updateOtherServer() throws RemoteException {
        return false;
    }

    @Override
    public void notifyClients() throws RemoteException {

    }

    @Override
    public void checkRoom() throws RemoteException {

    }

    @Override
    public ObservableList<ClientInterface> getClientsConnected() throws RemoteException {
        return null;
    }

    @Override
    public synchronized  String processInput(String input) throws IOException {
        String[] command = parseInput(input);
        if (command[0].equals("request")){
            switch(command[1]){
                case "login":
                                ClientInterface client = new SocketClient(HOSTNAME, Integer.parseInt(command[3]), command[2]);
                                login(client);
                                return "login accepted";

                default:
                                System.err.println("Command not recognized, please retry");
                            break;
            }
        }
        return null;
    }

    public String[] parseInput(String input){
        StringTokenizer tokens = new StringTokenizer(input, "-", false);
        String[] results = new String[COMMAND_LENGHT];
        int i = 0;
        while (tokens.hasMoreTokens() && i< COMMAND_LENGHT) {
            results[i] = (tokens.nextToken());
            i++;
        }

        return results;
    }



    /**
     * A private thread to handle requests on a particular
     * socket.
     */
    private class mySocketHandler extends Thread {
        private Socket socket;

        public mySocketHandler(Socket socket) {
            this.socket = socket;
            log("New connection at " + socket);
        }

        /**
         * Services this thread's client by first sending the
         * client a welcome message
         */
        public void run() {
            try {

                // Decorate the streams so we can send characters
                // and not just bytes.  Ensure output is flushed
                // after every newline.
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello");

                String outputLine;
                // Get messages from the client, line by line;
                while (true) {
                    String input = in.readLine();
                    outputLine = processInput(input);
                    out.println(outputLine);
                }
            } catch (IOException e) {
                log("Error handling client");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client closed");
            }
        }

        /**
         * Logs a simple message.  In this case we just write the
         * message to the server applications standard output.
         */
        private void log(String message) {
            System.out.println(message);
        }
    }
}
