package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private ObjCard[] publicObjectiveCards ;
    private ToolCard[] toolCards;
    private List<Die> draftPool;
    private RoundTrack roundTrack;
    private List<Player> players;
    private ScoreTrack scoreTrack;
    private DiceBag diceBag;

    public Board() {
        publicObjectiveCards = new ObjCard[GameManager.PUBLIC_OBJ_CARDS_NUMBER];
        toolCards = new ToolCard[GameManager.TOOL_CARDS_NUMBER];
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public ObjCard[] getPublicObjectiveCards() {
        return publicObjectiveCards;
    }

    public void setPublicObjectiveCards(ObjCard[] publicObjectiveCards) {
        this.publicObjectiveCards = publicObjectiveCards;
    }

    public Object getToolCards() {
        return toolCards;
    }

    public void setToolCards(ToolCard[] toolCards) {
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

    public void setRoundTrack() {
        roundTrack = new RoundTrack();
        roundTrack.getRoundCounter();
    }

    public void setScoreTrack() {
        scoreTrack = new ScoreTrack();
    }

    public void setDiceBag() {
        diceBag = new DiceBag();
    }
}
