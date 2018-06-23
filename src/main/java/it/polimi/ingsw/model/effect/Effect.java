package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.ToolCard;

public abstract class Effect {
    ToolCard toolCard;
    public abstract boolean perform(Object... args);
}
