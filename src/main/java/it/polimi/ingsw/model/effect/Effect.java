package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.ToolCard;

public abstract class Effect {
    protected ToolCard toolCard;
    private String name;
    public abstract void perform(Object... args);

    public String getName(){ return name;}
}
