package it.polimi.ingsw.network.server;

import java.io.IOException;

public interface ServerInterface {

    void start() throws IOException;

    /**
     *
     * @return
     * @throws IOException
     */
    public boolean clientPing() throws IOException;

}
