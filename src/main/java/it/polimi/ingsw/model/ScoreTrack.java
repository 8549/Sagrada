package it.polimi.ingsw.model;

public class ScoreTrack {
    private static ScoreTrack instance;


    public static ScoreTrack getIstance() {
        if (instance == null)
            instance = new ScoreTrack();
        return instance;

    }
}