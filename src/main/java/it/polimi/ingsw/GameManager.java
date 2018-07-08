package it.polimi.ingsw;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;

import java.io.IOException;
import java.util.*;

public class GameManager {
    public static final int PATTERN_CARDS_PER_PLAYER = 2;
    public static final int PUBLIC_OBJ_CARDS_NUMBER = 3;
    public static final int TOOL_CARDS_NUMBER = 3;
    public static final int DEFAULT_TURN_TIMEOUT = 5 * 60 * 1000;
    private MainServer server;
    private List<Player> players;
    private PublicObjectiveCard[] publicObjectiveCards = new PublicObjectiveCard[PUBLIC_OBJ_CARDS_NUMBER];
    private List<ToolCard> toolCard = new ArrayList<>();
    private Player firstPlayer;
    private Player currentPlayer;
    private Board board;
    public static final int ROUNDS = 10;
    private int numberCurrentRound;
    private Round round;
    private boolean hasMoved = false;
    private boolean timerIsRunning = false;
    private Timer timer;
    private int turnTimeout;


    public GameManager(MainServer server, List<Player> players) {
        this.server = server;
        this.players = players;
        this.turnTimeout = server.getTurnTimeout();
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
        server.gameStartedProcedures(players, turnTimeout / 1000);
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
        List<Player> playersWithPoints = new ArrayList<>();
        playersWithPoints.addAll(players);
        if (playersWithPoints.size() == 1) {
            server.notifyScore(playersWithPoints);
        } else {
            playersWithPoints.sort((p1, p2) -> Integer.compare(p1.getPoints(), p2.getPoints()));
            server.notifyScore(playersWithPoints);
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
        int activePlayer = 0;
        for (Player player : players) {
            if (player.getStatus().equals(PlayerStatus.ACTIVE)) {
                activePlayer++;
            }
        }
        if (activePlayer > 1) {
            if (currentPlayer.getStatus().equals(PlayerStatus.ACTIVE)) {
                checkTimerMove();
                server.notifyBeginTurn(round.getTurn().getPlayer(), numberCurrentRound, getRound().getCurrentTurn());
            } else {
                endCurrentTurn();
            }
        } else {
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
        for (Player player1 : players) {
            if (player1.getName().equals(player.getName())) {
                player1.setStatus(PlayerStatus.DISCONNECTED);
                break;
            }
        }
        if (getCurrentPlayer().getName().equals(player.getName())) {
            endCurrentTurn();
        }
    }

    public void reconnectPlayer(Player player) {
        for (Player player1 : players) {
            if (player1.getName().equals(player.getName())) {
                player1.setStatus(PlayerStatus.ACTIVE);
                break;
            }
        }
    }


    public void completePlayerSetup(Player playerC, String patternCardName) {
        Player p = getPlayerByName(playerC.getName());

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
            for (Player player : players) {
                player.setInitialTokens();

            }
            server.setPublicObj(publicObjectiveCards);
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

    public void updateModel(ClientObject c) {
        Player p = null;
        try {
            p = getPlayerByName(c.getPlayer().getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            server.gameStartedProceduresAfterReconnect(players, turnTimeout, c.getPlayer());
            server.setPlayerChoice(c, getPlayerByName(c.getPlayer().getName()).getPlayerWindow().getWindowPattern().getName());
            server.initPlayersData(players, c.getPlayer());
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.setPublicObj(publicObjectiveCards, p);

        server.setPrivateObj(p);

        server.pushTools(toolCard, p);

        for (Player player : players) {
            if (!player.getName().equals(p.getName())) {
                for (int i = 0; i < WindowPattern.ROWS; i++) {
                    for (int j = 0; j < WindowPattern.COLUMNS; j++) {
                        if (!player.getPlayerWindow().getCellAt(i, j).isEmpty()) {
                            try {
                                c.updateGrid(i, j, player.getPlayerWindow().getCellAt(i, j).getDie(), p.getName());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        }

        server.notifyFinishUpdate(c);

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
            }, turnTimeout);


        }
    }

    public boolean isToolActive() {
        return server.getActiveToolCardHandler() != null;

    }

    public Board getBoard() {
        return board;
    }

    public Round getRound() {
        return round;
    }

    public Player getPlayerByName(String name) {
        for (Player p : players) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

}