package it.polimi.ingsw.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternCardAdapter extends TypeAdapter {

    //dall'oggetto java convertiamo in json
    @Override
    public void write(JsonWriter out, Object o) throws IOException {
        out.setSerializeNulls(true);
        List<PatternCard> patternCards = (List<PatternCard>) o;
        out.beginArray();
        for (PatternCard patternCard : patternCards) {
            out.beginObject();
            // front
            WindowPattern front = patternCard.getFront();
            out.name("front").beginObject();
            out.value(front.getName());
            out.value(front.getDifficulty());
            out.name("rules").beginArray();
            for (PatternConstraint[] constraintsRows : front.getContraints()) {
                out.beginArray();
                for (PatternConstraint constraint : constraintsRows) {
                    if (constraint instanceof BlankConstraint) {
                        out.nullValue();
                    }
                    if (constraint instanceof NumberConstraint) {
                        Die die = new Die(SagradaColor.BLUE);
                        for (int i = Die.MIN; i <= Die.MAX; i++) {
                            die.setNumber(i);
                            if (constraint.checkConstraint(die)) {
                                out.value(i);
                            }
                        }
                    }
                    if (constraint instanceof ColorConstraint) {
                        Die die;
                        for (SagradaColor c : SagradaColor.values()) {
                            die = new Die(c);
                            if (constraint.checkConstraint(die)) {
                                out.value(c.name().toLowerCase());
                            }
                        }
                    }
                }
                out.endArray();
            }
            out.endArray();
            out.endObject();

            //back
            WindowPattern back = patternCard.getBack();
            out.name("back").beginObject();
            out.value(back.getName());
            out.value(back.getDifficulty());
            out.name("rules").beginArray();
            for (PatternConstraint[] constraintsRows : back.getContraints()) {
                out.beginArray();
                for (PatternConstraint constraint : constraintsRows) {
                    if (constraint instanceof BlankConstraint) {
                        out.nullValue();
                    }
                    if (constraint instanceof NumberConstraint) {
                        Die die = new Die(SagradaColor.BLUE);
                        for (int i = Die.MIN; i <= Die.MAX; i++) {
                            die.setNumber(i);
                            if (constraint.checkConstraint(die)) {
                                out.value(i);
                            }
                        }
                    }
                    if (constraint instanceof ColorConstraint) {
                        Die die;
                        for (SagradaColor c : SagradaColor.values()) {
                            die = new Die(c);
                            if (constraint.checkConstraint(die)) {
                                out.value(c.name().toLowerCase());
                            }
                        }
                    }
                }
                out.endArray();
            }
            out.endArray();
            out.endObject();

            out.endObject();
        }
        out.endArray();
    }

    // da json convertiamo in oggetto java
    public List<PatternCard> read(JsonReader in) throws IOException {
        int difficulty;
        String name;
        int i;
        int j;

        WindowPattern front;
        WindowPattern back;

        PatternConstraint[][] patternConstraints;
        List<PatternCard> patternCards = new ArrayList<>();

        //leggo inizio array
        in.beginArray();
        //ciclo su oggetti PatternCard
        while (in.hasNext()) {

            difficulty = 9;
            name = "NOT_INITIALIZED";
            front = null;
            back = null;

            in.beginObject();
            //ciclo su oggetti WindowPattern
            while (in.hasNext()) {
                String which = in.nextName();
                patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
                if (which.equals("front") || which.equals("back")) {
                    in.beginObject();
                    //ciclo sugli attributi di WindowPattern
                    while (in.hasNext()) {
                        switch (in.nextName()) {
                            case "name":
                                name = in.nextString();
                                break;
                            case "difficulty":
                                difficulty = in.nextInt();
                                break;
                            case "rules":
                                in.beginArray();
                                i = 0;
                                //ciclo sulle righe
                                while (in.hasNext()) {
                                    in.beginArray();
                                    j = 0;
                                    //ciclo sulle colonne
                                    while (in.hasNext()) {
                                        switch (in.peek()) {
                                            case NUMBER:
                                                patternConstraints[i][j] = new NumberConstraint(in.nextInt());
                                                break;
                                            case STRING:
                                                patternConstraints[i][j] = new ColorConstraint(SagradaColor.valueOf(in.nextString().toUpperCase()));
                                                break;
                                            case NULL:
                                                patternConstraints[i][j] = new BlankConstraint();
                                                in.nextNull();
                                                break;
                                        }
                                        j++;
                                    }
                                    in.endArray();
                                    i++;
                                }
                                in.endArray();
                                break;
                        }
                    }
                    in.endObject();
                }
                if (which.equals("front")) {
                    front = new WindowPattern(difficulty, name, patternConstraints);
                } else if (which.equals("back")) {
                    back = new WindowPattern(difficulty, name, patternConstraints);
                }
            }
            in.endObject();
            patternCards.add(new PatternCard(front, back));
        }
        in.endArray();
        return patternCards;
    }

}
