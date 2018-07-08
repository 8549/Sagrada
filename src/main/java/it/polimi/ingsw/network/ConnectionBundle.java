package it.polimi.ingsw.network;

public class ConnectionBundle {
    public static final String STRING_UNSET = "STRING_UNSET";
    public static final int INT_UNSET = 0;
    private ConnectionType connectionType;
    private String username;
    private String server;
    private int port;

    public ConnectionBundle() {
        port = INT_UNSET;
        server = STRING_UNSET;
        username = STRING_UNSET;
        connectionType = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }


}
