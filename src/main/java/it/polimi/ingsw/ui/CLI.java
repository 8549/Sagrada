package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;

import java.io.IOException;
import java.util.Scanner;

public class CLI implements UI {
    private ClientHandler handler;
    private ProxyModel model;


    @Override
    public void showLogin() {
        Scanner scanner = new Scanner(System.in);
        String hostName;
        int port;
        String username;
        ConnectionType connType;

        System.out.print("Enter the server address: ");
        hostName = scanner.next();
        System.out.print("Enter the server port: ");
        port = scanner.nextInt();
        System.out.print("Enter your username: ");
        username = scanner.next();
        System.out.print("Choose a connection type (SOCKET or RMI): ");
        connType = ConnectionType.valueOf(scanner.next().toUpperCase());

        try {
            handler.handleLogin(hostName, port, username, connType);
        } catch (IOException e) {
            System.err.println("Error in connection: " + e.getMessage());
        }
        // if successful, showLoggedInUsers
        // else, show error and exit or retry
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {

    }

    @Override
    public void showLoggedInUsers() {
        System.out.println("You are logged in.");
        if (model.players.size() > 0) {
            System.out.println("Already logged in users:\n");
        }
        for (Player p : model.players) {
            System.out.println(p.getName() + "\n");
        }
        model.players.addListener(new WeakListChangeListener<>(new ListChangeListener<Player>() {
            @Override
            public void onChanged(Change<? extends Player> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (Player p : c.getAddedSubList()) {
                            System.out.println(p.getName() + " logged in");
                        }
                    } else if (c.wasRemoved()) {
                        for (Player p : c.getRemoved()) {
                            System.out.println(p.getName() + " logged out");
                        }
                    }
                }
            }
        }));
    }

    @Override
    public void setProxyModel(ProxyModel model) {
        this.model = model;
    }

    @Override
    public void launch() {
        showLogin();
    }

    public void setHandler(ClientHandler ch){
        handler = ch;
    }
}
