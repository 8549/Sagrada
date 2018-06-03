package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.server.ClientWrapper;
import it.polimi.ingsw.network.server.ServerInterface;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    @Test
    void endGame() {
    }


    @Test
    void disconnectPlayer() {
        ServerInterface serverInterface = new ServerInterface() {
            @Override
            public void start(String[] args) throws IOException {

            }

            @Override
            public void ping() throws RemoteException {

            }

            @Override
            public boolean login(ClientWrapper client) throws RemoteException {
                return false;
            }

            @Override
            public boolean updateOtherServer() throws RemoteException {
                return false;
            }

            @Override
            public void notifyClients() throws RemoteException {

            }

            @Override
            public void showClients() throws RemoteException {

            }

            @Override
            public ObservableList<ClientWrapper> getClientsConnected() throws RemoteException {
                return null;
            }

            @Override
            public void removeClient(ClientWrapper c) throws RemoteException {

            }

            @Override
            public boolean checkTimer() throws RemoteException {
                return false;
            }

            @Override
            public void sendPlayers(ObservableList<Player> players) throws RemoteException {

            }

            @Override
            public void choosePatternCard(List<PatternCard> choices, Player player) throws RemoteException {

            }
        };
        ObservableList<Player> players = new ObservableList<Player>() {
            @Override
            public void addListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public void removeListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public boolean addAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Collection<? extends Player> col) {
                return false;
            }

            @Override
            public boolean removeAll(Player... elements) {
                return false;
            }

            @Override
            public boolean retainAll(Player... elements) {
                return false;
            }

            @Override
            public void remove(int from, int to) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Player> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Player player) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Player get(int index) {
                return null;
            }

            @Override
            public Player set(int index, Player element) {
                return null;
            }

            @Override
            public void add(int index, Player element) {

            }

            @Override
            public Player remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Player> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Player> listIterator(int index) {
                return null;
            }

            @Override
            public List<Player> subList(int fromIndex, int toIndex) {
                return null;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };

        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = DiceBag.getInstance();
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        roundPlayers.add(francesca);
        Round round = new Round(roundPlayers, 1);
        GameManager gameManager = new GameManager(serverInterface, players);

        gameManager.disconnectPlayer(giulia, round);
        assertEquals(6, round.getTurns().size());
    }

    @Test
    void reconnectPlayer() {
        ServerInterface serverInterface = new ServerInterface() {
            @Override
            public void start(String[] args) throws IOException {

            }

            @Override
            public void ping() throws RemoteException {

            }

            @Override
            public boolean login(ClientWrapper client) throws RemoteException {
                return false;
            }

            @Override
            public boolean updateOtherServer() throws RemoteException {
                return false;
            }

            @Override
            public void notifyClients() throws RemoteException {

            }

            @Override
            public void showClients() throws RemoteException {

            }

            @Override
            public ObservableList<ClientWrapper> getClientsConnected() throws RemoteException {
                return null;
            }

            @Override
            public void removeClient(ClientWrapper c) throws RemoteException {

            }

            @Override
            public boolean checkTimer() throws RemoteException {
                return false;
            }

            @Override
            public void sendPlayers(ObservableList<Player> players) throws RemoteException {

            }

            @Override
            public void choosePatternCard(List<PatternCard> choices, Player player) throws RemoteException {

            }
        };
        ObservableList<Player> players = new ObservableList<Player>() {
            @Override
            public void addListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public void removeListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public boolean addAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Collection<? extends Player> col) {
                return false;
            }

            @Override
            public boolean removeAll(Player... elements) {
                return false;
            }

            @Override
            public boolean retainAll(Player... elements) {
                return false;
            }

            @Override
            public void remove(int from, int to) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Player> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Player player) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Player get(int index) {
                return null;
            }

            @Override
            public Player set(int index, Player element) {
                return null;
            }

            @Override
            public void add(int index, Player element) {

            }

            @Override
            public Player remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Player> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Player> listIterator(int index) {
                return null;
            }

            @Override
            public List<Player> subList(int fromIndex, int toIndex) {
                return null;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };

        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = DiceBag.getInstance();
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        roundPlayers.add(francesca);
        Round round = new Round(roundPlayers, 1);
        GameManager gameManager = new GameManager(serverInterface, players);

        gameManager.disconnectPlayer(giulia, round);
        assertEquals(6, round.getTurns().size());


        gameManager.reconnectPlayer(francesca, round);
        assertEquals(8, round.getTurns().size());
    }

    @Test
    void gameLoop() {

        ServerInterface serverInterface = new ServerInterface() {
            @Override
            public void start(String[] args) throws IOException {

            }

            @Override
            public void ping() throws RemoteException {

            }

            @Override
            public boolean login(ClientWrapper client) throws RemoteException {
                return false;
            }

            @Override
            public boolean updateOtherServer() throws RemoteException {
                return false;
            }

            @Override
            public void notifyClients() throws RemoteException {

            }

            @Override
            public void showClients() throws RemoteException {

            }

            @Override
            public ObservableList<ClientWrapper> getClientsConnected() throws RemoteException {
                return null;
            }

            @Override
            public void removeClient(ClientWrapper c) throws RemoteException {

            }

            @Override
            public boolean checkTimer() throws RemoteException {
                return false;
            }

            @Override
            public void sendPlayers(ObservableList<Player> players) throws RemoteException {

            }

            @Override
            public void choosePatternCard(List<PatternCard> choices, Player player) throws RemoteException {

            }
        };
        ObservableList<Player> players = new ObservableList<Player>() {
            @Override
            public void addListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public void removeListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public boolean addAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Collection<? extends Player> col) {
                return false;
            }

            @Override
            public boolean removeAll(Player... elements) {
                return false;
            }

            @Override
            public boolean retainAll(Player... elements) {
                return false;
            }

            @Override
            public void remove(int from, int to) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Player> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Player player) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Player get(int index) {
                return null;
            }

            @Override
            public Player set(int index, Player element) {
                return null;
            }

            @Override
            public void add(int index, Player element) {

            }

            @Override
            public Player remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Player> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Player> listIterator(int index) {
                return null;
            }

            @Override
            public List<Player> subList(int fromIndex, int toIndex) {
                return null;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };

        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        DiceBag diceBag = DiceBag.getInstance();
        List<Player> roundPlayers = new ArrayList<>();
        roundPlayers.add(marco);
        roundPlayers.add(giulia);
        roundPlayers.add(andrea);
        roundPlayers.add(francesca);
        Round round = new Round(roundPlayers, 1);
        GameManager gameManager = new GameManager(serverInterface, players);
        gameManager.gameLoop();

    }

    @Test
    void checkConstraints() {
        ObservableList<Player> players = new ObservableList<Player>() {
            @Override
            public void addListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public void removeListener(ListChangeListener<? super Player> listener) {

            }

            @Override
            public boolean addAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Player... elements) {
                return false;
            }

            @Override
            public boolean setAll(Collection<? extends Player> col) {
                return false;
            }

            @Override
            public boolean removeAll(Player... elements) {
                return false;
            }

            @Override
            public boolean retainAll(Player... elements) {
                return false;
            }

            @Override
            public void remove(int from, int to) {

            }

            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Player> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Player player) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Player> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Player get(int index) {
                return null;
            }

            @Override
            public Player set(int index, Player element) {
                return null;
            }

            @Override
            public void add(int index, Player element) {

            }

            @Override
            public Player remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Player> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Player> listIterator(int index) {
                return null;
            }

            @Override
            public List<Player> subList(int fromIndex, int toIndex) {
                return null;
            }

            @Override
            public void addListener(InvalidationListener listener) {

            }

            @Override
            public void removeListener(InvalidationListener listener) {

            }
        };
        ServerInterface serverInterface = new ServerInterface() {
            @Override
            public void start(String[] args) throws IOException {

            }

            @Override
            public void ping() throws RemoteException {

            }

            @Override
            public boolean login(ClientWrapper client) throws RemoteException {
                return false;
            }

            @Override
            public boolean updateOtherServer() throws RemoteException {
                return false;
            }

            @Override
            public void notifyClients() throws RemoteException {

            }

            @Override
            public void showClients() throws RemoteException {

            }

            @Override
            public ObservableList<ClientWrapper> getClientsConnected() throws RemoteException {
                return null;
            }

            @Override
            public void removeClient(ClientWrapper c) throws RemoteException {

            }

            @Override
            public boolean checkTimer() throws RemoteException {
                return false;
            }

            @Override
            public void sendPlayers(ObservableList<Player> players) throws RemoteException {

            }

            @Override
            public void choosePatternCard(List<PatternCard> choices, Player player) throws RemoteException {

            }
        };
        Player marco = new Player("marco");
        players.add(marco);
        Player giulia = new Player("giulia");
        players.add(giulia);
        Player andrea = new Player("andrea");
        players.add(andrea);
        Player francesca = new Player("francesca");
        players.add(francesca);
        GameManager gameManager = new GameManager(serverInterface, players);
        List<PatternCard> cards = new ArrayList<>();
        PatternConstraint[][] patternConstraints = new PatternConstraint[WindowPattern.ROWS][WindowPattern.COLUMNS];

        patternConstraints[0][0] = new NumberConstraint(4);
        patternConstraints[0][1] = new BlankConstraint();
        patternConstraints[0][2] = new NumberConstraint(2);
        patternConstraints[0][3] = new NumberConstraint(5);
        patternConstraints[0][4] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][0] = new BlankConstraint();
        patternConstraints[1][1] = new BlankConstraint();
        patternConstraints[1][2] = new NumberConstraint(6);
        patternConstraints[1][3] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[1][4] = new NumberConstraint(2);
        patternConstraints[2][0] = new BlankConstraint();
        patternConstraints[2][1] = new NumberConstraint(3);
        patternConstraints[2][2] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[2][3] = new NumberConstraint(4);
        patternConstraints[2][4] = new BlankConstraint();
        patternConstraints[3][0] = new NumberConstraint(5);
        patternConstraints[3][1] = new ColorConstraint(SagradaColor.GREEN);
        patternConstraints[3][2] = new NumberConstraint(1);
        patternConstraints[3][3] = new BlankConstraint();
        patternConstraints[3][4] = new BlankConstraint();
        WindowPattern virtus = new WindowPattern(5, "Virtus", patternConstraints);

        Die die = new Die(SagradaColor.PURPLE);
        die.setNumber(3);

        assertFalse(gameManager.checkConstraints(virtus, 2, 2, die));
        assertTrue(gameManager.checkConstraints(virtus, 2, 1, die));
    }

}