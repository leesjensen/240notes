package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;
import java.util.Set;

public class RookMoveTests {

    @Test
    public void rookMoveUntilEdge() {

        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | |R| | | | | |
                | | | | | | | | |
                """);

        var rook = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        var position = TestFactory.getNewPosition(2, 3);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {2, 4}, {2, 5}, {2, 6}, {2, 7}, {2, 8},
                {2, 2}, {2, 1},
                {1, 3},
                {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {8, 3},
        });

        // validate moves
        Set<ChessMove> pieceMoves = new HashSet<>(rook.pieceMoves(board, position));
        Assertions.assertEquals(validMoves, pieceMoves,
                "ChessPiece pieceMoves did not return the correct moves");
    }


    @Test
    public void bishopCaptureEnemy() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                |N| | | | | | | |
                |r| | | | |B| | |
                | | | | | | | | |
                |q| | | | | | | |
                | | | | | | | | |
                """);

        var rook = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        var position = TestFactory.getNewPosition(4, 1);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {5, 1},
                {3, 1},
                {4, 2}, {4, 3}, {4, 4}, {4, 5}, {4, 6},
        });

        // validate moves
        Set<ChessMove> pieceMoves = new HashSet<>(rook.pieceMoves(board, position));
        Assertions.assertEquals(validMoves, pieceMoves,
                "ChessPiece pieceMoves did not return the correct moves");
    }


    @Test
    public void rookBlocked() {
        var board = TestFactory.loadBoard("""
                | | | | | | |n|r|
                | | | | | | | |p|
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var rook = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        var position = TestFactory.getNewPosition(8, 8);

        //make sure move list is empty
        Assertions.assertTrue(rook.pieceMoves(board, position).isEmpty(),
                "ChessPiece pieceMoves returned valid moves for a trapped piece");
    }

}
