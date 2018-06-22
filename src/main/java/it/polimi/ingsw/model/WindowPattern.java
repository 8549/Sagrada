package it.polimi.ingsw.model;

import java.io.Serializable;

public class WindowPattern implements Serializable {
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

    public PatternConstraint[][] getConstraints() {
        return patternConstraints;
    }

    public String getName() {
        return name;
    }

    public PatternConstraint getConstraint(int row, int column){
        return patternConstraints[row][column];
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
        WindowPattern pattern = (WindowPattern) o;
        return difficulty == pattern.difficulty &&
                name.equals(pattern.name) &&
                PatternConstraint.equals(patternConstraints, pattern.patternConstraints);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
