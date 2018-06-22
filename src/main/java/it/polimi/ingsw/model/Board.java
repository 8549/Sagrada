package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private List<ObjCard> publicObjectiveCards;
    private List<ToolCard> toolCards;
    private List<Die> draftPool;
    private RoundTrack roundTrack;
    private List<Player> players;
    private ScoreTrack scoreTrack;
    private DiceBag diceBag;

    public Board() {
        publicObjectiveCards = new ArrayList<>();
        toolCards = new ArrayList<>();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<ObjCard> getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(List<ObjCard> publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setToolCards(List<ToolCard> toolCards) {
        this.toolCards = toolCards;
    }

    public List<Die> getDraftPool() {
        return draftPool;
    }

    public void setDraftPool(List<Die> pool) {
        draftPool = pool;
    }

    public RoundTrack getRoundTrack() {
        return roundTrack;
    }

    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    public ScoreTrack getScoreTrack() {
        return scoreTrack;
    }

    public void setScoreTrack(ScoreTrack scoreTrack) {
        this.scoreTrack = scoreTrack;
    }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public void setRoundTrack() {
        roundTrack = RoundTrack.getInstance();
        roundTrack.getRoundCounter();
    }

    public void setScoreTrack() {
        scoreTrack = ScoreTrack.getIstance();
    }

    public void addPubCard(ObjCard card) {
        publicObjectiveCards.add(card);
    }

    public void setDiceBag() {
        diceBag = DiceBag.getInstance();
    }

    public void shufflePlayers() {
        Collections.shuffle(players);
    }

    public Player getFirstPlayer() {
        return players.get(0);
    }
}
