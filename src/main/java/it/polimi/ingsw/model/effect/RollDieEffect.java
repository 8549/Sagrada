package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class RollDieEffect extends Effect{

    public RollDieEffect(String name){
        this.name = name;
    }


    /**
     * Removes the old die from the draftpool, rolls the die, adds it to the draft pool and then update the draft pool
     * @param args die
     */
    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];

        for(Die die2 : toolCard.getBoard().getDraftPool()){
            if (die.getColor().equals(die2.getColor()) && die.getNumber()==die2.getNumber()){
                toolCard.getBoard().getDraftPool().remove(die);
                toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
                break;
            }
        }
        die.roll();
        toolCard.getToolCardHandler().setNumberOfPickedDie(die.getNumber());
        toolCard.setResponse(true);
    }
}
