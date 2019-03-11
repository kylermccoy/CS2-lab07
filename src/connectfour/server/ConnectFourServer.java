package connectfour.server;

import connectfour.ConnectFourException;
import connectfour.ConnectFourProtocol;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server waits for incoming client connections and pairs them off
 * to play the game.
 *
 * @author YOUR NAME HERE
 */
public class ConnectFourServer {

    /**
     * Starts a new sever.
     *
     * @param args Used to specify the port on which the server should listen
     *             for incoming client connections.
     *
     * @throws ConnectFourException If there is an error starting the server.
     */
    public static void main(String[] args) throws ConnectFourException {

        if (args.length != 1) {
            System.out.println("Usage: java ConnectFourServer port");
            System.exit(1);
        }

        int port = Integer.parseInt(args[0]);
    }
}