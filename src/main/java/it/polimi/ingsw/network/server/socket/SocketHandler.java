package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.network.MessageQueue;
import it.polimi.ingsw.network.SocketParser;
import it.polimi.ingsw.network.SoxketHandlerInterface;
import it.polimi.ingsw.network.server.ClientObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This thread handles socket requests
 */
public class SocketHandler extends Thread implements SoxketHandlerInterface {
    private Socket socket;
    private SocketParser socketParser;
    private BufferedReader in;
    private PrintWriter out;
    private ClientObject client;
    private SocketServer socketServer;
    MessageQueue messageQueue;
    Ping ping;

    public SocketHandler(Socket socket, SocketServer server) {
        this.socketServer = server;
        this.socket = socket;
        log("New connection at " + socket);
        socketParser = new SocketParser();
        messageQueue = new MessageQueue(server, this);

    }

    public void run() {
        try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Send a welcome message to the client.
                out.println("Hello from socketServer");
                messageQueue.start();
                ping = new Ping(this);
                ping.start();
                // Get messages from the client, line by line;
                while (true) {
                    String input = in.readLine();
                    if (input != null) {
                        System.out.println("Client message: " + input);
                        if(input.equals("pingResponse---end")){
                            ping.setIsAlive(true);
                        }else{
                            messageQueue.add(input);
                        }
                    }
                }
        } catch (IOException e) {
            log("Error handling client");
            e.getMessage();
            try {
                log("Connection with " + client.getPlayer().getName() + " closed");
                ping.interrupt();
                socket.close();
                socketServer.removeClient(client);
            } catch (IOException e1) {
                log("Couldn't close a socket, what's going on?");
            }
        }
    }

    /**
     * Log writes the message on socketServer's standard output
     */
    @Override
    public void log(String message) {
        System.out.println(message);
    }

    @Override
    public synchronized boolean send(String type, String header, String s) {
        out.println(type + "-" + header + "-" + s + "-end");
        return true;
    }

    @Override
    public synchronized ClientObject getClient() {
        return this.client;
    }

    @Override
    public synchronized void setClient(ClientObject client) {
        this.client = client;
    }


    private class Ping extends Thread{
        boolean[] isAlive = {false};
        Timer timer2 = new Timer();
        final boolean[] isTimerRunning = {false};
        SocketHandler socketHandler;

        public  Ping(SocketHandler socketHandler){
            this.socketHandler=socketHandler;
        }

        @Override
        public void run() {

            isAlive[0] =false;
            Timer timer1 = new Timer();

            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    socketHandler.send("ping","","");
                    System.out.println("[DEBUG] Ping sent");
                    isTimerRunning[0]= true;
                    synchronized (timer2){
                        if(timer2 ==null){
                            timer2= new Timer();
                        }
                    }
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!isAlive[0]) {
                                try {
                                    System.out.println("[DEBUG] Client " + socketHandler.getClient().getPlayer().getName() + " disconnected");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                isTimerRunning[0] = false;
                                socketServer.removeClient(client);
                                timer1.cancel();
                                timer2.cancel();

                            }else{
                                isAlive[0]= false;
                            }
                        }
                    }, 5*1000);
                }
            },  0, 20 * 1000 );
        }

        public void setIsAlive(boolean isAlive){
            this.isAlive[0]= isAlive;

        }
    }

}
