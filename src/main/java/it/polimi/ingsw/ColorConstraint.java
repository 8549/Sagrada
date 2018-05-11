package it.polimi.ingsw;

public class ColorConstraint implements PatternConstraint {
    private SagradaColor color;

    public ColorConstraint(SagradaColor color) {
        this.color = color;
    }

    @Override
    public boolean checkConstraint(Die die) {
        return color.equals(die.getColor());
    }

    public SagradaColor getColor() {
        return color;
    }
}

