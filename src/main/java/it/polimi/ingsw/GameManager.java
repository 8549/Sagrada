package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private static final int CARDS_PER_PLAYER = 2;
    private List<Player> players;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private ObjCard[] publicObjectiveCards;
    private ToolCard[] toolCard;
    private List<Die> draftPool;
    private Player firstPlayer;
    private int currentTurn;
    private int currentRound;
    public static final int ROUNDS = 10;

    public GameManager() {
        super();
    }

    /**
     * This met
     * }
     * hod performs the initial game setup following Sagrada's rules: places roundtrack, places scoretrack,
     * drafts 3 tool cards, drafts 3 public objective cards, selects randomly the first player
     */
    private void gameSetup() {

        //place roud track
        roundTrack = RoundTrack.getInstance();

        //init scoretrack
        scoreTrack = ScoreTrack.getIstance();

        //place toolcard
        CardsDeck toolDeck = new CardsDeck("", null); //TODO
        for (int i = 0; i < 3; i++) {
            toolCard[i] = (ToolCard) toolDeck.getRandomCard();
        }

        //obj pub
        CardsDeck objDeck = new CardsDeck("", null); //TODO
        for (int j = 0; j < 3; j++) {
            publicObjectiveCards[j] = (ObjCard) objDeck.getRandomCard();
        }

        //select first random

        Random random = new Random();
        firstPlayer = players.get(random.nextInt(players.size()));

    }

    /**
     * This method performs the initial player setup following Sagrada's rules, giving each player: a private objective
     * card, two window pattern cards (each containing two window patterns), the correct number of tokens (based on the
     * window pattern difficulty)
     */
    private void playerSetup() {
        //creare il deck perchè disogna estrarre le carte una volta sola e subito rimuoverle
        CardsDeck privateObjectiveCardsDeck = new CardsDeck("", null); // TODO

        // creare il deck perchè disogna estrarre le carte una volta sola e subito rimuoverle
        CardsDeck patternCardsDeck = new CardsDeck("", null); // TODO

        for (Player player : players) {

            //obj priv
            player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());

            //pattern card
            List<PatternCard> choices = new ArrayList<>();
            for (int i = 0; i < CARDS_PER_PLAYER; i++) {
                choices.add((PatternCard) patternCardsDeck.getRandomCard());
            }
            // set pattern card da player;

            //TODO WAIT FOR PLAYER CHOICE

            //token
            player.setInitialTokens();

            //pattern slide
            //TODO ANIMAZIONE VIEW
        }

    }

    public Player getFirstPlayer() {

        return firstPlayer;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public int getCurrentRound() {

        return currentRound;
    }


}