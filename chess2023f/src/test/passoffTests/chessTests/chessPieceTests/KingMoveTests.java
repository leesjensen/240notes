package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;
import java.util.Set;

public class KingMoveTests {

    @Test
    public void kingMoveUntilEdge() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | |K| | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var king = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        var position = TestFactory.getNewPosition(3, 6);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {4, 6}, {4, 7}, {3, 7}, {2, 7}, {2, 6}, {2, 5}, {3, 5}, {4, 5},
        });


        Set<ChessMove> pieceMoves = new HashSet<>(king.pieceMoves(board, position));
        Assertions.assertEquals(validMoves, pieceMoves,
                "ChessPiece pieceMoves did not return the correct moves");
    }


    @Test
    public void kingCaptureEnemy() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |N|n| | | |
                | | | |k| | | | |
                | | |P|b|p| | | |
                | | | | | | | | |
                """);

        var king = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        var position = TestFactory.getNewPosition(3, 4);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {4, 4}, {3, 5}, {2, 3}, {3, 3}, {4, 3},
        });


        // validate moves
        Set<ChessMove> pieceMoves = new HashSet<>(king.pieceMoves(board, position));
        Assertions.assertEquals(validMoves, pieceMoves,
                "ChessPiece pieceMoves did not return the correct moves");
    }


    @Test
    public void kingBlocked() {
        var board = TestFactory.loadBoard("""
                | | | | | | |r|k|
                | | | | | | |p|p|
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var king = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        var position = TestFactory.getNewPosition(8, 8);

        // make sure move list is empty
        Assertions.assertTrue(king.pieceMoves(board, position).isEmpty(),
                "ChessPiece pieceMoves returned valid moves for a trapped piece");
    }

}
