package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.ui.CLI;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.UI;

import java.util.Scanner;

public class RunClient {
    private static ClientHandler clientHandler;

    public static void main(String[] args) {
        String uiType = "";
        UI ui;
        boolean validChoice = false;
        if (args.length == 0) {
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
        } else {
            uiType = args[0];
        }
        if (uiType.equalsIgnoreCase("gui")) {
            ui = new GUI();
        } else if (uiType.equalsIgnoreCase("cli")) {
            ui = new CLI();
        } else {
            System.err.println("Invalid UI choice");
            return;
        }
        clientHandler = new ClientHandler(ui);
        ui.initUI();
    }

    public static ClientHandler getClientHandler() {
        return clientHandler;
    }
}
