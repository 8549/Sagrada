package it.polimi.ingsw.network.server.RMI;

import it.polimi.ingsw.network.server.MainServer;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class RMIPing extends Thread {
    private RMIClientObjectInterface client;
    private MainServer server;

    public RMIPing(RMIClientObjectInterface client, MainServer server) {
        this.client = client;
        this.server = server;
    }
    @Override
    public void run() {
        final boolean[] isTimerRunning = {false};
        final boolean[] isAlive = {false};
        Timer timer1 = new Timer();
        Timer timer2 = new Timer();

        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!isTimerRunning[0]) {
                        isTimerRunning[0] = true;
                        System.out.println("[DEBUG] RMI Ping Sent");
                        isAlive[0] = client.ping();

                    } else {
                        isAlive[0] = false;
                        System.out.println("[DEBUG] RMI Ping sent");
                        timer2.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if (!isAlive[0]) {
                                    try {
                                        System.out.println("[DEBUG] Client " + client.getPlayer().getName() + " disconnected");
                                    } catch (RemoteException e) {
                                        e.printStackTrace();
                                    }
                                    this.cancel();
                                    timer1.cancel();
                                    isTimerRunning[0] = false;
                                    server.disconnect(client);

                                }
                            }
                        }, 5 * 1000);
                        isAlive[0] = client.ping();

                    }
                } catch (RemoteException e) {
                    e.getMessage();
                    isTimerRunning[0] = false;
                    timer1.cancel();
                    timer2.cancel();
                    server.disconnect(client);
                }
            }
        }, 0, 20 * 1000);
    }
}
