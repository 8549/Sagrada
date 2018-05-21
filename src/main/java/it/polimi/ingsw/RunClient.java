package it.polimi.ingsw;

import it.polimi.ingsw.ui.CLILauncher;
import it.polimi.ingsw.ui.GUILauncher;
import it.polimi.ingsw.ui.GameProperties;
import javafx.application.Application;

import java.util.Properties;

public class RunClient {
    public static void main(String[] args) {

        Properties props = GameProperties.getFromFileOrCli(args);

        // Launch appropriate client with args
        if (props.containsKey(GameProperties.UI_KEY) &&
                (props.getProperty(GameProperties.UI_KEY).equalsIgnoreCase("gui"))) {
            Application.launch(GUILauncher.class, args);
        } else {
            CLILauncher.launch(args);
        }

    }

}
