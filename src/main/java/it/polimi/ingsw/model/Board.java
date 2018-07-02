package it.polimi.ingsw.model;

import it.polimi.ingsw.GameManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private ObjCard[] publicObjectiveCards ;
    private List<ToolCard> toolCards;
    private List<Die> draftPool;
    private RoundTrack roundTrack;
    private List<Player> players;
    private ScoreTrack scoreTrack;
    private DiceBag diceBag;

    public Board() {
        publicObjectiveCards = new ObjCard[GameManager.PUBLIC_OBJ_CARDS_NUMBER];
        toolCards = new ArrayList<>();
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

    public List<ToolCard> getToolCards() {
        return toolCards;
    }

    public void setToolCards(List<ToolCard> toolCards) {
        this.toolCards.addAll(toolCards);
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

    public ScoreTrack getScoreTrack() {
        return scoreTrack;
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
