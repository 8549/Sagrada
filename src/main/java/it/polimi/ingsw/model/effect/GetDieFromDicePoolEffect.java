package it.polimi.ingsw.model.effect;

public class GetDieFromDicePoolEffect extends Effect{
    @Override
    public boolean perform(Object... args) {
        toolCard.getDieFromDicePool();
        return true;
    }
}