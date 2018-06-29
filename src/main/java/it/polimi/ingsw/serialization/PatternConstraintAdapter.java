package it.polimi.ingsw.serialization;

import com.google.gson.JsonElement;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.model.*;

import java.io.IOException;

public class PatternConstraintAdapter extends TypeAdapter<PatternConstraint> {
    @Override
    public void write(JsonWriter jsonWriter, PatternConstraint constraint) throws IOException {
        JsonElement el = constraint.getAsJson();
        if (el.isJsonNull()) {
            jsonWriter.nullValue();
        } else {
            try {
                int n = el.getAsInt();
                jsonWriter.value(n);
            } catch (ClassCastException | NumberFormatException e) {
                String n = el.getAsString();
                jsonWriter.value(n);
            }
        }
    }

    @Override
    public PatternConstraint read(JsonReader jsonReader) throws IOException {
        PatternConstraint constraint = null;
        switch (jsonReader.peek()) {
            case NULL:
                constraint = new BlankConstraint();
                jsonReader.nextNull();
                break;
            case STRING:
                constraint = new ColorConstraint(SagradaColor.valueOf(jsonReader.nextString().toUpperCase()));
                break;
            case NUMBER:
                constraint = new NumberConstraint(jsonReader.nextInt());
                break;
        }
        return constraint;
    }
}
