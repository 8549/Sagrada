package it.polimi.ingsw.model.effect;

public class ChooseTwoDiceFromWindowPatterEffect extends Effect {
    public ChooseTwoDiceFromWindowPatterEffect(String name) {
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseTwoDieFromWindowPatter();
    }
}
