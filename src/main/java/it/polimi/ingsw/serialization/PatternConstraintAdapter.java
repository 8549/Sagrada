package it.polimi.ingsw.serialization;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import it.polimi.ingsw.*;

import java.io.IOException;

public class PatternConstraintAdapter extends TypeAdapter<PatternConstraint> {
    @Override
    public void write(JsonWriter jsonWriter, PatternConstraint constraint) throws IOException {
        if (constraint instanceof BlankConstraint) {
            jsonWriter.nullValue();
            return;
        }
        if (constraint instanceof ColorConstraint) {
            jsonWriter.value(((ColorConstraint) constraint).getColor().name().toLowerCase());
            return;
        }
        if (constraint instanceof NumberConstraint) {
            jsonWriter.value(((NumberConstraint) constraint).getNumber());
            return;
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
