package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class RollDieEffect extends Effect{

    public RollDieEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        die.roll();
        toolCard.getToolCardHandler().setNumberOfPickedDie(die.getNumber());

        for(Die die2 : toolCard.getBoard().getDraftPool()){
            if (die.getColor().equals(die2.getColor()) && die.getNumber()==die2.getNumber()){
                toolCard.getBoard().getDraftPool().remove(die);
                toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
                break;
            }
        }
        toolCard.setResponse(true);
    }
}
