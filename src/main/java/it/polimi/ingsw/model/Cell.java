package it.polimi.ingsw.model;


public class Cell {
    private Die die;
    private boolean empty;

    public Cell() {

        empty = true;
    }

    public boolean isEmpty() {

        return empty;
    }

    public boolean setDie(Die die) {
        if (empty) {
            this.die = die;
            empty = false;
            return true;
        } else {
            return false;
        }

    }

    public Die getDie() {

        return die;
    }

    /**
     * Remove die if present
     *
     * @return True of the die is removed, otherwise false
     */
    public boolean removeDie() {
        if (empty)
            return false;
        else {
            empty = true;
            return true;
        }


    }
}
