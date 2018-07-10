package it.polimi.ingsw.network;

import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.server.socket.SocketHandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue extends Thread {
    private SocketParser socketParser;
    private SocketInterface socketObject;
    private Iterator iterator;
    private SoxketHandlerInterface socketHandler;

    private Queue messageQueue = new ConcurrentLinkedQueue();

    public MessageQueue(SocketInterface socketObject, SoxketHandlerInterface socketHandler){
        this.socketObject= socketObject;
        this.socketHandler = socketHandler;
        socketParser = new SocketParser();
    }

    @Override
    public void run() {
        while (true) {
            if (messageQueue.peek() != null) {
                socketParser.parseInput((String) messageQueue.poll());
                try {
                    socketObject.processInput(socketParser.getType(), socketParser.getHeader(), socketParser.getData(), socketHandler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void  add(String message){
        iterator = messageQueue.iterator();
        boolean found=false;
        while(iterator.hasNext()){
            String value = (String) iterator.next();
            if(value.equals(message)){
                found=true;
                break;
            }
        }
        if(!found){
            messageQueue.offer(message);

        }
    }
}
