package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.network.ConnectionType;

import java.util.Scanner;

public class CLI implements UI {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void showLogin() {
        String hostName;
        int port;
        String username;
        ConnectionType connType;

        System.out.println("Enter the server address: ");
        hostName = scanner.next();
        System.out.println("Enter the server port: ");
        port = scanner.nextInt();
        System.out.println("Enter your username: ");
        username = scanner.next();
        System.out.println("Choose a connection type (SOCKET or RMI): ");
        connType = ConnectionType.valueOf(scanner.next());

        // LOGIN
        // if successful, showLoggedInUsers
        // else, show error and exit or retry
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {

    }

    @Override
    public void showLoggedInUsers() {

    }

    @Override
    public void setProxyModel(ProxyModel model) {

    }

    @Override
    public void launch() {
        showLogin();
    }
}
