package it.polimi.ingsw;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.ui.CLI;
import it.polimi.ingsw.ui.GUI;
import it.polimi.ingsw.ui.UI;

import java.util.Scanner;

public class RunClient {
    public static void main(String[] args) {
        String uiType;
        UI ui;
        ClientHandler clientHandler;

        if (args.length == 0) {
            Scanner in = new Scanner(System.in);
            System.out.print("Choose a UI mode (cli or gui): ");
            uiType = in.next();
        } else {
            uiType = args[0];
        }

        if (uiType.equalsIgnoreCase("gui")) {
            ui = new GUI();
        } else if (uiType.equalsIgnoreCase("cli")) {
            ui = new CLI();
            clientHandler= new ClientHandler(ui);
            ((CLI) ui).setHandler(clientHandler);
        } else {
            System.err.println("Invalid UI choice");
            return;
        }
        ui.launch();
    }

}
