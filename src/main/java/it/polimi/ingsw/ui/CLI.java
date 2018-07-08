package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.beans.value.ChangeListener;
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
    private boolean timeUp;


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

        if (connType.equals(ConnectionType.SOCKET)) {
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
        }

        System.out.print("Enter your username: ");
        username = scanner.next();

        handler.handleLogin(hostName, port, username, connType);

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
        handler.setChosenPatternCard(patterns[which - 1]);

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
    public ClientHandler getClientHandler() {
        return handler;
    }

    @Override
    public ProxyModel getModel() {
        return model;
    }

    @Override
    public void setModelAfterReconnecting(ProxyModel model) {
        this.model = model;
    }

    @Override
    public void initUI() {
        handler = new ClientHandler(this);
        showLogin();
    }

    @Override
    public void startGame() {
        if (listener != null) {
            model.getPlayers().removeListener(listener);
        }
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
            System.out.println(p.getName() + " " + printFavorTokens(p.getTokens()));
            printWindowPattern(p.getPlayerWindow().getWindowPattern(), p.getPlayerWindow());
        }
        printRoundTrack();
        System.out.print("Draft pool:");
        for (Die d : model.getDraftPool()) {
            System.out.print(" " + printDie(d));
        }
        System.out.print("\nPublic objective cards: ");
        for (ObjCard c : model.getPublicObjectiveCards()) {
            System.out.print(String.format("%s, ", c.getName()));
        }
        System.out.println("\nTool cards: ");
        for (ToolCard c : model.getToolCards()) {
            System.out.println(String.format("%s (cost: %d, has %d tokens on it)", c.getName(), c.getCost(), c.getTokens()));
        }
        System.out.println("\nYour private objective card: " + model.getMyself().getPrivateObjectiveCard().getName());
        System.out.println("Your remaining tokens: " + printFavorTokens(model.getMyself().getTokens()));
        printWindowPattern(model.getMyself().getPlayerWindow().getWindowPattern(), model.getMyself().getPlayerWindow());
    }

    /**
     * Helper method to get a CLI-suitable representation of a {@link Die}
     *
     * @param d the die you want to get a representation of
     * @return a string representation of the supplied d
     */
    private String printDie(Die d) {
        int unicodeNumber = 9856 + d.getNumber() - 1;
        return d.getColor().escapeString() + (char) unicodeNumber + SagradaColor.RESET;
    }

    /**
     * Helper method that checks if the time is up, using a variable set by the {@link ClientHandler}, and possibly
     * flushing the CLI input buffer. This method is used by {@link CLI#update()} to interrupt itself midway, so the
     * client doesn't go through the burden of getting all the user's input only to discard it and inform the user that
     * he couldn't make the move
     *
     * @return true if the time is up, false otherwise
     */
    private boolean isTimeUp() {
        if (timeUp) {
            flushConsole();
            return true;
        }
        return false;
    }

    /**
     * Helper method to flush the console input by repeatedly consuming tokens unitl there isn't any left
     */
    private void flushConsole() {
        while (scanner.hasNext()) {
            scanner.next();
        }
    }

    @Override
    public void myTurnStarted() {
        timeUp = false;
        askChoice();
    }

    /**
     * Ask the users what they want to do, it is an entrypoint for a single move (e.g. placing a die, using a tool card
     * or ending their turn
     */
    private void askChoice() {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("You can place a Die, use a Tool card or End your turn; enter the first character of your choice: ");
            String choice = scanner.next().toUpperCase();
            if (isTimeUp()) {
                return;
            }
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
                            if (isTimeUp()) {
                                return;
                            }
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
                            if (isTimeUp()) {
                                return;
                            }
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
                            if (isTimeUp()) {
                                return;
                            }
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
                    handler.handlePlacement(model.getDraftPool().get(which - 1), i - 1, j - 1);
                    break;
                case "E":
                    validChoice = true;
                    System.out.println("Ending your turn...");
                    handler.passTurn();
                    break;
                case "T":
                    validChoice = true;
                    int whichTool = -1;
                    System.out.print("Which tool card [1-" + model.getToolCards().size() + "]? ");
                    boolean validTool = false;
                    while (!validTool) {
                        if (scanner.hasNextInt()) {
                            whichTool = scanner.nextInt();
                            if (isTimeUp()) {
                                return;
                            }
                            if (whichTool > 0 && whichTool <= model.getToolCards().size()) {
                                validTool = true;
                            } else {
                                System.out.print("Please choose a valid tool card [1-" + model.getToolCards().size() + "]: ");
                            }
                        } else {
                            System.out.print("Please choose a valid tool card [1-" + model.getToolCards().size() + "]: ");
                            scanner.next();
                        }
                    }
                    handler.useTool(model.getToolCards().get(whichTool - 1));
                    break;
                default:
                    System.err.println("Invalid choice!");
            }
        }
    }

    @Override
    public void myTurnEnded() {
        timeUp = true;
        flushConsole();
        System.out.println("Time for your turn is up. Be quicker!");
    }

    @Override
    public void nextMove() {
        System.out.println("You can do something else!");
        update();
        askChoice();
    }

    @Override
    public void toolAvailable(boolean isAvailable) {
        if (!isAvailable) {
            System.err.println("You can't use that now!");
        } else {
            System.out.println("You can use that Tool Card.  Go on!");
        }
    }

    @Override
    public void playerDisconnected(Player p) {
        System.out.println(String.format("Player %s has disconnected", p.getName()));
    }

    @Override
    public void initBoard() {
        timeUp = false;
        System.out.println("The game is starting! You will play against:");
        for (Player p : model.getPlayers()) {
            System.out.println(String.format("%s (%s favor tokens)", p.getName(), printFavorTokens(p.getPlayerWindow().getWindowPattern().getDifficulty())));
        }
        System.out.println("You:");
        System.out.println(String.format("%s (%s favor tokens)", model.getMyself().getName(), printFavorTokens(model.getMyself().getPlayerWindow().getWindowPattern().getDifficulty())));
        System.out.println("Your private objective card will be: " + model.getMyself().getPrivateObjectiveCard().getName());

        ChangeListener<Number> listener1 = (observable, oldValue, newValue) -> System.out.println(String.format("Round %d ended. round %d is starting!", oldValue.intValue(), newValue.intValue()));
        ChangeListener<Number> listener = (observable, oldValue, newValue) -> update();
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
        askChoice();
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
                    if (isTimeUp()) {
                        return;
                    }
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
                    if (isTimeUp()) {
                        return;
                    }
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
            if (model.getMyself().getPlayerWindow().getCellAt(i - 1, j - 1).isEmpty()) {
                System.err.println("There's no die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        handler.sendDieFromWP(model.getMyself().getPlayerWindow().getCellAt(i - 1, j - 1).getDie(), i - 1, j - 1);
    }

    @Override
    public void chooseDieFromDraftPool() {
        System.out.print("This is the current draft pool: ");
        for (Die d : model.getDraftPool()) {
            System.out.print(printDie(d) + " ");
        }
        int which = -1;
        System.out.print("\nPlease choose a die [1-" + model.getDraftPool().size() + "]: ");
        boolean validDie = false;
        while (!validDie) {
            if (scanner.hasNextInt()) {
                which = scanner.nextInt();
                if (isTimeUp()) {
                    return;
                }
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
        printRoundTrack();
        int i = -1;
        System.out.print("Which round you want to pick a die from [1-" + roundTrack.getRoundCounter() + "]? ");
        boolean validChoice = false;
        while (!validChoice) {
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                if (isTimeUp()) {
                    return;
                }
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
                if (isTimeUp()) {
                    return;
                }
                if (j >= 0 && j <= model.getRoundTrack().getDiceNumberAtRound(i)) {
                    validChoice = true;
                    handler.sendDieFromRT(roundTrack.getDieAt(i - 1, j - 1), i - 1);
                } else {
                    System.out.print("Please enter a valid choice. ");
                }
            } else {
                System.out.print("Please enter a valid choice. ");
                scanner.next();
            }
        }
    }

    /**
     * This method prints the Round Track
     */
    private void printRoundTrack() {
        System.out.println("Round Track: ");
        for (int i = 0; i < model.getRoundTrack().getRoundCounter(); i++) {
            System.out.print((i + 1) + ") ");
            for (int j = 0; j < model.getRoundTrack().getDiceNumberAtRound(i); j++) {
                System.out.print(printDie(model.getRoundTrack().getDieAt(i, j)) + " ");
            }
            System.out.print("\n");
        }
    }

    @Override
    public void chooseIfDecrease() {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Do you want to Increase or Decrease the die? Type the first letter of your choice: ");
            String s = scanner.next().toUpperCase();
            if (isTimeUp()) {
                return;
            }
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
    public void chooseIfPlaceDie(int number) {
        boolean validChoice = false;
        while (!validChoice) {
            System.out.print("Do you want to Place the die or put it Back to the draft pool? Type the first letter of your choice: ");
            String s = scanner.next().toUpperCase();
            if (isTimeUp()) {
                return;
            }
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
                if (isTimeUp()) {
                    return;
                }
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
    public void setValue(SagradaColor color) {
        int i;
        System.out.print("Choose the new value for the " + color.toString().toLowerCase() + " die [" + Die.MIN + "-" + Die.MAX + "]: ");
        boolean validChoice = false;
        while (!validChoice) {
            if (scanner.hasNextInt()) {
                i = scanner.nextInt();
                if (isTimeUp()) {
                    return;
                }
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
    public void setNewCoordinates() {
        int i = -1, j = -1;
        boolean validDie = false;
        while (!validDie) {
            System.out.print("Please enter the new coordinate row [1-" + WindowPattern.ROWS + "]: ");
            boolean validRow = false;
            while (!validRow) {
                if (scanner.hasNextInt()) {
                    i = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
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
            System.out.print("Please enter the new coordinate column [1-" + WindowPattern.COLUMNS + "]: ");
            boolean validCol = false;
            while (!validCol) {
                if (scanner.hasNextInt()) {
                    j = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
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
            if (!model.getMyself().getPlayerWindow().getCellAt(i - 1, j - 1).isEmpty()) {
                System.err.println("There's already a die at the given coordinates! Please choose an empty cell.");
            } else {
                validDie = true;
            }
        }
        handler.sendNewCoordinates(i - 1, j - 1);
    }

    @Override
    public void chooseTwoDice() {
        int firstDieRow = -1, firstDieColumn = -1;
        boolean validDie = false;
        while (!validDie) {
            System.out.print("(First die) Please enter the chosen die row [1-" + WindowPattern.ROWS + "]: ");
            boolean validRow = false;
            while (!validRow) {
                if (scanner.hasNextInt()) {
                    firstDieRow = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (firstDieRow > 0 && firstDieRow <= WindowPattern.ROWS) {
                        validRow = true;
                    } else {
                        System.out.print("(First die) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    }
                } else {
                    System.out.print("(First die) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    scanner.next();
                }
            }
            System.out.print("(First die) Please enter the chosen die column [1-" + WindowPattern.COLUMNS + "]: ");
            boolean validCol = false;
            while (!validCol) {
                if (scanner.hasNextInt()) {
                    firstDieColumn = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (firstDieColumn > 0 && firstDieColumn <= WindowPattern.COLUMNS) {
                        validCol = true;
                    } else {
                        System.out.print("(First die) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    }
                } else {
                    System.out.print("(First die) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    scanner.next();
                }
            }
            if (model.getMyself().getPlayerWindow().getCellAt(firstDieRow - 1, firstDieColumn - 1).isEmpty()) {
                System.err.println("(First die) There's no die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        if (isTimeUp()) {
            return;
        }
        int secondDieRow = -1, secondDieColumn = -1;
        validDie = false;
        while (!validDie) {
            System.out.print("(Second die) Please enter the chosen die row [1-" + WindowPattern.ROWS + "]: ");
            boolean validRow = false;
            while (!validRow) {
                if (scanner.hasNextInt()) {
                    secondDieRow = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (secondDieRow > 0 && secondDieRow <= WindowPattern.ROWS) {
                        validRow = true;
                    } else {
                        System.out.print("(Second die) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    }
                } else {
                    System.out.print("(Second die) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    scanner.next();
                }
            }
            System.out.print("(Second die) Please enter the chosen die column [1-" + WindowPattern.COLUMNS + "]: ");
            boolean validCol = false;
            while (!validCol) {
                if (scanner.hasNextInt()) {
                    secondDieColumn = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (secondDieColumn > 0 && secondDieColumn <= WindowPattern.COLUMNS) {
                        validCol = true;
                    } else {
                        System.out.print("(Second die) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    }
                } else {
                    System.out.print("(Second die) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    scanner.next();
                }
            }
            if (model.getMyself().getPlayerWindow().getCellAt(secondDieRow - 1, secondDieColumn - 1).isEmpty()) {
                System.err.println("(Second die) There's no die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        handler.sendTwoDice(firstDieRow, firstDieColumn, secondDieRow, secondDieColumn);
    }

    @Override
    public void chooseTwoCoordinates() {
        int firstDieRow = -1, firstDieColumn = -1;
        boolean validDie = false;
        while (!validDie) {
            System.out.print("(First cell) Please enter the chosen cell row [1-" + WindowPattern.ROWS + "]: ");
            boolean validRow = false;
            while (!validRow) {
                if (scanner.hasNextInt()) {
                    firstDieRow = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (firstDieRow > 0 && firstDieRow <= WindowPattern.ROWS) {
                        validRow = true;
                    } else {
                        System.out.print("(First cell) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    }
                } else {
                    System.out.print("(First cell) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    scanner.next();
                }
            }
            System.out.print("(First cell) Please enter the chosen cell column [1-" + WindowPattern.COLUMNS + "]: ");
            boolean validCol = false;
            while (!validCol) {
                if (scanner.hasNextInt()) {
                    firstDieColumn = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (firstDieColumn > 0 && firstDieColumn <= WindowPattern.COLUMNS) {
                        validCol = true;
                    } else {
                        System.out.print("(First cell) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    }
                } else {
                    System.out.print("(First cell) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    scanner.next();
                }
            }
            if (!model.getMyself().getPlayerWindow().getCellAt(firstDieRow - 1, firstDieColumn - 1).isEmpty()) {
                System.err.println("(First cell) There's already a die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        if (isTimeUp()) {
            return;
        }
        int secondDieRow = -1, secondDieColumn = -1;
        validDie = false;
        while (!validDie) {
            System.out.print("(Second cell) Please enter the chosen cell row [1-" + WindowPattern.ROWS + "]: ");
            boolean validRow = false;
            while (!validRow) {
                if (scanner.hasNextInt()) {
                    secondDieRow = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (secondDieRow > 0 && secondDieRow <= WindowPattern.ROWS) {
                        validRow = true;
                    } else {
                        System.out.print("(Second cell) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    }
                } else {
                    System.out.print("(Second cell) Please choose a valid row [1-" + WindowPattern.ROWS + "]: ");
                    scanner.next();
                }
            }
            System.out.print("(Second cell) Please enter the chosen cell column [1-" + WindowPattern.COLUMNS + "]: ");
            boolean validCol = false;
            while (!validCol) {
                if (scanner.hasNextInt()) {
                    secondDieColumn = scanner.nextInt();
                    if (isTimeUp()) {
                        return;
                    }
                    if (secondDieColumn > 0 && secondDieColumn <= WindowPattern.COLUMNS) {
                        validCol = true;
                    } else {
                        System.out.print("(Second cell) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    }
                } else {
                    System.out.print("(Second cell) Please choose a valid column [1-" + WindowPattern.COLUMNS + "]: ");
                    scanner.next();
                }
            }
            if (model.getMyself().getPlayerWindow().getCellAt(secondDieRow - 1, secondDieColumn - 1).isEmpty()) {
                System.err.println("(Second cell) There's already a die at the given coordinates!");
            } else {
                validDie = true;
            }
        }
        handler.sendTwoNewCoordinates(firstDieRow, firstDieColumn, secondDieRow, secondDieColumn);
    }

    @Override
    public void toolEnded(boolean success) {
        if (success) {
            System.out.println("The tool card succeeded!");
        } else {
            System.err.println("The tool card failed!");
        }
    }

    /**
     * Helper method to print an arbitrary number of favor tokens
     *
     * @param n the number of tokens to print
     * @return a string consisting of n chars
     */
    private String printFavorTokens(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(FAVOR_TOKEN_CHAR);
        }
        return s.toString();
    }

    /**
     * Helper method to print a scheme card
     *
     * @param p the window pattern to print
     */
    private void printWindowPattern(WindowPattern p) {
        printWindowPattern(p, new PlayerWindow());
    }

    /**
     * Helper method to print a scheme card with possibly the dice placed by the player onto it
     *
     * @param p the window pattern to print
     * @param w the player's window (it contains the dice of the player)
     */
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
