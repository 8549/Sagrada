package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CLI implements UI {
    private static final char FAVOR_TOKEN_CHAR = '\u2022';
    private static final char DOWN_RIGHT = '\u250C';
    private static final char DOWN_LEFT = '\u2510';
    private static final char DOWN_HORIZONTAL = '\u252C';
    private static final char HORIZONTAL = '\u2500';
    private static final char UP_RIGHT = '\u2514';
    private static final char UP_LEFT = '\u2518';
    private static final char UP_HORIZONTAL = '\u2534';
    private static final char VERTICAL_RIGHT = '\u251C';
    private static final char VERTICAL_LEFT = '\u2524';
    private static final char VERTICAL_HORIZONTAL = '\u253C';
    private static final char VERTICAL = '\u2502';

    private final Scanner scanner = new Scanner(System.in);
    private ClientHandler handler;
    private ProxyModel model;
    private ListChangeListener listener;


    @Override
    public void failedLogin() {
        System.err.println("Login failed, retrying");
        showLogin();
    }

    @Override
    public void showLogin() {
        String hostName;
        int port = 0;
        String username;
        ConnectionType connType = ConnectionType.SOCKET;

        boolean validHostName = false;
        System.out.print("Enter the server address: ");
        hostName = scanner.next();
        while (!validHostName) {
            try {
                InetAddress.getByName(hostName);
                validHostName = true;
            } catch (UnknownHostException e) {
                System.out.print("Please enter a valid and reachable server address: ");
                hostName = scanner.next();
            }
        }

        boolean validPort = false;
        System.out.print("Enter the server port: ");
        while (!validPort) {
            if (scanner.hasNextInt()) {
                port = scanner.nextInt();
                if (port > 0 && port < 0xFFFF) {
                    validPort = true;
                } else {
                    System.out.print("Please choose a valid port number: ");
                }
            } else {
                System.out.print("Please choose a valid port number: ");
                scanner.next();
            }
        }

        System.out.print("Enter your username: ");
        username = scanner.next();

        boolean validConn = false;
        System.out.print("Choose a connection type (SOCKET or RMI): ");
        String c = scanner.next();
        while (!validConn) {
            try {
                connType = ConnectionType.valueOf(c.toUpperCase());
                validConn = true;
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.print("Please choose either SOCKET or RMI: ");
                c = scanner.next();
            }
        }

        try {
            handler.handleLogin(hostName, port, username, connType);
        } catch (IOException e) {
            System.err.println("Error in connection: " + e.getMessage());
        }
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {
        WindowPattern[] patterns = {one.getFront(), one.getBack(), two.getFront(), two.getBack()};
        int i = 1;
        for (WindowPattern p : patterns) {
            System.out.println(String.format("%d) %s, difficulty %d", i++, p.getName(), p.getDifficulty()));
            printWindowPattern(p);
        }
        System.out.print("Please choose your window pattern: ");
        int which = scanner.nextInt();
        try {
            handler.setChosenPatternCard(patterns[which-1]);
        } catch (IOException e) {
            System.err.println("There was a connection error");
        }
    }

    @Override
    public void showLoggedInUsers() {
        System.out.println("You are logged in.");
        if (model.players.size() > 0) {
            System.out.println("Already logged in users:");
        }
        for (Player p : model.players) {
            System.out.println(p.getName());
        }
        listener = new WeakListChangeListener<>(new ListChangeListener<Player>() {
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
        });
        model.players.addListener(listener);
    }

    @Override
    public void setProxyModel(ProxyModel model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        showLogin();
    }

    @Override
    public void startGame() {
        model.players.removeListener(listener);
    }


    @Override
    public void update() {
        System.out.println(String.format("It's turn %d of round %d", model.getCurrentTurn() + 1, model.getCurrentRound() + 1));
        for (Player p : model.players) {
            System.out.println(p.getName());
            printWindowPattern(p.getPlayerWindow().getWindowPattern(), p.getPlayerWindow());
        }
        System.out.print("\nDraft pool:");
        for (Die d : model.draftPool) {
            System.out.print(" " + d.toCLI());
        }
        System.out.print("\nPublic objective cards: ");
        for (PublicObjectiveCard c : model.publicObjectiveCards) {
            System.out.print(String.format("%s, ", c.getName()));
        }
        System.out.println("\nYour private objective card: " + model.myself.getPrivateObjectiveCard().getName());
        printWindowPattern(model.myself.getPlayerWindow().getWindowPattern(), model.myself.getPlayerWindow());
    }

    @Override
    public void myTurnStarted() {
        update();
        System.out.println(String.format("It's your turn! (Turn number %d of round %d)", model.getCurrentTurn(), model.getCurrentRound()));
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("You can place a Die, use a Tool card or Pass; enter the initial character of your choice: ");
            String choice = scanner.next().toUpperCase();
            switch (choice) {
                case "D":
                    validChoice = true;
                    int which = -1;
                    int i = -1;
                    int j = -1;
                    System.out.print("Which die [1-" + model.draftPool.size() + "]? ");
                    boolean validDie = false;
                    while (!validDie) {
                        if (scanner.hasNextInt()) {
                            which = scanner.nextInt();
                            if (which > 0 && which <= model.draftPool.size()) {
                                validDie = true;
                            } else {
                                System.out.print("Please choose a valid die [1-" + model.draftPool.size() + "]: ");
                            }
                        } else {
                            System.out.print("Please choose a valid die [1-" + model.draftPool.size() + "]: ");
                            scanner.next();
                        }
                    }
                    System.out.print("Which row do you want to place it [1-" + WindowPattern.ROWS + "]: ");
                    boolean validRow = false;
                    while (!validRow) {
                        if (scanner.hasNextInt()) {
                            i = scanner.nextInt();
                            if (i > 0 && i <= WindowPattern.ROWS) {
                                validRow = true;
                            } else {
                                System.out.print("Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                            }
                        } else {
                            System.out.print("Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                            scanner.next();
                        }
                    }
                    System.out.print("Which column do you want to place it [1-" + WindowPattern.COLUMNS + "]: ");
                    boolean validCol = false;
                    while (!validCol) {
                        if (scanner.hasNextInt()) {
                            j = scanner.nextInt();
                            if (j > 0 && j <= WindowPattern.COLUMNS) {
                                validCol = true;
                            } else {
                                System.out.print("Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                            }
                        } else {
                            System.out.print("Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                            scanner.next();
                        }
                    }
                    handler.handlePlacement(model.draftPool.get(which - 1), i - 1, j - 1);
                    break;
                case "P":
                    validChoice = true;
                    System.out.println("Ok, you're passing...");
                    // handler.handlePass();
                    break;
                case "T":
                    validChoice = true;
                    System.out.println("Coming soon...");
                    // handler.handleToolCard();
                    break;
                default:
                    System.err.println("Invalid choice!");
            }
        }
    }

    @Override
    public void myTurnEnded() {
        System.out.println("Your turn has ended.");
    }

    @Override
    public void playerDisconnected(Player p) {
        System.out.println(String.format("Player %s has disconnected", p.getName()));
    }

    @Override
    public void initBoard() {
        System.out.println("The game is starting! You will play against:");
        for (Player p : model.players) {
            System.out.println(String.format("%s (%s favor tokens)", p.getName(), printFavorTokens(p.getPlayerWindow().getWindowPattern().getDifficulty())));
            printWindowPattern(p.getPlayerWindow().getWindowPattern());
        }
        System.out.println("You:");
        System.out.println(String.format("%s (%s favor tokens)", model.myself.getName(), printFavorTokens(model.myself.getPlayerWindow().getWindowPattern().getDifficulty())));
        printWindowPattern(model.myself.getPlayerWindow().getWindowPattern());
        System.out.println("Your private objective card will be: " + model.myself.getPrivateObjectiveCard().getName());

        model.draftPool.addListener(new WeakListChangeListener<>(new ListChangeListener<Die>() {
            @Override
            public void onChanged(Change<? extends Die> c) {
                while (c.next()) {
                    update();
                    if (c.wasAdded()) {
                        /*for (Die d : c.getAddedSubList()) {
                            System.out.println("Die " + d.toCLI() + " was added");
                        }*/
                    } else if (c.wasRemoved()) {
                        for (Die d : c.getRemoved()) {
                            System.out.println("Die " + d.toCLI() + " was removed");
                        }
                    }
                }
            }
        }));
        model.currentRoundProperty().addListener(new WeakChangeListener<>(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(String.format("Turn changed %d --> %d", oldValue.intValue(), newValue.intValue()));
            }
        }));
        model.currentTurnProperty().addListener(new WeakChangeListener<>(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(String.format("Turn changed %d --> %d", oldValue.intValue(), newValue.intValue()));
            }
        }));
    }

    private String printFavorTokens(int n) {
        String s = "";
        for (int i = 0; i < n; i++) {
            s += FAVOR_TOKEN_CHAR;
        }
        return s;
    }

    private void printWindowPattern(WindowPattern p) {
        printWindowPattern(p, new PlayerWindow());
    }

    private void printWindowPattern(WindowPattern p, PlayerWindow w) {
        StringBuilder b = new StringBuilder();
        int k = 0;
        int l = 0;
        int gridMaxRows = 2 * WindowPattern.ROWS + 1;
        int gridMaxCols = 2 * WindowPattern.COLUMNS + 1;
        for (int i = 0; i < gridMaxRows; i++) {
            for (int j = 0; j < gridMaxCols; j++) {
                if (i == 0) {
                    if (j == 0) {
                        b.append(DOWN_RIGHT);
                    } else if (j == gridMaxCols - 1) {
                        b.append(DOWN_LEFT);
                    } else if (j % 2 == 0) {
                        b.append(DOWN_HORIZONTAL);
                    } else if (j % 2 == 1) {
                        b.append(HORIZONTAL);
                    }
                } else if (i == gridMaxRows - 1) {
                    if (j == 0) {
                        b.append(UP_RIGHT);
                    } else if (j == gridMaxCols - 1) {
                        b.append(UP_LEFT);
                    } else if (j % 2 == 0) {
                        b.append(UP_HORIZONTAL);
                    } else if (j % 2 == 1) {
                        b.append(HORIZONTAL);
                    }
                } else if (i % 2 == 0) {
                    if (j == 0) {
                        b.append(VERTICAL_RIGHT);
                    } else if (j == gridMaxCols - 1) {
                        b.append(VERTICAL_LEFT);
                    } else if (j % 2 == 1) {
                        b.append(HORIZONTAL);
                    } else if (j % 2 == 0) {
                        b.append(VERTICAL_HORIZONTAL);
                    }
                } else if (i % 2 == 1) {
                    if (j == 0) {
                        b.append(VERTICAL);
                    } else if (j == gridMaxCols - 1) {
                        b.append(VERTICAL);
                    } else if (j % 2 == 1) {
                        if (w.getCellAt(k, l).isEmpty()) {
                            b.append(p.getConstraints()[k][l].toCLI());
                        } else {
                            b.append(w.getCellAt(k, l).getDie().toCLI());
                        }
                        l++;
                        if (l % WindowPattern.COLUMNS == 0) {
                            l = 0;
                            k++;
                        }
                    } else if (j % 2 == 0) {
                        b.append(VERTICAL);
                    }
                }
            }
            b.append("\n");
        }
        System.out.println(b.toString());
    }

    @Override
    public void setHandler(ClientHandler ch){
        handler = ch;
    }
}
