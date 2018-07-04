package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.ToolCard;

import java.util.Objects;

public abstract class Effect {
    ToolCard toolCard;
    String name;
    public abstract void perform(Object... args);

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){ return name;}

    public void setToolCard(ToolCard toolCard){
        this.toolCard= toolCard;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Effect effect = (Effect) o;
        return Objects.equals(name, effect.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }
}
