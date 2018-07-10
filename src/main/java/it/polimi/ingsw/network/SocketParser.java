package it.polimi.ingsw.network;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SocketParser {
    String[] message;
    private static final int TYPE = 0;
    private static final int HEADER = 1;
    private static final int DATA = 2;

    public void parseInput(String input){
        if (input !=null) {
            int len = input.length();
            StringTokenizer tokens = new StringTokenizer(input, "-", false);
            message = new String[len];
            int i = 0;
            while (tokens.hasMoreTokens() && i < len) {
                message[i] = (tokens.nextToken());
                i++;
            }
        }
    }
    public List<String> parseData(String data){
        List<String> d= new ArrayList<>();
        StringTokenizer tokens = new StringTokenizer(data, "/", false);
        while (tokens.hasMoreTokens()) {
            d.add(tokens.nextToken());

        }
        return d;
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
