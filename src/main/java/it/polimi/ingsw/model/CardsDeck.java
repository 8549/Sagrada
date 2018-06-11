package it.polimi.ingsw.model;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.Utils;
import it.polimi.ingsw.serialization.GsonSingleton;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class CardsDeck  {

    private List<? extends Card> cardsDeck; // card da fare

    public CardsDeck(String file, Type type) {
        try (JsonReader reader = new JsonReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(file)))) {
            cardsDeck = GsonSingleton.getInstance().fromJson(reader, type);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * It returns and removes a random tool card from the deck
     *
     * @return A random tool card from the deck if there's still any card left, otherwise it creates a blank error card
     */
    public Card getRandomCard() {
        if (cardsDeck.size() > 0) {
            return cardsDeck.remove(Utils.getRandom(0, cardsDeck.size()-1));
        } else {
            return null;
        }

    }

    public static WindowPattern getWindowPatternByName(String name) {
        List<? extends Card> deck;
        try (JsonReader reader = new JsonReader(new InputStreamReader(CardsDeck.class.getClassLoader().getResourceAsStream("PatternCards.json")))) {
            deck = GsonSingleton.getInstance().fromJson(reader, new TypeToken<List<PatternCard>>() {
            }.getType());
            for (Card c : deck) {
                PatternCard pc = (PatternCard) c;
                if (pc.getFront().getName().equalsIgnoreCase(name)) {
                    return pc.getFront();
                } else if (pc.getBack().getName().equalsIgnoreCase(name)) {
                    return pc.getBack();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Card getByName(String name) {
        for (Card c : cardsDeck) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public List<? extends Card> getAsList() {
        return cardsDeck;
    }
}
