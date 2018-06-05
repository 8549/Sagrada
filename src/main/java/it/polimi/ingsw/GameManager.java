package it.polimi.ingsw;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.MainServer;
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
    private MainServer server;
    private List<Player> players;
    private RoundTrack roundTrack;
    private ScoreTrack scoreTrack;
    private ObjCard[] publicObjectiveCards;
    private ToolCard[] toolCard;
    private List<Die> draftPool;
    private DiceBag diceBag;
    private Player firstPlayer;
    private Player currentPlayer;
    public static final int ROUNDS = 10;
    public static final int FIRSTROUND = 1;
    public static final int SECONDROUND = 2;

    public GameManager(MainServer server, List<Player> players) {
        this.server = server;
        this.players = players;
        System.out.println("Game is started with " + players.toString());
        gameSetup();
        playerSetup();
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
        diceBag = DiceBag.getInstance();

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
        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());

        //confirm players
        server.gameStartedProcedures(players);

        for (Player player : players) {

            //obj priv
            //player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());

            //pattern card
            List<PatternCard> choices = new ArrayList<>();
            for (int i = 0; i < PATTERN_CARDS_PER_PLAYER; i++) {
                choices.add((PatternCard) patternCardsDeck.getRandomCard());
                System.out.println("Choice: " + choices.get(i).getName());
            }


            // set pattern card da player;
            System.out.println("Game manager ask for Pattern to " + player.getName());

            server.choosePatternCard(choices, player);
            //TODO WAIT FOR PLAYER CHOICE

            //token
            player.setInitialTokens();
        }
    }


    public Player getFirstPlayer() {

        return firstPlayer;
    }

    public void endGame() {
        //check points private objective cards
        // check Points public objective cards
        // send points and winner
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void gameLoop() {
        for (int i = 1; i <= ROUNDS; i++) {
            Round round = new Round(players, i);
            round.playRound();
            players.add(players.get(0));
            players.remove(players.get(0));
        }
        endGame();
    }

    public void disconnectPlayer(Player player, Round round) {
        players.remove(player);
        int num = round.getCurrentTurn();
        round.removeTurn(player, round.getTurns().get(num).getNumber());
    }

    public void reconnectPlayer(Player player, Round round) {
        if (players.size() < 4) {
            int numb = round.getTurns().get(round.getCurrentTurn()).getNumber();
            Turn turn = new Turn(player, numb);
            players.add(player);
            if (numb == FIRSTROUND) {
                int num = round.getTurns().size() / 2;
                round.getTurns().add(num - 1, turn);
                round.getTurns().add(num, turn);
            } else {
                round.getTurns().add(turn);
            }
        }
    }

    public boolean checkConstraints(WindowPattern windowPattern, int row, int column, Die die) {
        return windowPattern.getConstraint(row, column).checkConstraint(die);
    }
}