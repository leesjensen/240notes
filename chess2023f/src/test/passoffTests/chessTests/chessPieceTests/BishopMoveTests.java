package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BishopMoveTests {

    @Test
    @DisplayName("Move Until Edge")
    public void bishopEmptyBoard() {

        var boardText = """
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |B| | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """;

        var moveCords = new int[][]{
                {4, 3}, {3, 2}, {2, 1},            // - -
                {6, 3}, {7, 2}, {8, 1},            // + -
                {4, 5}, {3, 6}, {2, 7}, {1, 8},    // - +
                {6, 5}, {7, 6}, {8, 7},            // + +
        };

        var board = TestFactory.loadBoard(boardText);
        var bishop = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        var position = TestFactory.getNewPosition(5, 4);
        var validMoves = TestFactory.loadMoves(position, moveCords);

        // validate moves
        Set<ChessMove> pieceMoves = new HashSet<>(bishop.pieceMoves(board, position));
        Assertions.assertEquals(validMoves, pieceMoves,
                "ChessPiece pieceMoves did not return the correct moves");
    }


    @Test
    @DisplayName("Capture Enemy Pieces")
    public void bishopCapture() {

        var boardText = """
                | | | | | | | | |
                | | | |Q| | | | |
                | | | | | | | | |
                | |b| | | | | | |
                |r| | | | | | | |
                | | | | | | | | |
                | | | | |P| | | |
                | | | | | | | | |
                """;

        var moveCords = new int[][]{
                // - - none
                {6, 1},                            // + -
                {4, 3}, {3, 4}, {2, 5},            // - +
                {6, 3}, {7, 4}                     // + +
        };

        var board = TestFactory.loadBoard(boardText);
        var bishop = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        var position = TestFactory.getNewPosition(5, 2);
        var validMoves = TestFactory.loadMoves(position, moveCords);

        // validate moves
        Set<ChessMove> pieceMoves = new HashSet<>(bishop.pieceMoves(board, position));
        Assertions.assertEquals(validMoves, pieceMoves,
                "ChessPiece pieceMoves did not return the correct moves");
    }


    @Test
    @DisplayName("Bishop Completely Blocked")
    public void stuck() {

        var boardText = """
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | |R| |P| |
                | | | | | |B| | |
                """;

        var board = TestFactory.loadBoard(boardText);
        var bishop = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        var position = TestFactory.getNewPosition(1, 6);

        // make sure move list is empty
        Assertions.assertTrue(bishop.pieceMoves(board, position).isEmpty(),
                "ChessPiece pieceMoves returned valid moves for a trapped piece");
    }

}
