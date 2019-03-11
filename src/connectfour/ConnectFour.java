package connectfour;

/**
 * A basic implementation of the Connect Four game.
 *
 * @author RIT CS
 */
public class ConnectFour {
    /**
     * Used to indicate a move that has been made on the board.
     */
    public enum Move {
        PLAYER_ONE('X'),
        PLAYER_TWO('O'),
        NONE('.');

        private char symbol;

        private Move(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }
    }

    /**
     * The number of rows in the board.
     */
    private int rows;

    /**
     * The number of columns in the board.
     */
    private int cols;

    /**
     * The board.
     */
    private Move[][] board;

    /**
     * Used to keep track of which player's turn it is; 0 for player 1, and 1
     * for player 2.
     */
    private int turn;

    /**
     *  The last column a piece was placed.  Used for win checking.
     */
    private int lastCol;

    /**
     * The row the last piece was placed.  Used for win checking.
     */
    private int lastRow;

    /**
     * Creates a Connect Four game using a board with the standard number of
     * rows (6) and columns (7).
     */
    public ConnectFour() {
        this(6, 7);
    }

    /**
     * Creates a Connect Four game using a board with the specified number of
     * rows and columns. Assumes that player 1 is the first to move.
     *
     * @param rows The number of rows in the board.
     * @param cols The number of columns in the board.
     */
    public ConnectFour(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        board = new Move[cols][rows];
        for(int col=0; col<cols; col++) {
            for(int row=0; row < rows; row++) {
                board[col][row] = Move.NONE;
            }
        }

        turn = 0;
    }

    /**
     * Makes a move for the player whose turn it is. If the move is successful,
     * play automatically switches to the other player's turn.
     *
     * @param column The column in which the player is moving.
     *
     * @throws ConnectFourException If the move is invalid for any reason.
     */
    public void makeMove(int column) throws ConnectFourException {
        Move move = turn == 0 ? Move.PLAYER_ONE : Move.PLAYER_TWO;

        if(column < 0 || column >= cols) {
            throw new ConnectFourException("Invalid column");
        }
        else if(board[column][0] != Move.NONE) {
            throw new ConnectFourException("Column full!");
        }
        else {
            int dropTo = 0;
            for(int r=1; r<rows && board[column][r] == Move.NONE; r++) {
                dropTo = r;
            }
            board[column][dropTo] = move;

            turn = turn ^ 1;
            lastCol = column;
            lastRow = dropTo;
        }
    }

    /**
     * Returns true if the game is currently in a winning state. Can be used to
     * determine if the most recent move won the game (and therefore the player
     * that made the move has won).
     *
     * @return True if the game is in a winning state. False otherwise.
     */
    public boolean hasWonGame() {
        Move player = board[lastCol][lastRow];

        // check left horizontal
        if (this.lastCol >= 3) {
            int count = 1;
            for (int col=this.lastCol-1; col>this.lastCol-4; --col) {
                if (board[col][lastRow] == player) {
                    ++count;
                }
            }
            if (count == 4) {
                return true;
            }
        }

        // check right horizontal
        if (this.lastCol <= 3) {
            int count = 1;
            for (int col=this.lastCol+1; col>this.lastCol+4; ++col) {
                if (board[col][lastRow] == player) {
                    ++count;
                }
            }
            if (count == 4) {
                return true;
            }
        }

        // check vertically down
        if (this.lastRow <=2) {
            int count = 1;
            for (int row=this.lastRow+1; row<this.lastRow+4; ++row) {
                if (board[this.lastCol][row] == player) {
                    ++count;
                }
            }
            if (count == 4) {
                return true;
            }
        }

        // check diagonally to left
        if (this.lastRow <= 2) {
            int count = 1;
            int col = this.lastCol - 1;
            for (int row = this.lastRow + 1; row < this.lastRow + 4; ++row) {
                if (board[col][row] == player) {
                    --col;
                    ++count;
                }
            }
            if (count == 4) {
                return true;
            }
        }

        // check diagonally to right
        if (this.lastRow <= 2) {
            int count = 1;
            int col = this.lastCol + 1;
            for (int row = this.lastRow + 1; row < this.lastRow + 4; ++row) {
                if (board[col][row] == player) {
                    ++col;
                    ++count;
                }
            }
            if (count == 4) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a {@link String} representation of the board, suitable for
     * printing.
     *
     * @return A {@link String} representation of the board.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(int r=0; r<rows; r++) {
            for(int c=0; c<cols; c++) {
                builder.append('[');
                builder.append(board[c][r].getSymbol());
                builder.append(']');
            }
            builder.append('\n');
        }
        return builder.toString();
    }
}
