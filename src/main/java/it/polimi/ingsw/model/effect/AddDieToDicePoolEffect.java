package it.polimi.ingsw.model.effect;

import it.polimi.ingsw.model.DiceBag;
import it.polimi.ingsw.model.Die;

public class AddDieToDicePoolEffect extends Effect {


    @Override
    public boolean perform(Object... args) {
        Die die = (Die) args[0];
        return toolCard.addDieToDiceBag(die);
    }
}
