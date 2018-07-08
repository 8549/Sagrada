package it.polimi.ingsw.model.effect;

public class ChooseDieFromWindowPatternEffect extends Effect {

    public ChooseDieFromWindowPatternEffect(String name){
        this.name = name;
    }

    /**
     * If it's the first choice or the die need to be placed ask the tool card to ask the  player to choose a die from the window pattern
     * otherwise sets everything ok to true
     * @param args
     */
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
