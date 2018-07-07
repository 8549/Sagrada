package it.polimi.ingsw;

import java.util.Random;

public class Utils {
    private static final Random rnd = new Random();

    /**
     * Return a random number between a lower and an upper bounds, inclusive. Uses {@link Random}
     *
     * @param min the lower bound
     * @param max the upper bound
     * @return the random number between the bounds
     */
    public static int getRandom(int min, int max){
        return (rnd.nextInt(max + 1 - min) + min);
    }
}
