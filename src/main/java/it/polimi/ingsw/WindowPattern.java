package it.polimi.ingsw;

import java.util.Arrays;
import java.util.Objects;

public class WindowPattern {
    public static final int ROWS = 4;
    public static final int COLUMNS = 5;
    private int difficulty;
    private String name;
    private PatternConstraint[][] patternConstraints;

    public WindowPattern(int difficulty, String name, PatternConstraint[][] patternConstraints) {
        this.difficulty = difficulty;
        this.name = name;
        this.patternConstraints = patternConstraints;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public PatternConstraint[][] getContraints() {
        return patternConstraints;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WindowPattern{");
        builder.append("\ndifficulty=").append(difficulty);
        builder.append(",\nname='").append(name);
        builder.append(",\npatternConstraints= [");
        for (PatternConstraint[] patternConstraintRow : patternConstraints) {
            builder.append("\n[");
            for (PatternConstraint patternConstraint : patternConstraintRow) {
                builder.append(patternConstraint.toString()).append(", ");
            }
            builder.append("]\n");
        }
        builder.append("]\n");
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WindowPattern that = (WindowPattern) o;
        return difficulty == that.difficulty &&
                Objects.equals(name, that.name) &&
                Arrays.equals(patternConstraints, that.patternConstraints);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(difficulty, name);
        result = 31 * result + Arrays.hashCode(patternConstraints);
        return result;
    }
}
