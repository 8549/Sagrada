package it.polimi.ingsw.ui;

import java.util.Properties;
import java.util.Scanner;

public class CLILauncher {
    public static void launch(String[] args) {
        // Print Sagrada logo
        // TODO: ascii logo
        System.out.println("Welcome to Sagrada!");

        Properties props = GameProperties.getFromFileOrCli(args);

        // If a value wasn't supplied neither by the CLI nor by the file, ask the user for it
        for (String key : GameProperties.keys) {
            Scanner scanner = new Scanner(System.in);
            while (props.get(key) == null) {
                // If the required option wasn't supplied neither by file nor by cli arguments, ask to the user
                System.out.print("Please enter " + key + ": ");
                props.setProperty(key, scanner.next());
            }
        }

        boolean validProps = false;
        try {
            validProps = GameProperties.validateArgs(props);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid " + e.getMessage());
            System.exit(-1);
        }

        // TODO
        if (validProps) {
            System.out.println("DEBUG: NOW LAUNCH THE CLIENT!");
        } else {
            System.out.println("This should never happen, but it did.");
        }
    }
}
