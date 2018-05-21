package it.polimi.ingsw.ui;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.RunClient;
import it.polimi.ingsw.serialization.GsonSingleton;
import joptsimple.OptionParser;
import joptsimple.OptionSet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

public class GameProperties {
    public static final String USERNAME_KEY = "username";
    public static final String HOSTNAME_KEY = "hostname";
    public static final String PORT_KEY = "port";
    public static final String TIMEOUT_KEY = "timeout";
    public static final String CONNECTION_KEY = "connection";
    public static final String UI_KEY = "ui";
    public static final String[] keys = {USERNAME_KEY, HOSTNAME_KEY, PORT_KEY, TIMEOUT_KEY, CONNECTION_KEY, UI_KEY};

    public static Properties getFromFileOrCli(String[] args) {
        // Load config from file
        Properties props = new Properties();

        Gson gson = GsonSingleton.getInstance();
        try (JsonReader reader = new JsonReader(new InputStreamReader(RunClient.class.getClassLoader().getResourceAsStream("ClientConfig.json")))) {
            props = gson.fromJson(reader, Properties.class);
        } catch (IOException | NullPointerException e) {
            System.err.println("Couldn't load ClientConfig.json");
        }

        // Parse CLI args
        OptionParser parser = new OptionParser();
        // Options can be parsed with "abbreviations" (e.g. we can use -p 3150 -u nickname) as long as they're not ambiguous
        // THe parsed arguments default on Strings, which is fine until the very end of this method where we actually launch the GUI/CLI.
        parser.accepts(USERNAME_KEY, "Log in to the server using this name").withRequiredArg();
        parser.accepts(HOSTNAME_KEY, "The hostname or IP address of the game server").withRequiredArg();
        parser.accepts(PORT_KEY, "The port of the game server is listening on. Ignored if connecting via RMI").withRequiredArg();
        parser.accepts(TIMEOUT_KEY, "Timeout of this client (in millis)").withRequiredArg();
        parser.accepts(CONNECTION_KEY, "Connect to the game server either using RMI or Socket").withRequiredArg();
        parser.accepts(UI_KEY, "Game mode, either CLI or GUI").withRequiredArg();
        OptionSet opts = parser.parse(args);

        // If found, the CLI arguments override those loaded from file
        for (String key : GameProperties.keys) {
            if (opts.hasArgument(key)) {
                props.setProperty(key, (String) opts.valueOf(key));
            }
        }

        return props;
    }

    public static Properties getFromFileOrCli(List<String> args) {
        String[] strings = new String[args.size()];
        return getFromFileOrCli(args.toArray(strings));
    }

    public static boolean validateArgs(Properties props) {
        // Input validation
        if (props.getProperty(USERNAME_KEY).isEmpty()) {
            throw new IllegalArgumentException(HOSTNAME_KEY);
        }
        if (props.getProperty(CONNECTION_KEY).equalsIgnoreCase("socket")) {
            try {
                Integer.valueOf(props.getProperty(PORT_KEY));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(PORT_KEY);
            }
        }
        try {
            Integer.valueOf(props.getProperty(TIMEOUT_KEY));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(TIMEOUT_KEY);
        }
        if (!(props.getProperty(CONNECTION_KEY).equalsIgnoreCase("rmi") ||
                props.getProperty(CONNECTION_KEY).equalsIgnoreCase("socket"))) {
            throw new IllegalArgumentException(CONNECTION_KEY);
        }
        if (!(props.getProperty(UI_KEY).equalsIgnoreCase("gui") ||
                props.getProperty(UI_KEY).equalsIgnoreCase("cli"))) {
            throw new IllegalArgumentException(UI_KEY);
        }
        return true;
    }
}
