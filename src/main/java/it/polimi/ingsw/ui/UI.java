package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;

public interface UI {
    void showLogin();

    void showPatternCardsChooser(PatternCard one, PatternCard two);

    void showLoggedInUsers();

    void setProxyModel(ProxyModel model);

    void initUI();

    void startGame();

    void update();

    void myTurnStarted();

    void myTurnEnded();

    void playerDisconnected(Player p);

    void initBoard();
}
