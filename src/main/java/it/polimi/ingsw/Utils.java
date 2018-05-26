package it.polimi.ingsw;

import java.util.Random;

public class Utils {
    private static final Random rnd = new Random();

    public static int getRandom(int min, int max){
        return (rnd.nextInt(max + 1 - min) + min);
    }
}
