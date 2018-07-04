package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class FlipDieEffect extends Effect{

    public FlipDieEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        toolCard.getBoard().getDraftPool().remove(die);
        die.flip();
        toolCard.getBoard().getDraftPool().add(die);
        toolCard.setResponse(true);
    }
}
