package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionType;

import java.io.IOException;
import java.rmi.RemoteException;

public class ClientHandler {
    ClientInterface client;
    boolean loginResponse;

    public ClientHandler(){

    }

    public void handleLogin(String hostname, int port, String username, ConnectionType connectionType) throws IOException {

        if(connectionType.equals(ConnectionType.SOCKET)){
            client = new SocketClient(this);
        }else if(connectionType.equals(ConnectionType.RMI)){
            client = new RMIClient(this);

        }

        client.connect(hostname, port, username);
        client.login();
    }

    public boolean getLoginResponse(boolean response){
        return response;
    }

    public void setLoginResponse(boolean loginResponse){
        this.loginResponse = loginResponse;
    }

    public void showLoginDialog(){

    }
}
