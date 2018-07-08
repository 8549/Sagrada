package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.PatternCard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PublicObjectiveCard;
import it.polimi.ingsw.network.server.RMI.RMIServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RMIClientInterface extends ClientInterface, Remote {
    @Override
    void connect(String serverAddress, int portNumber, String userName) throws RemoteException;

    @Override
    String getName() throws RemoteException;

    @Override
    void requestPlacement(int number, String color, int row, int column)throws RemoteException;

    void loginResponse(boolean result) throws RemoteException;

    void addPlayersToProxy(List<Player> players) throws RemoteException;

    void addPlayerToProxy(Player player)throws RemoteException;

    void initPatternCardChoice(List<PatternCard> choices) throws RemoteException;

    void initGame(List<Player> p, int timeout) throws RemoteException;

    void setPrivateObj(String name) throws RemoteException;

    void setPublicObj(List<PublicObjectiveCard> publicObjCards) throws RemoteException;
    void setDraft(List<Die> draft) throws RemoteException;
    void beginTurn(String name, int round, int turn) throws RemoteException;
    void patternCardResponse(String name)throws RemoteException;

    void initTools(List<String> names) throws RemoteException;

    void setEndPoint(RMIServerInterface server) throws RemoteException;
    void updateOpponentsInfo(List<Player> players) throws RemoteException;
    void moveResponse(String name, boolean response, Die d, int row, int column) throws RemoteException;
    void moveTimeOut()throws RemoteException;
    void endCurrentTurn(String name) throws RemoteException;
    void endRound(List<Die> dice) throws RemoteException;

    void chooseDieFromWindowPattern() throws RemoteException;

    void chooseDieFromDraftPool() throws RemoteException;

    void chooseDieFromRoundTrack() throws RemoteException;

    void chooseIfDecrease() throws RemoteException;

    void chooseIfPlaceDie(int number) throws RemoteException;

    void chooseToMoveOneDie() throws RemoteException;

    void setValue(String color) throws RemoteException;

    void setNewCoordinates() throws RemoteException;

    void chooseTwoDice() throws RemoteException;

    void chooseTwoNewCoordinates() throws RemoteException;

    boolean ping() throws RemoteException;


    void sendDieFromWP(Die d, int row, int column) throws RemoteException;

    void sendDieFromDP(Die d) throws RemoteException;

    void sendDieFromRT(Die d, int round) throws RemoteException;

    void sendDecreaseChoice(boolean choice) throws RemoteException;

    void sendPlacementChoice(boolean choice) throws RemoteException;

    void sendNumberDiceChoice(boolean choice) throws RemoteException;

    void sendValue(int value) throws RemoteException;

    void sendNewCoordinates(int row, int column) throws RemoteException;

    void sendTwoDice(int row1, int col1, int row2, int col2) throws RemoteException;

    void sendTwoNewCoordinates(int row1, int col1, int row2, int col2) throws RemoteException;


    void nextMove() throws RemoteException;

    void pushTokens(String name, String tool, int cost) throws RemoteException;

    void notifyMoveDie(Player player, Die d, int row, int column, int newRow, int newColumn) throws RemoteException;

    void notifyAddDie(Player player, Die d, int rowm, int column) throws RemoteException;

    void changeTurn(Player first) throws RemoteException;

    void updateRoundTrack(Die d, int diePosition, int round) throws RemoteException;

    void updateGrid(int row, int col, Die d, String name) throws RemoteException;

    void notifyEndTool(boolean response, String name) throws RemoteException;

    void reconnection() throws RemoteException;

    void endGame(List<Player> players) throws RemoteException;

    void finishUpdate() throws RemoteException;
}
