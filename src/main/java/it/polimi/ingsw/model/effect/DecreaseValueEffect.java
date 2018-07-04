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
            for(Die die2 : toolCard.getBoard().getDraftPool()){
                if (die.getColor().equals(die2.getColor()) && die.getNumber()==die2.getNumber()){
                    toolCard.getBoard().getDraftPool().remove(die);
                    break;
                }
            }
            die.decrease();
            toolCard.getBoard().getDraftPool().add(die);
        }
        toolCard.setResponse(true);
    }
}
