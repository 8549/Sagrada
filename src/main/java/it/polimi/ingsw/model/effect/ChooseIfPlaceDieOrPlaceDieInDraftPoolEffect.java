package it.polimi.ingsw.model.effect;

public class ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect extends Effect {

    public ChooseIfPlaceDieOrPlaceDieInDraftPoolEffect(String name){
        this.name = name;
    }

    @Override
    public void perform(Object... args) {
        toolCard.chooseIfPlaceDie();
    }
}
