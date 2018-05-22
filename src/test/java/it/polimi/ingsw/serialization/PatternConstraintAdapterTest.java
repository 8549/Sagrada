package it.polimi.ingsw.serialization;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import it.polimi.ingsw.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

class PatternConstraintAdapterTest {
    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = GsonSingleton.getInstance();
    }

    @Test
    void write() {
        List<PatternCard> patternCards = getSamplePatternCards();
        System.out.println(gson.toJson(patternCards));
    }

    @Test
    void read() {
        List<PatternCard> expected = getSamplePatternCards();

        try (JsonReader reader = new JsonReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("SamplePatternCards.json")))) {

            // Block approach
            List<PatternCard> actual = gson.fromJson(reader, new TypeToken<List<PatternCard>>() {
            }.getType());

            // Streaming approach
            /*List<PatternCard> actual = new ArrayList<>();
            reader.beginArray();
            while (reader.hasNext()) {
                PatternCard patternCard = gson.fromJson(reader, PatternCard.class);
                actual.add(patternCard);
            }
            reader.endArray();
            reader.close();*/

            assertEquals(expected, actual);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<PatternCard> getSamplePatternCards() {
        List<PatternCard> cards = new ArrayList<>();
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];

        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);

        patternConstraints[0][0] = new NumberConstraint(2);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(5);
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[1][1] = new NumberConstraint(6);
        patternConstraints[1][2] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[1][3] = new NumberConstraint(2);
        patternConstraints[1][4] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[2][2] = new NumberConstraint(4);
        patternConstraints[2][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new BlankConstraint();
        patternConstraints[3][1] = new NumberConstraint(3);
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new NumberConstraint(4);
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern symphonyOfLight = new WindowPattern(6, "Symphony of Light", patternConstraints);

        PatternCard card1 = new PatternCard(virtus, symphonyOfLight);

        patternConstraints[0][0] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[0][1] = new NumberConstraint(6);
        patternConstraints[0][2] = new BlankConstraint();
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(3);
        patternConstraints[1][0] = new NumberConstraint(5);
        patternConstraints[1][1] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[1][2] = new NumberConstraint(3);
        patternConstraints[1][3] = new BlankConstraint();
        patternConstraints[1][4] = new BlankConstraint();
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(2);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[2][3] = new NumberConstraint(1);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new BlankConstraint();
        patternConstraints[3][1] = new NumberConstraint(1);
        patternConstraints[3][2] = new NumberConstraint(5);
        patternConstraints[3][3] = new ColorConstraint(SagradaColor.PURPLE);
        patternConstraints[3][4] = new NumberConstraint(4);
        WindowPattern firmitas = new WindowPattern(5, "Firmitas", patternConstraints);

        patternConstraints[0][0] = new ColorConstraint(SagradaColor.YELLOW);
        patternConstraints[0][1] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[0][2] = new BlankConstraint();
        patternConstraints[0][3] = new BlankConstraint();
        patternConstraints[0][4] = new NumberConstraint(1);
        patternConstraints[1][0] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(5);
        patternConstraints[1][3] = new BlankConstraint();
        patternConstraints[1][4] = new NumberConstraint(4);
        patternConstraints[2][0] = new NumberConstraint(3);
        patternConstraints[2][1] = new BlankConstraint();
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.RED);
        patternConstraints[2][3] = new BlankConstraint();
        patternConstraints[2][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][0] = new NumberConstraint(2);
        patternConstraints[3][1] = new BlankConstraint();
        patternConstraints[3][2] = new BlankConstraint();
        patternConstraints[3][3] = new ColorConstraint(SagradaColor.BLUE);
        patternConstraints[3][4] = new ColorConstraint(SagradaColor.YELLOW);
        WindowPattern kaleidoscopicDream = new WindowPattern(4, "Kaleidoscopic Dream", patternConstraints);

        PatternCard card2 = new PatternCard(firmitas, kaleidoscopicDream);

        cards.add(card1);
        cards.add(card2);

        return cards;
    }
}