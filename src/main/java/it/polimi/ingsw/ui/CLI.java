package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.beans.value.ChangeListener;
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
        System.out.print("Please choose your window pattern [1- " + patterns.length + "]: ");
        boolean validPatternCard = false;
        int which = 0;
        while (!validPatternCard) {
            if (scanner.hasNextInt()) {
                which = scanner.nextInt();
                if (which > 0 && which <= patterns.length) {
                    validPatternCard = true;
                } else {
                    System.out.print("Please type a valid choice: ");
                }
            } else {
                System.out.print("Please type a valid choice: ");
                scanner.next();
            }
        }
        try {
            handler.setChosenPatternCard(patterns[which-1]);
        } catch (IOException e) {
            System.err.println("There was a connection error");
        }
    }

    @Override
    public void showLoggedInUsers() {
        model = handler.getModel();
        System.out.println("You are logged in.");
        if (model.getPlayers().size() > 0) {
            System.out.println("Already logged in users:");
        }
        for (Player p : model.getPlayers()) {
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
        model.getPlayers().addListener(listener);
    }

    @Override
    public void setProxyModel(ProxyModel model) {
        this.model = model;
    }

    @Override
    public ClientHandler getClientHandler() {
        return handler;
    }

    @Override
    public ProxyModel getModel() {
        return model;
    }

    @Override
    public void initUI() {
        handler = new ClientHandler(this);
        //handler = RunClient.getClientHandler();
        showLogin();
    }

    @Override
    public void startGame() {
        model.getPlayers().removeListener(listener);
    }


    @Override
    public void update() {
        if (model.getMyself().getName().equals(model.getCurrentPlayer().getName())) {
            System.out.println("It's your turn! You have " + model.getTimeout() + " seconds to play.");
        } else {
            System.out.println(String.format("%s is playing! ", model.getCurrentPlayer().getName()));
        }
        System.out.println(String.format("It's turn %d of round %d", model.getCurrentTurn() + 1, model.getCurrentRound()));
        for (Player p : model.getPlayers()) {
            System.out.println(p.getName());
            printWindowPattern(p.getPlayerWindow().getWindowPattern(), p.getPlayerWindow());
        }
        System.out.print("Draft pool:");
        for (Die d : model.getDraftPool()) {
            System.out.print(" " + printDie(d));
        }
        System.out.print("\nPublic objective cards: ");
        for (ObjCard c : model.getPublicObjectiveCards()) {
            System.out.print(String.format("%s, ", c.getName()));
        }
        System.out.println("\nYour private objective card: " + model.getMyself().getPrivateObjectiveCard().getName());
        printWindowPattern(model.getMyself().getPlayerWindow().getWindowPattern(), model.getMyself().getPlayerWindow());
    }

    private String printDie(Die d) {
        int unicodeNumber = 9856 + d.getNumber() - 1;
        return d.getColor().escapeString() + (char) unicodeNumber + SagradaColor.RESET;
    }

    @Override
    public void myTurnStarted() {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("You can place a Die, use a Tool card or Pass; enter the first character of your choice: ");
            String choice = scanner.next().toUpperCase();
            switch (choice) {
                case "D":
                    validChoice = true;
                    int which = -1;
                    int i = -1;
                    int j = -1;
                    System.out.print("Which die [1-" + model.getDraftPool().size() + "]? ");
                    boolean validDie = false;
                    while (!validDie) {
                        if (scanner.hasNextInt()) {
                            which = scanner.nextInt();
                            if (which > 0 && which <= model.getDraftPool().size()) {
                                validDie = true;
                            } else {
                                System.out.print("Please choose a valid die [1-" + model.getDraftPool().size() + "]: ");
                            }
                        } else {
                            System.out.print("Please choose a valid die [1-" + model.getDraftPool().size() + "]: ");
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
                    try {
                        handler.handlePlacement(model.getDraftPool().get(which - 1), i - 1, j - 1);
                    }catch (IOException e){
                        System.err.println("There was a connection error");
                    }
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
        System.out.println("Time for your turn is up. Be quicker!");
    }

    @Override
    public void playerDisconnected(Player p) {
        System.out.println(String.format("Player %s has disconnected", p.getName()));
    }

    @Override
    public void initBoard() {
        System.out.println("The game is starting! You will play against:");
        for (Player p : model.getPlayers()) {
            System.out.println(String.format("%s (%s favor tokens)", p.getName(), printFavorTokens(p.getPlayerWindow().getWindowPattern().getDifficulty())));
            //printWindowPattern(p.getPlayerWindow().getWindowPattern());
        }
        System.out.println("You:");
        System.out.println(String.format("%s (%s favor tokens)", model.getMyself().getName(), printFavorTokens(model.getMyself().getPlayerWindow().getWindowPattern().getDifficulty())));
        //printWindowPattern(model.getMyself().getPlayerWindow().getWindowPattern());
        System.out.println("Your private objective card will be: " + model.getMyself().getPrivateObjectiveCard().getName());

        ChangeListener<Number> listener1 = new WeakChangeListener<>((observable, oldValue, newValue) -> System.out.println(String.format("Round %d ended. round % is starting!", oldValue.intValue(), newValue.intValue())));
        ChangeListener<Number> listener = new WeakChangeListener<>((observable, oldValue, newValue) -> {
            update();
        });
        model.currentRoundProperty().addListener(listener1);
        model.currentTurnProperty().addListener(listener);
    }

    @Override
    public boolean isGUI() {
        return false;
    }

    @Override
    public void wrongMove() {
        System.err.println("Your move was incorrect! Please, try again");
        myTurnStarted();
    }

    @Override
    public void chooseDieFromWindowPattern() {
        int i = -1, j = -1;
        boolean validDie = false;
        while (!validDie) {
            System.out.print("Please enter the chosen die row [1-" + WindowPattern.ROWS + "]: ");
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
            System.out.print("Please enter the chosen die column [1-" + WindowPattern.COLUMNS + "]: ");
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
            if (model.getMyself().getPlayerWindow().getCellAt(i, j).isEmpty()) {
                System.err.println("There's no die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        handler.sendDieFromWP(model.getMyself().getPlayerWindow().getCellAt(i, j).getDie(), i, j);
    }

    @Override
    public void chooseDieFromDraftPool() {
        System.out.print("This is the current draft pool: ");
        for (Die d : model.getDraftPool()) {
            System.out.print(printDie(d));
        }
        int which = -1;
        System.out.print("\nPlease choose a die [1-" + model.getDraftPool().size() + "]: ");
        boolean validDie = false;
        while (!validDie) {
            if (scanner.hasNextInt()) {
                which = scanner.nextInt();
                if (which > 0 && which <= model.getDraftPool().size()) {
                    validDie = true;
                } else {
                    System.out.print("Please choose a valid die [1-" + model.getDraftPool().size() + "]: ");
                }
            } else {
                System.out.print("Please choose a valid die [1-" + model.getDraftPool().size() + "]: ");
                scanner.next();
            }
        }
        handler.sendDieFromDP(model.getDraftPool().get(which - 1));
    }

    @Override
    public void chooseDieFromRoundTrack() {
        RoundTrack roundTrack = model.getRoundTrack();
        // PRINT ROUNDTRACK
        int i = -1;
        System.out.print("Which round you want to pick a die from [1-" + roundTrack.getRoundCounter() + "]? ");
        boolean validChoice = false;
        while (!validChoice) {
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                if (i >= 1 && i <= roundTrack.getRoundCounter()) {
                    validChoice = true;
                } else {
                    System.out.print("Please enter a valid choice. ");
                }
            } else {
                System.out.print("Please enter a valid choice. ");
                scanner.next();
            }
        }
        System.out.print("Which die do you want? ");
        validChoice = false;
        int j;
        while (!validChoice) {
            if (scanner.hasNextInt()) {
                j = scanner.nextInt();
                if (j >= 0 && j <= model.getRoundTrack().getDiceNumberAtRound(i)) {
                    validChoice = true;
                    handler.sendDieFromRT(roundTrack.getDieAt(i, j), i);
                } else {
                    System.out.print("Please enter a valid choice. ");
                }
            } else {
                System.out.print("Please enter a valid choice. ");
                scanner.next();
            }
        }
    }

    @Override
    public void chooseIfDecrease() {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Do you want to Increase or Decrease the die? Type the first letter of your choice: ");
            String s = scanner.next().toUpperCase();
            switch (s) {
                case "I":
                    validChoice = true;
                    handler.sendDecreaseChoice(false);
                    break;
                case "D":
                    validChoice = true;
                    handler.sendDecreaseChoice(true);
                    break;
                default:
                    System.err.println("Invalid choice!");
            }
        }
    }

    @Override
    public void chooseIfPlaceDie() {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Do you want to Place the die or put it Back to the draft pool? Type the first letter of your choice: ");
            String s = scanner.next().toUpperCase();
            switch (s) {
                case "P":
                    validChoice = true;
                    handler.sendPlacementChoice(true);
                    break;
                case "B":
                    validChoice = true;
                    handler.sendPlacementChoice(false);
                    break;
                default:
                    System.err.println("Invalid choice!");
            }
        }
    }

    @Override
    public void chooseToMoveOneDie() {
        int i;
        System.out.print("Do you want to move 1 or 2 dice? ");
        boolean validChoice = false;
        while (!validChoice) {
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                if (i == 1 || i == 2) {
                    validChoice = true;
                    if (i == 2) {
                        handler.sendNumberDiceChoice(true);
                    } else {
                        handler.sendNumberDiceChoice(false);
                    }
                } else {
                    System.out.print("Please enter a valid choice. Do you want to move 1 or 2 dice? ");
                }
            } else {
                System.out.print("Please enter a valid choice. Do you want to move 1 or 2 dice? ");
                scanner.next();
            }
        }
    }

    @Override
    public void setValue() {
        int i;
        System.out.print("Choose the new die value [" + Die.MIN + "-" + Die.MAX + "]: ");
        boolean validChoice = false;
        while (!validChoice) {
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                if (i >= Die.MIN && i <= Die.MAX) {
                    validChoice = true;
                    handler.sendValue(i);
                } else {
                    System.out.print("Please enter a valid choice. ");
                }
            } else {
                System.out.print("Please enter a valid choice. ");
                scanner.next();
            }
        }
    }

    @Override
    public void setOldCoordinates() {
        int i = -1, j = -1;
        boolean validDie = false;
        while (!validDie) {
            System.out.print("Please enter the chosen die row [1-" + WindowPattern.ROWS + "]: ");
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
            System.out.print("Please enter the chosen die column [1-" + WindowPattern.COLUMNS + "]: ");
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
            if (model.getMyself().getPlayerWindow().getCellAt(i, j).isEmpty()) {
                System.err.println("There's no die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        handler.sendOldCoordinates(i, j);

    }

    @Override
    public void setNewCoordinates() {
        int i = -1, j = -1;
        boolean validDie = false;
        while (!validDie) {
            System.out.print("Please enter the chosen die row [1-" + WindowPattern.ROWS + "]: ");
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
            System.out.print("Please enter the chosen die column [1-" + WindowPattern.COLUMNS + "]: ");
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
            if (!model.getMyself().getPlayerWindow().getCellAt(i, j).isEmpty()) {
                System.err.println("There's already a die at the given coordinates! Please choose an empty cell.");
            } else {
                validDie = true;
            }
        }
        handler.sendNewCoordinates(i, j);
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
                            b.append(printDie(w.getCellAt(k, l).getDie()));
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
}
