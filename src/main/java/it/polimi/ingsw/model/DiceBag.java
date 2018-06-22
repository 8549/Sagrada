package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceBag {
    private static List<Die> dice;

    public DiceBag() {
        dice = new ArrayList<Die>();
        for (SagradaColor color : SagradaColor.values()) {
            for (int i = 0; i < 18; i++) {
                dice.add(new Die(color));
            }
        }
    }

    /**
     * Pulls out a die from the dice bag
     *
     * @return A die if there's any, otherwise null
     */
    public Die draftDie() {
        if (dice.isEmpty()) {
            return null;
        } else {
            return dice.remove(new Random().nextInt(dice.size()));

        }
    }

    public int getSize(){
        return dice.size();
    }

    /**
     * Add die if the bag isn't full
     * @param die
     * @return true if the bag wasn't full and the die was added, false otherwise
     */
    public boolean addDie(Die die) {
        if (dice.size() < 90) {
            dice.add(die);
            return true;
        }
        else
            return false;
    }
}
