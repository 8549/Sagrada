package it.polimi.ingsw.network.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.StringTokenizer;

public class SocketParser {
    String[] message;
    private static final int TYPE = 0;
    private static final int HEADER = 1;
    private static final int DATA = 2;
    private static final int END = 3;

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
    public ObservableList<String> parseData(String data){
        ObservableList<String> d= FXCollections.observableArrayList();
        StringTokenizer tokens = new StringTokenizer(data, "/", false);
        int i = 0;
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
