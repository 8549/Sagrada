package it.polimi.ingsw.model.effect;

public class ChooseDieFromWindowPatternEffect extends Effect {

    public ChooseDieFromWindowPatternEffect(String name){
        this.name = name;
    }
    @Override
    public void perform(Object... args) {
        boolean firstChoice= (boolean) args[0];
        boolean placeDie= (boolean) args[1];
        if (firstChoice){
            toolCard.chooseDieFromWindowPattern();
        }
        else{
            if(placeDie) {
                toolCard.chooseDieFromWindowPattern();
            }
            else {
                toolCard.setResponse(true);
            }
        }
    }
}
