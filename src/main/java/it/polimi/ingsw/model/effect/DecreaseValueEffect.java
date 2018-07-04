package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class DecreaseValueEffect extends Effect {

    public DecreaseValueEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        boolean decrease = (boolean) args[1];
        if (decrease) {
            toolCard.getBoard().getDraftPool().remove(die);
            die.decrease();
            toolCard.getBoard().getDraftPool().add(die);
        }
        toolCard.setResponse(true);
    }
}
