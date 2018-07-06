package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.client.RMIClientInterface;
import it.polimi.ingsw.network.server.ServerInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerInterface extends ServerInterface, Remote {

    @Override
    void start(String[] args) throws RemoteException;


    void login(Player p, RMIClientInterface c ) throws RemoteException;

    void patternCardValidation(String patternName, RMIClientInterface c) throws RemoteException;

    void validateMove(Die d, int row, int column, Player p) throws RemoteException;
    void passTurn (String name) throws RemoteException;
    void requestTool(String name, String tool) throws RemoteException;

    RMIServerInterface getNewStub() throws RemoteException;

    /**
     * Response from client of effect of Toolcard
     * @param row
     * @param column
     * @throws RemoteException
     */
    void setDieFromWP(int row, int column) throws RemoteException;

    /**
     * Response from client of effect of Toolcard
     * @param d
     * @throws RemoteException
     */
    void setDieFromDP(Die d) throws RemoteException;

    /**
     *Response from client of effect of Toolcard
     * @param d
     * @param round
     * @throws RemoteException
     */
    void setDieFromRT(Die d, int round) throws RemoteException;

    /**
     * Response from client of effect of Toolcard
     * @param choice
     * @throws RemoteException
     */
    void setDecrease(boolean choice) throws RemoteException;

    /**
     * Response from client of effect of Toolcard
     * @param choice
     * @throws RemoteException
     */
    void setPlacementChoice(boolean choice) throws RemoteException;

    /**
     * Response from client of effect of Toolcard
     * @param choice
     * @throws RemoteException
     */
    void setNumberDiceChoice(boolean choice) throws RemoteException;

    /**
     * Response from client of effect of Toolcard
     * @param value
     * @throws RemoteException
     */
    void setValue(int value) throws RemoteException;


    /**
     * Response from client of effect of Toolcard
     * @param row
     * @param column
     * @throws RemoteException
     */
    void setNewCoordinates(int row, int column) throws RemoteException;

    /**
     *Response from client of effect of Toolcard
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @throws RemoteException
     */
    public void setTwoDice(int row1, int col1, int row2, int col2) throws RemoteException;

    /**
     *Response from client of effect of Toolcard
     * @param row1
     * @param col1
     * @param row2
     * @param col2
     * @throws RemoteException
     */
    public void setTwoNewCoordinates(int row1, int col1, int row2, int col2) throws RemoteException;



}
