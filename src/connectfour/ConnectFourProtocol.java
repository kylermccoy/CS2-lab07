package connectfour;

/**
 * The {@link ConnectFourProtocol} interface provides constants for all of the
 * messages that are communicated between the server and the clients.
 *
 * @author RIT CS
 */
public interface ConnectFourProtocol {
    /**
     * Request sent from the server to the client after the client initially
     * opens a {@link java.net.Socket} connection to the server. This is the
     * first part of the handshake used to establish that the client
     * understands the {@link ConnectFourProtocol protocol}.
     */
    public static final String CONNECT = "CONNECT";

    /**
     * Response sent from the client to the server in response to a
     * {@link #CONNECT} request. This is the second part of the handshake used
     * to establish that the client understands the
     * {@link ConnectFourProtocol protocol}.
     */
    public static final String CONNECTED = "CONNECTED";

    /**
     * Request sent from the server to the client when it is the client's turn
     * to make a move.
     */
    public static final String MAKE_MOVE = "MAKE_MOVE";

    /**
     * Response sent from the client to the server in response to a
     * {@link #MAKE_MOVE} request. The response should include the column
     * number into which the player would like to move.<P>
     *
     * For example (to move in the 3rd column): MOVE 3\n
     */
    public static final String MOVE = "MOVE";

    /**
     * Request sent from the server to the client when either player has moved.
     * The request will include the column in which the player moved.<P>
     *
     * For example (if a move was made in the 3rd column): MOVE_MADE 3\n
     */
    public static final String MOVE_MADE = "MOVE_MADE";

    /**
     * Response sent from the client to the server in response to a
     * {@link #MOVE_MADE} request.
     */
    public static final String MOVE_RECORDED = "MOVE_RECORDED";

    /**
     * Request sent from the server to the client when the client has won the
     * game.
     */
    public static final String GAME_WON = "GAME_WON";

    /**
     * Response sent from the client to the server in response to a
     * {@link #GAME_WON} request.
     */
    public static final String WON = "WON";

    /**
     * Request sent from the server to the client when the client has lost the
     * game.
     */
    public static final String GAME_LOST = "GAME_LOST";

    /**
     * Response sent from the client to the server in response to a
     * {@link #GAME_LOST} request.
     */
    public static final String LOST = "LOST";

    /**
     * Request sent from the server to the client when any kind of error has
     * resulted from a bad client response. No response is expected from the
     * client and the connection is terminated (as is the game).
     */
    public static final String ERROR = "ERROR";
}