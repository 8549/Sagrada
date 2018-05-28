package it.polimi.ingsw;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.ServerInterface;
import it.polimi.ingsw.network.server.SocketServer;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameManager {
    private static final int PATTERN_CARDS_PER_PLAYER = 2;
    private ServerInterface server;
    private ObservableList<Player> players;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private ObjCard[] publicObjectiveCards;
    private ToolCard[] toolCard;
    private List<Die> draftPool;
    private DiceBag diceBag;
    private Player firstPlayer;
    private Player currentPlayer;
    private int currentTurn;
    private int currentRound;
    public static final int ROUNDS = 10;

    public GameManager(ServerInterface server, ObservableList<Player> players) {
        this.server = server;
        this. players = players;
        System.out.println("Game is started with " + players.toString());
    }

    /**
     * This method performs the initial game setup following Sagrada's rules: places roundtrack, places scoretrack,
     * drafts 3 tool cards, drafts 3 public objective cards, selects randomly the first player
     */
    private void gameSetup() {

        //place round track
        roundTrack = RoundTrack.getInstance();
        roundTrack.getRoundCounter();

        //init scoretrack
        scoreTrack = ScoreTrack.getIstance();

        //place toolcard
        /*CardsDeck toolDeck = new CardsDeck("", null); //TODO
        for (int i = 0; i < 3; i++) {
            toolCard[i] = (ToolCard) toolDeck.getRandomCard();
        }*/

        //obj pub
        /*CardsDeck objDeck = new CardsDeck("", null); //TODO
        for (int j = 0; j < 3; j++) {
            publicObjectiveCards[j] = (ObjCard) objDeck.getRandomCard();
        }*/

        Collections.shuffle(players);
        //select first random
        firstPlayer = players.get(0);

    }

    /**
     * This method performs the initial player setup following Sagrada's rules, giving each player: a private objective
     * card, two window pattern cards (each containing two window patterns), the correct number of tokens (based on the
     * window pattern difficulty)
     */
    private void playerSetup() {
        //create deck, extract one time only and immediately delete cards
        //CardsDeck privateObjectiveCardsDeck = new CardsDeck("", null); // TODO

        //create deck, extract one time only and immediately delete cards
        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>(){}.getType());
        try {
            server.sendPlayers(players);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        for (Player player : players) {

            //obj priv
            //player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());

            //pattern card
            List<PatternCard> choices = new ArrayList<>();
            for (int i = 0; i < PATTERN_CARDS_PER_PLAYER; i++) {
                choices.add((PatternCard) patternCardsDeck.getRandomCard());
            }


            // set pattern card da player;
            //TODO WAIT FOR PLAYER CHOICE

            //token
            player.setInitialTokens();
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


    public void setNextTurn() {
        if (currentTurn == 1) {
        } else {
            setNextRound();
        }
    }

    public void setNextRound() {
        if (currentRound == 10) {
            endGame();
        } else {
        }
    }

    public void endGame() {

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }


}
