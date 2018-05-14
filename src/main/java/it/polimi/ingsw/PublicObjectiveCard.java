package it.polimi.ingsw;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class PublicObjectiveCard extends ObjCard {
    private int numberOfTimes;
    private int points;

    public int setOfShades(int num1, int num2, Cell[][] grid) {
        int numbers[] = new int[6];
        int repetitions;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }

        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                numbers[grid[i][j].getDie().getNumber() - 1]++;
            }
        }
        if (num1 == 0 && num2 == 0) {
            repetitions = numbers[0];
            for (int number : numbers) {
                if (number < repetitions) {
                    repetitions = number;
                }
            }
            return repetitions;
        } else {
            return (numbers[num1 - 1] > numbers[num2 - 1]) ? numbers[num2 - 1] : numbers[num1 - 1];
        }

    }

    public int setOfColors(Cell[][] grid) {
        Map<SagradaColor, Integer> colorMap = new HashMap<>();
        for (SagradaColor color : SagradaColor.values()) {
            colorMap.put(color, 0);
        }
        for (int i = 0; i < WindowPattern.ROWS; i++) {
            for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                SagradaColor key = grid[i][j].getDie().getColor();
                int oldValue = colorMap.get(key);
                colorMap.replace(key, oldValue++);
            }
        }
        return colorMap.values()
                .stream()
                .mapToInt(v -> v)
                .min()
                .orElse(0);

    }

    public int rowNumberVariety(Cell[][] grid) {
        int repetitions = 0;
        for (Cell[] row : grid) {
            if (5 == Arrays.asList(row)
                    .stream()
                    .mapToInt(v -> v.getDie().getNumber())
                    .distinct()
                    .count()) {
                repetitions++;
            }
        }
        return repetitions;
    }


    //TODO
    public int checkObjective(Cell[][] grid) {
        // read from file
        return 0;

    }

    //TODO
    public int getPoints() {
        return 0;
    }
}
