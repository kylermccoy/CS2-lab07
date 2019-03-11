package connectfour.client;

import connectfour.ConnectFourException;

/**
 * Represents the client side of a Connect Four game. Establishes a connection
 * with the server and then responds to requests from the server (often by
 * prompting the real user).
 *
 * @author YOUR NAME HERE
 */
public class ConnectFourClient {

    /**
     * Starts a new {@link ConnectFourClient}.
     *
     * @param args Used to specify the hostname and port of the Connect Four
     *             server through which the client will play.
     * @throws ConnectFourException If there is a problem creating the client
     *                              or connecting to the server.
     */
    public static void main(String[] args) throws ConnectFourException {
        if (args.length != 2) {
            System.out.println(
                    "Usage: java ConnectFourClient hostname port");
            System.exit(1);
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
    }
}
