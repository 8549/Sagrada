package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.serialization.PatternCardAdapter;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatternCardAdapterTest {

    @Test
    void write() {
    }

    @Test
    void read() {
        String name1 = "Virtus";
        int difficulty1 = 5;
        PatternConstraint[][] patternConstraints1 = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints1[0][0] = new NumberConstraint(4);
        patternConstraints1[0][1] = new BlankConstraint();
        patternConstraints1[0][2] = new NumberConstraint(2);
        patternConstraints1[0][3] = new NumberConstraint(5);
        patternConstraints1[0][4] = new ColorConstraint(SagradaColor.GREEN.getColor());
        patternConstraints1[1][0] = new BlankConstraint();
        patternConstraints1[1][1] = new BlankConstraint();
        patternConstraints1[1][2] = new NumberConstraint(6);
        patternConstraints1[1][3] = new ColorConstraint(SagradaColor.GREEN.getColor());
        patternConstraints1[1][4] = new NumberConstraint(2);
        patternConstraints1[2][0] = new BlankConstraint();
        patternConstraints1[2][1] = new NumberConstraint(3);
        patternConstraints1[2][2] = new ColorConstraint(SagradaColor.GREEN.getColor());
        patternConstraints1[2][3] = new NumberConstraint(4);
        patternConstraints1[2][4] = new BlankConstraint();
        patternConstraints1[3][0] = new NumberConstraint(5);
        patternConstraints1[3][1] = new ColorConstraint(SagradaColor.GREEN.getColor());
        patternConstraints1[3][2] = new NumberConstraint(1);
        patternConstraints1[3][3] = new BlankConstraint();
        patternConstraints1[3][4] = new BlankConstraint();

        String name2 = "Symphony of Light";
        int difficulty2 = 6;
        PatternConstraint[][] patternConstraints2 = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints2[0][0] = new NumberConstraint(2);
        patternConstraints2[0][1] = new BlankConstraint();
        patternConstraints2[0][2] = new NumberConstraint(5);
        patternConstraints2[0][3] = new BlankConstraint();
        patternConstraints2[0][4] = new NumberConstraint(1);
        patternConstraints2[1][0] = new ColorConstraint(SagradaColor.YELLOW.getColor());
        patternConstraints2[1][1] = new NumberConstraint(6);
        patternConstraints2[1][2] = new ColorConstraint(SagradaColor.PURPLE.getColor());
        patternConstraints2[1][3] = new NumberConstraint(2);
        patternConstraints2[1][4] = new ColorConstraint(SagradaColor.RED.getColor());
        patternConstraints2[2][0] = new BlankConstraint();
        patternConstraints2[2][1] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints2[2][2] = new NumberConstraint(4);
        patternConstraints2[2][3] = new ColorConstraint(SagradaColor.GREEN.getColor());
        patternConstraints2[2][4] = new BlankConstraint();
        patternConstraints2[3][0] = new BlankConstraint();
        patternConstraints2[3][1] = new NumberConstraint(3);
        patternConstraints2[3][2] = new BlankConstraint();
        patternConstraints2[3][3] = new NumberConstraint(5);
        patternConstraints2[3][4] = new BlankConstraint();

        String name3 = "Gravitas";
        int difficulty3 = 5;
        PatternConstraint[][] patternConstraints3 = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints3[0][0] = new NumberConstraint(1);
        patternConstraints3[0][1] = new BlankConstraint();
        patternConstraints3[0][2] = new NumberConstraint(3);
        patternConstraints3[0][3] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints3[0][4] = new BlankConstraint();
        patternConstraints3[1][0] = new BlankConstraint();
        patternConstraints3[1][1] = new NumberConstraint(2);
        patternConstraints3[1][2] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints3[1][3] = new BlankConstraint();
        patternConstraints3[1][4] = new BlankConstraint();
        patternConstraints3[2][0] = new NumberConstraint(6);
        patternConstraints3[2][1] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints3[2][2] = new BlankConstraint();
        patternConstraints3[2][3] = new NumberConstraint(4);
        patternConstraints3[2][4] = new BlankConstraint();
        patternConstraints3[3][0] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints3[3][1] = new NumberConstraint(5);
        patternConstraints3[3][2] = new NumberConstraint(2);
        patternConstraints3[3][3] = new BlankConstraint();
        patternConstraints3[3][4] = new NumberConstraint(1);

        String name4 = "Water of Life";
        int difficulty4 = 6;
        PatternConstraint[][] patternConstraints4 = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
        patternConstraints4[0][0] = new NumberConstraint(6);
        patternConstraints4[0][1] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints4[0][2] = new BlankConstraint();
        patternConstraints4[0][3] = new BlankConstraint();
        patternConstraints4[0][4] = new NumberConstraint(1);
        patternConstraints4[1][0] = new BlankConstraint();
        patternConstraints4[1][1] = new NumberConstraint(5);
        patternConstraints4[1][2] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints4[1][3] = new BlankConstraint();
        patternConstraints4[1][4] = new BlankConstraint();
        patternConstraints4[2][0] = new NumberConstraint(4);
        patternConstraints4[2][1] = new ColorConstraint(SagradaColor.RED.getColor());
        patternConstraints4[2][2] = new NumberConstraint(2);
        patternConstraints4[2][3] = new ColorConstraint(SagradaColor.BLUE.getColor());
        patternConstraints4[2][4] = new BlankConstraint();
        patternConstraints4[3][0] = new ColorConstraint(SagradaColor.GREEN.getColor());
        patternConstraints4[3][1] = new NumberConstraint(6);
        patternConstraints4[3][2] = new ColorConstraint(SagradaColor.YELLOW.getColor());
        patternConstraints4[3][3] = new NumberConstraint(3);
        patternConstraints4[3][4] = new ColorConstraint(SagradaColor.PURPLE.getColor());

        WindowPattern front1 = new WindowPattern(difficulty1, name1, patternConstraints1);
        WindowPattern back1 = new WindowPattern(difficulty2, name2, patternConstraints2);

        PatternCard patternCard1 = new PatternCard(front1, back1);

        WindowPattern front2 = new WindowPattern(difficulty3, name3, patternConstraints3);
        WindowPattern back2 = new WindowPattern(difficulty4, name4, patternConstraints4);

        PatternCard patternCard2 = new PatternCard(front2, back2);

        List<PatternCard> patternCards = new ArrayList<>();
        patternCards.add(patternCard1);
        patternCards.add(patternCard2);

        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(List.class, new PatternCardAdapter());
        final Gson gson = gsonBuilder.create();

        try (FileReader fileReader = new FileReader(getClass().getResource("TestPatternCardAdapter.json").getPath())) {
            List<PatternCard> patternCardsFromGson = gson.fromJson(fileReader, List.class);
            assertEquals(patternCards.toString(), patternCardsFromGson.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}