package it.polimi.ingsw.network.server.socket;

import it.polimi.ingsw.ToolCardHandler;
import it.polimi.ingsw.model.Die;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.PlayerStatus;
import it.polimi.ingsw.model.SagradaColor;
import it.polimi.ingsw.network.SocketInterface;
import it.polimi.ingsw.network.SocketParser;
import it.polimi.ingsw.network.SoxketHandlerInterface;
import it.polimi.ingsw.network.client.SocketClient;
import it.polimi.ingsw.network.server.ClientObject;
import it.polimi.ingsw.network.server.MainServer;
import it.polimi.ingsw.network.server.ServerInterface;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


import static it.polimi.ingsw.network.server.MainServer.DEFAULT_SOCKET_PORT;

public class SocketServer implements ServerInterface, SocketInterface {

    private SocketParser socketParserServer = new SocketParser();
    private List<ClientObject> users;
    private List<SocketHandler> socketClients = new ArrayList<>();
    private SoxketHandlerInterface socketHandler;
    MainServer server;
    int ids=0;

    public SocketServer(List<ClientObject> users, MainServer server) {
        this.users = users;
        this.server = server;
    }

    @Override
    public void start() throws IOException {
        ServerSocket listener = new ServerSocket(DEFAULT_SOCKET_PORT);
        System.out.println("[DEBUG] Socket socketServer is listening on port " + DEFAULT_SOCKET_PORT);

        try {
            while (true) {
                SocketHandler socketHandler = new SocketHandler(listener.accept(), this);
                System.out.println("New Connection detected");
                if (!socketClients.contains(socketHandler)) {
                    socketClients.add(socketHandler);
                }
                this.socketHandler = socketHandler;
                socketHandler.start();

            }
        } catch (IOException e) {
            System.err.println("[System]Socket socketServer failed " + e);
            e.getMessage();
        } finally {
            listener.close();
            System.out.println("Socket socketServer closed");
        }
    }

    @Override
    public boolean clientPing() {
        return true;
    }

    @Override
    public synchronized void processInput(String type, String header, String data, SoxketHandlerInterface s) throws IOException {
        if (type.equals("request")) {
            switch (header) {
                case "login":
                    SocketClientObject client = new SocketClientObject(new Player(data), s);
                    PlayerStatus result = server.addClient(client);
                    if(result.equals(PlayerStatus.ACTIVE)){
                        s.send("response", "login", "true");
                        server.addAlreadyLoogedPlayers(client);
                        server.addLoggedPlayer(client.getPlayer());
                        s.setClient(client);
                        server.checkTimer();

                    }else if(result.equals(PlayerStatus.ALREADYINGAME) || result.equals(PlayerStatus.NOTINGAME)){
                        s.send("response", "login", "Login failed");

                    } else if(result.equals(PlayerStatus.RECONNECTED)){
                        s.setClient(client);
                        s.send("response", "login", "true");
                        client.reconnection();
                        server.startUpdateModel(client);
                    }
                    break;
                case "patterncard":
                    System.out.println("Player" + s.getClient().getPlayer().getName() + " has chosen " + data);
                    server.setPlayerChoice(s.getClient(), data);
                    break;
                case "placement":
                    List<String> c = socketParserServer.parseData(data);
                    Die d = new Die(SagradaColor.valueOf(c.get(1)));
                    d.setNumber(Integer.valueOf(c.get(0)));
                    server.handleMove(d, Integer.valueOf(c.get(2)), Integer.valueOf(c.get(3)), s.getClient().getPlayer());
                    break;
                case "passTurn":
                    server.passTurn(s.getClient().getPlayer().getName());
                    break;
                case "tool":
                    server.useTool(s.getClient().getPlayer().getName(), data);
                    break;
                default:
                    System.out.println("Wrong message!");
                    break;
            }
        } else {
            if (type.equals("response")) {
                switch (header) {
                    case "setDieFromWP":
                        List<String> dieFromWP = socketParserServer.parseData(data);
                        ToolCardHandler t1 = server.getActiveToolCardHandler();
                                        /*Die die = new Die(SagradaColor.valueOf(dieFromWP.get(1)));
                                        die.setNumber(Integer.valueOf(dieFromWP.get(0)));*/
                        t1.setDieFromWindowPattern(Integer.valueOf(dieFromWP.get(2)), Integer.valueOf(dieFromWP.get(3)));
                        break;

                    case "setDieFromDP":
                        List<String> dieFromDP = socketParserServer.parseData(data);
                        ToolCardHandler t2 = server.getActiveToolCardHandler();
                        Die die = new Die(SagradaColor.valueOf(dieFromDP.get(1)));
                        die.setNumber(Integer.valueOf(dieFromDP.get(0)));
                        t2.setDieFromDraftPool(die);
                        break;
                    case "setDieFromRT":
                        List<String> dieFromRT = socketParserServer.parseData(data);
                        ToolCardHandler t3 = server.getActiveToolCardHandler();
                        Die die2 = new Die(SagradaColor.valueOf(dieFromRT.get(1)));
                        die2.setNumber(Integer.valueOf(dieFromRT.get(0)));
                        t3.setDieFromRoundTrack(die2, Integer.valueOf(dieFromRT.get(2)));
                        break;
                    case "setDecrease":
                        ToolCardHandler t4 = server.getActiveToolCardHandler();
                        if (data.equals("true")) {
                            t4.setDecreaseChoice(true);
                        } else {
                            t4.setDecreaseChoice(false);
                        }
                        break;
                    case "setPlacementChoice":
                        ToolCardHandler t5 = server.getActiveToolCardHandler();
                        if (data.equals("true")) {
                            t5.setIfPlace(true);
                        } else {
                            t5.setIfPlace(false);
                        }
                        break;
                    case "setNumberDiceChoice":
                        ToolCardHandler t6 = server.getActiveToolCardHandler();
                        if (data.equals("true")) {
                            t6.setMovementChoice(true);
                        } else {
                            t6.setMovementChoice(false);
                        }
                        break;
                    case "setValue":
                        ToolCardHandler t7 = server.getActiveToolCardHandler();
                        t7.chosenValue(Integer.valueOf(data));
                        break;

                    case "setNewCoordinates":
                        List<String> newC = socketParserServer.parseData(data);
                        ToolCardHandler t9 = server.getActiveToolCardHandler();
                        t9.setNewCoordinatesChoice(Integer.valueOf(newC.get(0)), Integer.valueOf(newC.get(1)));
                        break;

                    case "setTwoDice":
                                List<String> twoD = socketParserServer.parseData(data);
                                ToolCardHandler t10 = server.getActiveToolCardHandler();
                                int val1 = Integer.valueOf(twoD.get(0));
                                int val2 = Integer.valueOf(twoD.get(1));
                                int val3 = Integer.valueOf(twoD.get(2));
                                int val4 = Integer.valueOf(twoD.get(3));
                                t10.setTwoDiceFromWindowPattern(val1, val2, val3, val4);
                        break;
                    case "sendTwoNewCoordinates":
                                List<String> twoC = socketParserServer.parseData(data);
                                ToolCardHandler t11 = server.getActiveToolCardHandler();
                                int c1 = Integer.valueOf(twoC.get(0));
                                int c2 = Integer.valueOf(twoC.get(1));
                                int c3 = Integer.valueOf(twoC.get(2));
                                int c4 = Integer.valueOf(twoC.get(3));
                                t11.setTwoNewCoordinatesChoice(c1, c2, c3, c4);
                        break;

                    default:
                        System.out.println("Wrong message!");
                        break;
                }
            }
        }

    }

    public void removeClient(ClientObject client) {
        server.disconnect(client);

    }



}
