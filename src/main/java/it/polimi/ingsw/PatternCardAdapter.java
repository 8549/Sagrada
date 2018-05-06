package it.polimi.ingsw;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatternCardAdapter extends TypeAdapter {
    //dall'oggetto java convertiamo in json
    public void write(JsonWriter jsonWriter, Object o) throws IOException {

    }

    // da json convertiamo in oggetto java
    public List<PatternCard> read(JsonReader jsonReader) throws IOException {
        int difficulty;
        String name;
        int i;
        int j;

        WindowPattern front;
        WindowPattern back;

        PatternConstraint[][] patternConstraints;
        List<PatternCard> patternCards = new ArrayList<>();

        //leggo inizio array
        jsonReader.beginArray();
        //ciclo su oggetti PatternCard
        while (jsonReader.hasNext()) {

            difficulty = 9;
            name = "NOT_INITIALIZED";
            front = null;
            back = null;

            jsonReader.beginObject();
            //ciclo su oggetti WindowPattern
            while (jsonReader.hasNext()) {
                String which = jsonReader.nextName();
                patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];
                if (which.equals("front") || which.equals("back")) {
                    jsonReader.beginObject();
                    //ciclo sugli attributi di WindowPattern
                    while (jsonReader.hasNext()) {
                        switch (jsonReader.nextName()) {
                            case "name":
                                name = jsonReader.nextString();
                                break;
                            case "difficulty":
                                difficulty = jsonReader.nextInt();
                                break;
                            case "rules":
                                jsonReader.beginArray();
                                i = 0;
                                //ciclo sulle righe
                                while (jsonReader.hasNext()) {
                                    jsonReader.beginArray();
                                    j = 0;
                                    //ciclo sulle colonne
                                    while (jsonReader.hasNext()) {
                                        switch (jsonReader.peek()) {
                                            case NUMBER:
                                                patternConstraints[i][j] = new NumberConstraint(jsonReader.nextInt());
                                                break;
                                            case STRING:
                                                patternConstraints[i][j] = new ColorConstraint(SagradaColor.valueOf(jsonReader.nextString()).getColor());
                                                break;
                                            case NULL:
                                                patternConstraints[i][j] = new BlankConstraint();
                                                jsonReader.nextNull();
                                                break;
                                        }
                                        j++;
                                    }
                                    jsonReader.endArray();
                                    i++;
                                }
                                jsonReader.endArray();
                                break;
                        }
                    }
                    jsonReader.endObject();
                }
                if (which.equals("front")) {
                    front = new WindowPattern(difficulty, name, patternConstraints);
                } else if (which.equals("back")) {
                    back = new WindowPattern(difficulty, name, patternConstraints);
                }
            }
            jsonReader.endObject();
            patternCards.add(new PatternCard(front, back));
        }
        jsonReader.endArray();
        return patternCards;
    }

}
