package it.polimi.ingsw.network.client;

import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.ToolCard;
import it.polimi.ingsw.model.WindowPattern;

import java.io.IOException;

public interface ClientInterface  {
    String getName() throws IOException;

    void login() throws IOException;

    void connect(String serverAddress, int portNumber, String userName) throws IOException;
    void validatePatternCard(WindowPattern w) throws IOException;

    void requestPlacement(int number, String color, int row, int column) throws IOException;

    void passTurn() throws IOException;


    public void sendDieFromWP(Die d, int row, int column) throws IOException;

    public void sendDieFromDP(Die d) throws IOException;

    public void sendDieFromRT(Die d, int round) throws IOException;

    public void sendDecreaseChoice(boolean choice) throws IOException;

    public void sendPlacementChoice(boolean choice) throws IOException;

    public void sendNumberDiceChoice(boolean choice) throws IOException;

    public void sendValue(int value) throws IOException;

    public void sendNewCoordinates(int row, int column) throws IOException;

    public void sendTwoDice(int row1, int col1, int row2, int col2) throws IOException;

    public void sendTwoNewCoordinates(int row1, int col1, int row2, int col2) throws IOException;

    void requestTool(ToolCard tool) throws IOException;
}
