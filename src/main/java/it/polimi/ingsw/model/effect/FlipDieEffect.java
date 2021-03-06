package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class FlipDieEffect extends Effect{

    public FlipDieEffect(String name){
        this.name = name;
    }

    /**
     * Removes the old die from the draftpool, flip the die, adds it to the draft pool and then update the draft pool
     * @param args die
     */
    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        for(Die die2 : toolCard.getBoard().getDraftPool()){
            if (die.getColor().equals(die2.getColor()) && die.getNumber()==die2.getNumber()){
                toolCard.getBoard().getDraftPool().remove(die);
                break;
            }
        }
        die.flip();
        toolCard.getBoard().getDraftPool().add(die);
        toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());
        toolCard.setResponse(true);
    }
}
