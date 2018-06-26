package it.polimi.ingsw.model.effect;

public class GetDieFromDicePoolEffect extends Effect{
    @Override
    public void perform(Object... args) {
        toolCard.getDieFromDicePool();
        toolCard.setResponse(true);
    }
}