package it.polimi.ingsw;

import com.google.gson.TypeAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardsDeck {

    private List<Card> cardsDeck; // card da fare

    public CardsDeck(String directory, TypeAdapter adapter) {
        //load from file
        //populate list
        cardsDeck = new ArrayList<Card>();
    }

    /**
     * It returns and removes a random tool card from the deck
     *
     * @return A random tool card from the deck if there's still any card left, otherwise it creates a blank error card
     */
    public Card getRandomCard() {
        if (cardsDeck.size() > 0) {
            Random rnd = new Random();
            return cardsDeck.remove(rnd.nextInt(cardsDeck.size()));
        } else {
            //return empty tool card
            //ToolCard emptyToolCard = new ToolCard
            //return emptyToolCard.setEmpty;
            return null; //TODO
        }

    }


}
