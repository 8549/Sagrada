package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.MessageQueue;
import it.polimi.ingsw.network.SocketParser;
import it.polimi.ingsw.network.SoxketHandlerInterface;
import it.polimi.ingsw.network.server.ClientObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketHandlerClient extends Thread implements SoxketHandlerInterface {
        private BufferedReader in;
        private PrintWriter out;
        private SocketClient client;
        private Socket socket;
        private SocketParser socketParser;
        private MessageQueue messageQueue;
        private ClientHandler ch;

        public SocketHandlerClient(SocketClient client, Socket socket, ClientHandler ch) throws IOException {
            this.client = client;
            this.socket = socket;
            this.ch = ch;
            socketParser = new SocketParser();
            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            messageQueue = new MessageQueue(client, this);
        }
        public synchronized void run(){
            // Get messages from the server, line by line;
            System.out.println("client is listening");
            try {

                while (true) {
                    String input = in.readLine();
                    if (input != null) {
                        if (input.equals("Hello from socketServer")) {
                            System.out.println("Connection with server established");
                            messageQueue.start();
                        }else{
                            if(input.equals("ping---end")){
                                send("pingResponse", "","");
                            }else{
                                messageQueue.add(input);
                            }
                        }
                    }
                }
            }catch (IOException e){
                System.out.println("Error while handlig client socket");
                ch.handleDisconnection();
            }
        }

    @Override
    public void log(String message) {

    }

    public boolean send(String type, String header, String s){
            out.println(type + "-" + header + "-" + s + "-end");
            return true;
        }

    @Override
    public ClientObject getClient() {
        return null;
    }

    @Override
    public void setClient(ClientObject client) {

    }
}

