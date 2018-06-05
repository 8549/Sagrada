package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WindowPattern;
import it.polimi.ingsw.network.ConnectionType;
import it.polimi.ingsw.network.client.ClientHandler;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;

import java.io.IOException;
import java.util.Scanner;

public class CLI implements UI {
    private final Scanner scanner = new Scanner(System.in);
    private ClientHandler handler;
    private ProxyModel model;
    private ListChangeListener listener;


    @Override
    public void showLogin() {
        String hostName;
        int port;
        String username;
        ConnectionType connType;

        System.out.print("Enter the server address: ");
        hostName = scanner.next();
        System.out.print("Enter the server port: ");
        port = scanner.nextInt();
        System.out.print("Enter your username: ");
        username = scanner.next();
        System.out.print("Choose a connection type (SOCKET or RMI): ");
        connType = ConnectionType.valueOf(scanner.next().toUpperCase());

        try {
            handler.handleLogin(hostName, port, username, connType);
        } catch (IOException e) {
            System.err.println("Error in connection: " + e.getMessage());
        }
        // if successful, showLoggedInUsers
        // else, show error and exit or retry
    }

    @Override
    public void showPatternCardsChooser(PatternCard one, PatternCard two) {
        WindowPattern[] patterns = {one.getFront(), one.getBack(), two.getFront(), two.getBack()};
        int i = 1;
        for (WindowPattern p : patterns) {
            System.out.println(String.format("%d) %s, difficulty %d", i++, p.getName(), p.getDifficulty()));
            printWindowPattern(p);
        }
        System.out.print("Plase choose your window pattern: ");
        int which = scanner.nextInt();
        handler.setChosenPatternCard(patterns[which]);
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
    public void launch() {
        showLogin();
    }

    @Override
    public void startGame() {
        model.players.removeListener(listener);
        System.out.println("The game is starting! Players for this match will be: ");
        int i;
        for (i = 0; i < model.players.size() - 1; i++) {
            System.out.print(model.players.get(i).getName() + ", ");
        }
        System.out.println(model.players.get(i).getName());
    }

    public void printWindowPattern(WindowPattern p) {
        StringBuilder b = new StringBuilder();
        int k = 0;
        int l = 0;
        int gridMaxRows = 2 * WindowPattern.ROWS + 1;
        int gridMaxCols = 2 * WindowPattern.COLUMNS + 1;
        for (int i = 0; i < gridMaxRows; i++) {
            for (int j = 0; j < gridMaxCols; j++) {
                if (i == 0) {
                    if (j == 0) {
                        // print left down beginning
                        b.append((char) 9484);
                    } else if (j == gridMaxCols - 1) {
                        // print right down end
                        b.append((char) 9488);
                    } else if (j % 2 == 0) {
                        // print T intersection
                        b.append((char) 9516);
                    } else if (j % 2 == 1) {
                        // print top line
                        b.append((char) 9472);
                    }
                } else if (i == gridMaxRows - 1) {
                    if (j == 0) {
                        // print left top end
                        b.append((char) 9492);
                    } else if (j == gridMaxCols - 1) {
                        // print right top end
                        b.append((char) 9496);
                    } else if (j % 2 == 0) {
                        // print _|_ intersection
                        b.append((char) 9524);
                    } else if (j % 2 == 1) {
                        // print bottom line
                        b.append((char) 9472);
                    }
                } else if (i % 2 == 0) {
                    if (j == 0) {
                        // print |-
                        b.append((char) 9500);
                    } else if (j == gridMaxCols - 1) {
                        // print -|
                        b.append((char) 9508);
                    } else if (j % 2 == 1) {
                        // print middle line
                        b.append((char) 9472);
                    } else if (j % 2 == 0) {
                        //print +
                        b.append((char) 9532);
                    }
                } else if (i % 2 == 1) {
                    if (j == 0) {
                        // print left |
                        b.append((char) 9474);
                    } else if (j == gridMaxCols - 1) {
                        // print right |
                        b.append((char) 9474);
                    } else if (j % 2 == 1) {
                        // print constraint
                        b.append(p.getConstraints()[k][l].toCLI());
                        l++;
                        if (l % WindowPattern.COLUMNS == 0) {
                            l = 0;
                            k++;
                        }
                    } else if (j % 2 == 0) {
                        //print middle |
                        b.append((char) 9474);
                    }
                }
            }
            b.append("\n");
        }
        System.out.println(b.toString());
    }

    public void setHandler(ClientHandler ch){
        handler = ch;
    }
}
