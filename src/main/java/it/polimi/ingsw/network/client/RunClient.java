package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.ConnectionBundle;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.ui.CLI;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.UI;

import java.util.Scanner;

public class RunClient {
    public static final ConnectionBundle bundle = new ConnectionBundle();

    public static void main(String[] args) {
        String uiType = "";
        UI ui;
        boolean validChoice = false;
        if (args.length > 4) {
            try {
                int port = Integer.valueOf(args[4]);
                bundle.setPort(port);
            } catch (NumberFormatException e) {
                bundle.setPort(ConnectionBundle.INT_UNSET);
            }
        }
        if (args.length > 3) {
            bundle.setUsername(args[3]);
        }
        if (args.length > 2) {
            if (args[2].equalsIgnoreCase("rmi")) {
                bundle.setConnectionType(ConnectionType.RMI);
            } else if (args[2].equalsIgnoreCase("socket")) {
                bundle.setConnectionType(ConnectionType.SOCKET);
            }
        }
        if (args.length > 1) {
            bundle.setServer(args[1]);
        }
        if (args.length > 0) {
            uiType = args[0];
        }
        if (uiType.equalsIgnoreCase("cli") || uiType.equalsIgnoreCase("gui")) {
            validChoice = true;
        } else {
            Scanner in = new Scanner(System.in);
            System.out.print("Choose a UI mode (cli or gui): ");
            while (!validChoice) {
                if (in.hasNext()) {
                    uiType = in.next();
                    if (uiType.equalsIgnoreCase("gui") || uiType.equalsIgnoreCase("cli")) {
                        validChoice = true;
                    } else {
                        System.out.print("Please choose a valid UI mode (cli or gui): ");
                    }
                } else {
                    System.out.print("Please choose a valid UI mode (cli or gui): ");
                    in.next();
                }
            }
        }
        if (uiType.equalsIgnoreCase("gui")) {
            ui = new GUI();
        } else if (uiType.equalsIgnoreCase("cli")) {
            ui = new CLI();
        } else {
            System.err.println("Invalid UI choice");
            return;
        }
        ui.initUI();
    }

    public static ConnectionBundle getBundle() {
        return bundle;
    }
}
