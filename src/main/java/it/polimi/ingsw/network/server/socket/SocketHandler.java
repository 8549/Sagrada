package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.network.server.ClientObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This thread handles socket requests
 */
public class SocketHandler extends Thread {
    private Socket socket;
    private SocketParser socketParser;
    private BufferedReader in;
    private PrintWriter out;
    private ClientObject client;
    private SocketServer socketServer;
    private Queue messageQueue = new LinkedList();

    public SocketHandler(Socket socket, SocketServer server) {
        this.socketServer = server;
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
            out.println("Hello from socketServer");

            // Get messages from the client, line by line;
            while (true) {
                String input = in.readLine();
                if (input != null) {
                    System.out.println("Client message: " + input);
                    socketParser.parseInput(input);
                    socketServer.processInput(socketParser.getType(), socketParser.getHeader(), socketParser.getData(), this);
                }
            }
        } catch (IOException e) {
            log("Error handling client");
            e.printStackTrace();
        } finally {
            try {
                log("Connection with " + client.getPlayer().getName() + " closed");
                socket.close();
                socketServer.removeClient(client);
            } catch (IOException e) {
                log("Couldn't close a socket, what's going on?");
            }

        }
    }

    /**
     * Log writes the message on socketServer's standard output
     */
    public void log(String message) {
        System.out.println(message);
    }

    public synchronized boolean send(String type, String header, String s) {
        out.println(type + "-" + header + "-" + s + "-end");
        return true;
    }

    public synchronized ClientObject getClient() {
        return this.client;
    }

    public synchronized void setClient(ClientObject client) {
        this.client = client;
    }


}
