package it.polimi.ingsw.model.effect;


public class MoveDieEffect extends Effect{

    public MoveDieEffect(String name){
        this.name = name;
    }

    /**
     * Asks the tool card to move the dice if it's possible
     * @param args boolean : number , color , adjacency and place
     */
    @Override
    public void perform(Object... args) {
        toolCard.processTwoMoveWithoutConstraints(true, true, true, false);
    }
}
