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
    private Board board;

    public Round(List<Player> players, int number, Board  board) {
        this.number = number;
        this.players = players;
        setTurns();
        startPlayer = players.get(0);
        currentTurn = 0;
        this.board= board;
        draftPool = new ArrayList<>();
        for (int i = 0; i < (players.size() * 2 + 1); i++)
            draftPool.add(board.getDiceBag().draftDie());
        board.setDraftPool(draftPool);
    }

    public Turn getTurn() {
        return turns.get(currentTurn);
    }


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

    /**
     * Adds the second turn of the given turn right after the end of the given turn
     * removes the second turn of the given of turn from it's original place
     * @param turn: the number of the turn which needs to be doubled
     */
    public void doubledTurn(int turn) {
        if (turn < players.size()) {
            turns.add(turn + 1, turns.get(turn));
            turns.get(turn + 1).modifyTurn();
            turns.get(turn + 1).setDieNotPlaced();
            turns.remove(turns.size() - 1 - turn);
        }
    }

    /**
     * Adds one to current turr
     */
    public void passCurrentTurn() {
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

    public List<Die> getDraftPool() {
        return draftPool;
    }

    /**
     * Removes the given die from the draft pool
     * @param die the die that needs to be removed
     */
    public void removeDieFromDraftPool(Die die){
        draftPool.remove(die);
    }

}
