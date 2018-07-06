package it.polimi.ingsw;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;
import jdk.net.SocketFlow;

import java.util.*;

public class GameManager {
    public static final int PATTERN_CARDS_PER_PLAYER = 2;
    public static final int PUBLIC_OBJ_CARDS_NUMBER = 3;
    public static final int TOOL_CARDS_NUMBER = 3;
    public static final int MOVE_TIMEOUT = 880;
    private MainServer server;
    private List<Player> players;
    private PublicObjectiveCard[] publicObjectiveCards = new PublicObjectiveCard[PUBLIC_OBJ_CARDS_NUMBER];
    private List<ToolCard> toolCard = new ArrayList<>();
    private Player firstPlayer;
    private Player currentPlayer;
    private Board board;
    public static final int ROUNDS = 10;
    public static final int FIRSTROUND = 1;
    public static final int SECONDROUND = 2;
    private int numberCurrentRound;
    private Round round;
    private boolean hasMoved = false;
    private boolean timerIsRunning = false;
    private Timer timer;


    public GameManager(MainServer server, List<Player> players) {
        this.server = server;
        this.players = players;
        System.out.println("Game is started with " + players.toString());
        board = new Board();

    }

    public void init() {
        gameSetup();
        playerSetup();
    }

    public GameManager(List<Player> players) {
        this.players = players;
        board = new Board();
        board.setDiceBag();
    }

    /**
     * This method performs the initial game setup following Sagrada's rules: places roundtrack,
     * * drafts 3 tool cards, drafts 3 public objective cards, selects randomly the first player
     */
    private void gameSetup() {

        //place round track
        board.setRoundTrack();

        //place toolcard
        CardsDeck toolDeck = new CardsDeck("ToolCards.json", new TypeToken<List<ToolCard>>() {
        }.getType());
        for (int i = 0; i < TOOL_CARDS_NUMBER; i++) {
            toolCard.add((ToolCard) toolDeck.getRandomCard());
        }
        board.setToolCards(toolCard);


        //obj pub
        CardsDeck objDeck = new CardsDeck("PublicObjectiveCards.json", new TypeToken<List<PublicObjectiveCard>>() {
        }.getType());
        for (int j = 0; j < PUBLIC_OBJ_CARDS_NUMBER; j++) {
            publicObjectiveCards[j] = (PublicObjectiveCard) objDeck.getRandomCard();
        }
        board.setPublicObjectiveCards(publicObjectiveCards);

        Collections.shuffle(players);
        //select first random
        firstPlayer = players.get(0);
        board.setDiceBag();

        numberCurrentRound = 1;

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
        server.gameStartedProcedures(players, MOVE_TIMEOUT);
        board.setPlayers(players);

        for (Player player : players) {

            //obj priv
            player.setPrivateObjectiveCard((ObjCard) privateObjectiveCardsDeck.getRandomCard());

            //pattern card
            List<PatternCard> choices = new ArrayList<>();
            for (int i = 0; i < PATTERN_CARDS_PER_PLAYER; i++) {
                choices.add((PatternCard) patternCardsDeck.getRandomCard());
                System.out.println("Choice: " + choices.get(i).getName());
            }
            player.setChoices(choices);

            // set pattern card da player;
            System.out.println("Game manager ask for Pattern to " + player.getName());

        }

        for (Player p : players) {
            server.setPrivateObj(p);
            server.choosePatternCard(p.getChoices(), p);
        }
        server.setPublicObj(publicObjectiveCards);

    }

    public MainServer getServer() {
        return this.server;
    }


    public void endGame() {
        for (Player player : players) {
            player.addPoints(player.getPrivateObjectiveCard().checkObjective(player.getPlayerWindow().getDiceGrid()));
            for (ObjCard pubCard : publicObjectiveCards) {
                player.addPoints(pubCard.checkObjective(player.getPlayerWindow().getDiceGrid()));
            }
            player.addPoints(player.getTokens());
            player.subPoints(player.getPlayerWindow().emptyCount());
        }
        int pointWinner = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPoints() > players.get(i + 1).getPoints()) {
                pointWinner = players.get(i).getPoints();
            } else {
                pointWinner = players.get(i + 1).getPoints();
            }
        }
        for (int i = 1; i < players.size(); i++) {
            if (players.get(i).getPoints() == pointWinner) {
                server.notifyWinner(players.get(i), players.get(i).getPoints());
            } else {
                server.notifyLoser(players.get(i), players.get(i).getPoints());
            }
        }
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
        currentPlayer = round.getTurn().getPlayer();
        int activePlayer=0;
        for(Player player : players){
            if (player.getStatus().equals(PlayerStatus.ACTIVE)){
                activePlayer++;
            }
        }
        if (activePlayer>1) {
            if (currentPlayer.getStatus().equals(PlayerStatus.ACTIVE)) {
                checkTimerMove();
                server.notifyBeginTurn(round.getTurn().getPlayer(), numberCurrentRound, getRound().getCurrentTurn());
            } else {
                endCurrentTurn();
            }
        }else {
            endGame();
        }
    }

    public void endCurrentTurn() {
        server.notifyEndTurn(getCurrentPlayer());
        round.passCurrentTurn();

        if (round.getCurrentTurn() < round.getTurns().size()) {
            startCurrentTurn();
        } else {
            completeRound(round.getDraftPool());
        }
    }


    public void completeRound(List<Die> dieForRoundTrack) {
        server.notifyEndRound(dieForRoundTrack);
        players.add(players.get(0));
        players.remove(0);
        board.getRoundTrack().addRound(dieForRoundTrack);
        numberCurrentRound++;
        if (numberCurrentRound <= ROUNDS) {
            startRound();
        } else {
            endGame();
        }
    }


    public void disconnectPlayer(Player player) {
        for (Player player1 : players){
            if(player1.equals(player)) {
                player1.setStatus(PlayerStatus.DISCONNECTED);
                break;
            }
        }
    }

    public void reconnectPlayer(Player player) {
        for (Player player1 : players){
            if(player1.equals(player)) {
                player1.setStatus(PlayerStatus.ACTIVE);
                break;
            }
        }
    }


    public void completePlayerSetup(Player playerC, String patternCardName) {
        Player p = null;
        for (Player pl : players) {
            if (pl.getName().equals(playerC.getName())) {
                p = pl;
            }
        }
        WindowPattern w = null;
        for (PatternCard c : p.getChoices()) {
            if (c.getBack().getName().equals(patternCardName)) {
                w = c.getBack();
            } else if (c.getFront().getName().equals(patternCardName)) {
                w = c.getFront();
            }
        }
        boolean everybodyHasChosen = false;
        boolean flag = true;
        if (w != null) {
            System.out.println("Setting " + w.getName() + " to " + p.getName());
            for (Player player : players) {
                if (!player.hasChosenPatternCard() && player.getName().equals(p.getName())) {
                    player.setHasChosenPatternCard(player.getPlayerWindow().setWindowPattern(w));

                }
                if (!player.hasChosenPatternCard()) {
                    flag = false;
                }
            }
            if (flag) {
                everybodyHasChosen = true;

            }
        } else {
            System.out.println("Error, patternCard not found! ");
        }

        if (everybodyHasChosen && round == null) {
            //token
            for (Player player : players) {
                player.setInitialTokens();

            }
            server.pushTools(toolCard);

            List<Player> updatedPlayer = new ArrayList<>(players);
            server.initPlayersData(updatedPlayer);
            startRound();

        }


    }

    public void processMove(Die die, int row, int column, Player player) {
        boolean diePlaced = round.getTurn().isDiePlaced();
        if (diePlaced || isToolActive()) {
            server.notifyMoveNotAvailable();
            System.out.println("[DEBUG] Die already placed, can't place another or a toolCard is in use");
        } else {
            MoveValidator mv = new MoveValidator(round.getTurn(), round.getDraftPool(), true, true, true);
            boolean result = mv.validateMove(die, row, column, player);
            if (result) {
                timer.cancel();
                timerIsRunning = false;
                hasMoved = false;
                round.getTurn().getPlayer().getPlayerWindow().addDie(die, row, column);
                round.removeDieFromDraftPool(die);
                round.getTurn().setDiePlaced();
                server.notifyPlacementResponse(true, player, die, row, column);
                server.setDraft(round.getDraftPool());
                if (round.getTurn().isToolCardUsed()) {
                    endCurrentTurn();
                } else {
                    server.askPlayerForNextMove();
                }
            } else {
                server.notifyPlacementResponse(false, player, die, row, column);
                System.out.println("[DEBUG] Wrong move, should they try again or not?");
            }
        }
    }

    public void useTool(String name, String tool) {
        if (getCurrentPlayer().getName().equals(name)) {
            for (ToolCard t : board.getToolCards()) {
                if (t.getName().equals(tool)) {
                    t.useTools(getCurrentPlayer(), this);
                }
            }
        }
    }

    public void checkTimerMove() {
        if (!timerIsRunning) {
            timerIsRunning = true;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!hasMoved) {
                        timerIsRunning = false;
                        server.moveTimeOut();
                        endCurrentTurn();
                    }
                }
            }, MOVE_TIMEOUT * 1000);


        }
    }

    public boolean isToolActive() {
        if (server.getActiveToolCardHandler() == null) {
            return false;
        } else {
            return true;
        }

    }

    public Board getBoard() {
        return board;
    }

    public Round getRound() {
        return round;
    }

}