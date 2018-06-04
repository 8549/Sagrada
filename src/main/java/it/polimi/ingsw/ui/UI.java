package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.PatternCard;

public interface UI {
    void showLogin();

    void showPatternCardsChooser(PatternCard one, PatternCard two);

    void showLoggedInUsers();

    void setProxyModel(ProxyModel model);

    void launch();
}
