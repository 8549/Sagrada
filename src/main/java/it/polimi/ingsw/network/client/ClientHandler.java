package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.UI;

import java.io.IOException;

public class ClientHandler {
    private ClientInterface client;
    private boolean loginResponse;
    private UI ui;

    public ClientHandler(UI ui) {
        this.ui = ui;
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

    public void loginSuccessful() {
        ui.showLoggedInUsers();
    }
}
