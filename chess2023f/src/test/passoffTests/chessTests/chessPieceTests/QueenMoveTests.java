package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;

public class QueenMoveTests {
    @Test
    public void queenMoveUntilEdge() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | |q| |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var queen = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        var position = TestFactory.getNewPosition(7, 7);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {8, 7},
                {8, 8},
                {7, 8},
                {6, 8},
                {6, 7}, {5, 7}, {4, 7}, {3, 7}, {2, 7}, {1, 7},
                {6, 6}, {5, 5}, {4, 4}, {3, 3}, {2, 2}, {1, 1},
                {7, 6}, {7, 5}, {7, 4}, {7, 3}, {7, 2}, {7, 1},
                {8, 6},
        });

        Assertions.assertEquals(validMoves, queen.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void queenCaptureEnemy() {
        var board = TestFactory.loadBoard("""
                |b| | | | | | | |
                | | | | | | | | |
                | | |R| | | | | |
                | | | | | | | | |
                |Q| | |p| | | | |
                | | | | | | | | |
                |P| |n| | | | | |
                | | | | | | | | |
                """);

        var queen = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        var position = TestFactory.getNewPosition(4, 1);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {5, 1}, {6, 1}, {7, 1}, {8, 1},
                {5, 2},
                {4, 2}, {4, 3}, {4, 4},
                {3, 1}, {3, 2},
                {2, 3},
        });

        Assertions.assertEquals(validMoves, queen.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void queenBlocked() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                |P|R| | | | | | |
                |Q|K| | | | | | |
                """);

        var queen = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        var position = TestFactory.getNewPosition(1, 1);
        var validMoves = new HashSet<ChessMove>();

        Assertions.assertEquals(validMoves, queen.pieceMoves(board, position), "Wrong moves");
    }

}
