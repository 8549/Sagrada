package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.Die;

public class IncreaseValueEffect extends Effect {

    public IncreaseValueEffect(String name){
        this.name = name;
    }

    /**
     * if the die needs to be decreased
     * Removes the old die from the draftpool, increase the value of the die, adds it to the draft pool and then update the draft pool
     * @param args
     */
    @Override
    public void perform(Object... args) {
        Die die = (Die) args[0];
        boolean decrease = (boolean) args[1];
        if (!decrease) {
            if (die.getNumber() == 6) {
                toolCard.setResponse(false);
            } else {
                for (Die die2 : toolCard.getBoard().getDraftPool()) {
                    if (die.getColor().equals(die2.getColor()) && die.getNumber() == die2.getNumber()) {
                        toolCard.getBoard().getDraftPool().remove(die);
                        break;
                    }
                }
                die.increase();
                toolCard.getBoard().getDraftPool().add(die);
                toolCard.getToolCardHandler().updateDraftPool(toolCard.getBoard().getDraftPool());

                toolCard.setResponse(true);
            }
        }
    }
}
