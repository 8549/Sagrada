package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceBag {
    private List<Die> dice;

    public DiceBag() {
        dice = new ArrayList<Die>();
        for (SagradaColor color : SagradaColor.values()) {
            for (int i = 0; i < 18; i++) {
                dice.add(new Die(color.getColor()));
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
}