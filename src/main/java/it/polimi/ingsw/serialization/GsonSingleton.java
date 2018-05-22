package it.polimi.ingsw.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.PatternConstraint;

public class GsonSingleton {
    private static final Gson instance = new GsonBuilder()
            .registerTypeAdapter(PatternConstraint.class, new PatternConstraintAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    public static Gson getInstance() {
        return instance;
    }
}
