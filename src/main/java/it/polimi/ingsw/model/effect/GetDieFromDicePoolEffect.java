package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.SagradaColor;

public class GetDieFromDicePoolEffect extends Effect{

    public GetDieFromDicePoolEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.getDieFromDicePool();
        toolCard.setResponse(true);
    }
}