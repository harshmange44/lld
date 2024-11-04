package org.hrsh.chess;

public class ChessEngine {
    // 1 One Chess Board
    // 2 Two Players - White & Black
    // 3 White plays first
    // 4 Game finish on Checkmate, Stalemate or Resignation
    // 5 Timer for each Player
    // 6 Log moves

    private Board board;
    private Player player1, player2;
    private Player currentPlayer;
    private Status status;

    public void initGame(Board board, Player player1, Player player2) {
        board = new Board();
        board.init();
        initPlayers();
        status = Status.INACTIVE;
    }

    private void initPlayers() {
        player1 = new Player("P1", Color.WHITE, 10);
        player2 = new Player("P2", Color.BLACK, 10);
        currentPlayer = player1;
    }

    public void start() {
        status = Status.ACTIVE;

        while (status == Status.ACTIVE && player1.getTimeInMins() > 0 && player2.getTimeInMins() > 0) {
            Move move = getMove(board, currentPlayer);
            board.playMove(move);

            if (isGameCompleted()) {
                break;
            }

            if (currentPlayer.getColor() == player1.getColor()) {
                currentPlayer = player2;
            } else {
                currentPlayer = player1;
            }
        }
    }

    private boolean isGameCompleted() {
        if (board.isCheckmate(player1) || board.isStalemate(player1)) {
            status = Status.BLACK_WIN;
            return true;
        } else if (board.isCheckmate(player2) || board.isStalemate(player2)) {
            status = Status.WHITE_WIN;
            return true;
        } else if (player1.getTimeInMins() == 0) {
            status = Status.BLACK_WIN;
            return true;
        } else if (player2.getTimeInMins() == 0) {
            status = Status.WHITE_WIN;
            return true;
        }
        return false;
    }

    private Move getMove(Board board, Player currentPlayer) {
        return null;
    }
}
