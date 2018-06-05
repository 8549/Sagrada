package it.polimi.ingsw.ui;

import it.polimi.ingsw.model.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProxyModel {
    ObservableList<Player> players = FXCollections.observableArrayList();

    public void addPlayers(Player p) {
        players.add(p);
    }

    public void addPlayers(List<Player> l) {
        players.addAll(l);
    }
    public void removePlayer (Player p){
        for(Player player : players){
            if(player.getName().equals(p.getName())){
                players.remove(player);
                break;
            }
        }
    }

    public void resetPlayers(List<Player> l) {
        players.clear();
        players.addAll(l);
    }
}
