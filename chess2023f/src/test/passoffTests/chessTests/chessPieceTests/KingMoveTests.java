package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;

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

        Assertions.assertEquals(validMoves, king.pieceMoves(board, position), "Wrong moves");
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


        Assertions.assertEquals(validMoves, king.pieceMoves(board, position), "Wrong moves");
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
        var validMoves = new HashSet<ChessMove>();

        Assertions.assertEquals(validMoves, king.pieceMoves(board, position), "Wrong moves");
    }

}
