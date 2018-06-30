package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class IncreaseValueEffect extends Effect {

    public IncreaseValueEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        boolean decrease = (boolean) args[1];
        if (!decrease) {
            die.increase();
        }
        toolCard.setResponse(true);
    }
}
