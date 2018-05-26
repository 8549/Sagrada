package it.polimi.ingsw.network.server;

import java.util.StringTokenizer;

public class SocketParser {
    String[] message;
    public static final int TYPE = 0;
    public static final int HEADER = 1;
    public static final int DATA = 2;
    public static final int END = 3;

    public void parseInput(String input){
        int len= input.length();
        StringTokenizer tokens = new StringTokenizer(input, "-", false);
        message = new String[len];
        int i = 0;
        while (tokens.hasMoreTokens() && i< len) {
            message[i] = (tokens.nextToken());
            i++;
        }
    }

    public String getType(){
        return message[TYPE];
    }

    public String getHeader(){
        return message[HEADER];
    }

    public String getData(){
        return message[DATA];
    }


}
