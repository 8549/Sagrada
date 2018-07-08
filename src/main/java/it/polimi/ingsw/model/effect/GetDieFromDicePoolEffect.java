package it.polimi.ingsw.model.effect;


public class GetDieFromDicePoolEffect extends Effect{

    public GetDieFromDicePoolEffect(String name){
        this.name = name;
    }

    /**
     * Ask the tool card to draft a die
     * @param args die : the chosen die, decrease : if the die needs to be decreased
     */
    @Override
    public void perform(Object... args) {
        toolCard.getDieFromDicePool();
        toolCard.setResponse(true);
    }
}