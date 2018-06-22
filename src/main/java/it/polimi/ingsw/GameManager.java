package it.polimi.ingsw;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.MainServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameManager {
    public static final int PATTERN_CARDS_PER_PLAYER = 2;
    public static final int PUBLIC_OBJ_CARDS_NUMBER = 3;
    public static final int TOOL_CARDS_NUMBER = 3;
    private MainServer server;
    private List<Player> players;
    private ObjCard[] publicObjectiveCards = new ObjCard[PUBLIC_OBJ_CARDS_NUMBER];
    private ToolCard[] toolCard;
    private Player firstPlayer;
    private Player currentPlayer;
    private Board board;
    public static final int ROUNDS = 10;
    public static final int FIRSTROUND = 1;
    public static final int SECONDROUND = 2;
    private int numberCurrentRound;
    private Round round;


    public GameManager(MainServer server, List<Player> players) {
        this.server = server;
        this.players = players;
        System.out.println("Game is started with " + players.toString());
        board = new Board();
        gameSetup();
        playerSetup();
    }

    public GameManager(List<Player> players) {
        this.players = players;
        board = new Board();

    }

    /**
     * This method performs the initial game setup following Sagrada's rules: places roundtrack, places scoretrack,
     * drafts 3 tool cards, drafts 3 public objective cards, selects randomly the first player
     */
    private void gameSetup() {

        //place round track
        board.setRoundTrack();

        //init scoretrack
        board.setScoreTrack();

        //place toolcard
        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
        }.getType());
        for (int i = 0; i < TOOL_CARDS_NUMBER; i++) {
            toolCard[i] = (ToolCard) toolDeck.getRandomCard();
        }
        board.setToolCards(toolCard);

        //obj pub
        CardsDeck objDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
        }.getType());
        for (int j = 0; j < PUBLIC_OBJ_CARDS_NUMBER; j++) {
            publicObjectiveCards[j] = (ObjCard) objDeck.getRandomCard();
        }
        board.setPublicObjectiveCards(publicObjectiveCards);

        Collections.shuffle(players);
        //select first random
        firstPlayer = players.get(0);
        board.setDiceBag();

        numberCurrentRound = 0;
    }

    /**
     * This method performs the initial player setup following Sagrada's rules, giving each player: a private objective
     * card, two window pattern cards (each containing two window patterns), the correct number of tokens (based on the
     * window pattern difficulty)
     */
    private void playerSetup() {
        //create deck, extract one time only and immediately delete cards
        CardsDeck privateObjectiveCardsDeck = new CardsDeck("PrivateObjectiveCards.json", new TypeToken<List<PrivateObjectiveCard>>() {
        }.getType());

        //create deck, extract one time only and immediately delete cards
        CardsDeck patternCardsDeck = new CardsDeck("PatternCards.json", new TypeToken<List<PatternCard>>() {
        }.getType());

        //confirm players
        server.gameStartedProcedures(players);

        for (Player player : players) {

            //obj priv
            player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());
            server.setPrivateObj(player, player.getPrivateObjectiveCard());

            //pattern card
            List<PatternCard> choices = new ArrayList<>();
            for (int i = 0; i < PATTERN_CARDS_PER_PLAYER; i++) {
                choices.add((PatternCard) patternCardsDeck.getRandomCard());
                System.out.println("Choice: " + choices.get(i).getName());
            }
            player.setChoices(choices);

            // set pattern card da player;
            System.out.println("Game manager ask for Pattern to " + player.getName());

            server.choosePatternCard(choices, player);

        }
        server.setPublicObj(publicObjectiveCards);


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

    public void startRound() {

        round = new Round(players, numberCurrentRound, board);
        server.setDraft(round.getDraftPool());

        startCurrentTurn();
    }

    public void startCurrentTurn() {
        server.notifyBeginTurn(round.getTurn().getPlayer());
    }

    public void endCurrentTurn() {
        round.passCurrentTurn();
        server.notifyEndTurn(players);
        if (round.getCurrentTurn() < round.getTurns().size()) {
            startCurrentTurn();
        } else {
            completeRound(round.getDraftPool());
        }
    }


    public void completeRound(List<Die> dieForRoundTrack) {
        server.notifyEndRound(players);
        players.add(players.get(0));
        players.remove(0);
        board.getRoundTrack().addRound(dieForRoundTrack);
        numberCurrentRound++;
        if (numberCurrentRound < ROUNDS) {
            startRound();
        }
    }


    public void disconnectPlayer(Player player) {
        player.setStatus(PlayerStatus.DISCONNECTED);
    }

    public void reconnectPlayer(Player player) {
        player.setStatus(PlayerStatus.ACTIVE);
    }


    public void completePlayerSetup(Player p, String patternCardName) {
        WindowPattern w = null;
        for (PatternCard c : p.getChoices()) {
            if (c.getBack().getName().equals(patternCardName)) {
                w = c.getBack();
            } else if (c.getFront().getName().equals(patternCardName)) {
                w = c.getFront();
            }
        }
        boolean everybodyHasChosen = true;
        if (w != null) {
            System.out.println("Setting " + w.getName() + " to " + p.getName());
            for (Player player : players) {
                if (!player.hasChosenPatternCard() && player.getName().equals(p.getName())) {
                    player.setHasChosenPatternCard(player.getPlayerWindow().setWindowPattern(w));

                }
                if (!player.hasChosenPatternCard()) {
                    everybodyHasChosen = false;
                }
            }
        } else {
            System.out.println("Error, patterncard not found! ");
        }

        if (everybodyHasChosen && round == null) {
            //token
            for (Player player : players) {
                player.setInitialTokens();
            }
            server.initPlayersData();
            startRound();

        }


    }

    public void processMove(Die die, int row, int column, Player player) {
        MoveValidator mv = new MoveValidator(round.getTurn(), round.getDraftPool(), true, true, true);
        boolean result = mv.validateMove(die, row, column, player);
        boolean diePlaced = round.getTurn().isDiePlaced();
        if (result) {
            if (!diePlaced) {
                round.getTurn().getPlayer().getPlayerWindow().addDie(die, row, column);
                round.removeDieFromDraftPool(die);
                round.getTurn().setDiePlaced();
            }
        }
        if (result && !diePlaced) {
            server.notifyPlacementResponse(true, player);
            server.setDraft(round.getDraftPool());
            endCurrentTurn();
        } else {
            server.notifyPlacementResponse(false, player);
            System.out.println("[DEBUG] Wrong move, should they try again or not?");
        }
    }

    public Board getBoard() {
        return board;
    }
}