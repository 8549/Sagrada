package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.ProxyModel;
import it.polimi.ingsw.ui.UI;

import java.io.IOException;
import java.util.List;

public class ClientHandler {
    private ClientInterface client;
    private boolean loginResponse;
    private UI ui;
    ProxyModel proxyModel;

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


    public void setLoginResponse(boolean loginResponse){
        this.loginResponse = loginResponse;
    }

    public  void loginSuccessful() {
        proxyModel = new ProxyModel();
        ui.setProxyModel(this.proxyModel);
        ui.showLoggedInUsers();

    }

    public void addPlayersToProxyModel(List<Player> p){
        while(proxyModel==null){}
        proxyModel.addPlayers(p);
    }
    public void addPlayersToProxyModel(Player p){
        while(proxyModel==null){}
        proxyModel.addPlayers(p);
    }
}
