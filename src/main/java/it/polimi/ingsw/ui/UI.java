package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.ClientHandler;

public interface UI {
    void failedLogin();

    void showLogin();

    void showPatternCardsChooser(PatternCard one, PatternCard two);

    /**
     * This method is called if the login was successful; it shows currently logged in users, and updates accordingly
     * to other client(s) connecting and disconnecting.
     */
    void showLoggedInUsers();

    void setProxyModel(ProxyModel model);

    void initUI();

    void startGame();

    void update();

    void myTurnStarted();

    void myTurnEnded();

    void playerDisconnected(Player p);

    void initBoard();

    void setHandler(ClientHandler ch);
}
