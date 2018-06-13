package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private Player startPlayer;
    private List<Die> draftPool;
    private List<Turn> turns;
    private List<Player> players;
    private int number;
    private int currentTurn;

    public Round(List<Player> players, int number) {
        this.number = number;
        this.players = players;
        setTurns();
        startPlayer = players.get(0);
        currentTurn = 0;
        draftPool = new ArrayList<>();
        for (int i = 0; i < (players.size() * 2 + 1); i++)
            draftPool.add(DiceBag.draftDie());
    }
    public Turn getTurn(){ return turns.get(currentTurn);}

    public void setTurns() {
        turns = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            Turn turn = new Turn(players.get(i), 1);
            turns.add(i, turn);
        }
        int num = players.size();
        for (int i = (num - 1); i >= 0; i--) {
            Turn turn = new Turn(players.get(i), 2);
            turns.add(num, turn);
            num++;
        }

    }

    public void removeTurn(Player player, int turn) {
        int firstTurn = 0;
        int secondTurn = 0;
        for (int i = 0; i < turns.size(); i++) {
            if (turns.get(i).getPlayer().equals(player)) {
                if (i < turns.size() / 2) {
                    firstTurn = i;
                } else {
                    secondTurn = i;
                }
            }
        }
        turns.remove(secondTurn);
        if (turn == 1) {
            turns.remove(firstTurn);
        }
    }

    public void playRound() {
        int i = 0;
        for (Turn turn : turns) {


            //turn.getPlayer().getPlayerWindow().addDie();
            turn.setDiePlaced();


            //or use tool card
            /*if (toolCardUsed) {
                turn.setToolCardUsed();
            }
            //or place die
            if (turnDoubled) {
                turns.remove(players.size() * 2 - i - 1);
                turns.add(i + 1, turn);
            }*/
        }
        currentTurn++;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

}
