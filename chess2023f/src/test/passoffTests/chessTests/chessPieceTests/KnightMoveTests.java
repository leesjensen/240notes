package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

public class KnightMoveTests {

    @Test
    public void knightMiddleOfBoard() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | |N| | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(5, 5);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {7, 6}, {6, 7}, {4, 7}, {3, 6}, {3, 4}, {4, 3}, {6, 3}, {7, 4},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");

        knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Changing color impacted moves");
    }


    @Test
    public void knightEdgeOfBoardLeft() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                |n| | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(4, 1);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {6, 2}, {5, 3}, {3, 3}, {2, 2},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void knightEdgeOfBoardRight() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | |n|
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(3, 8);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {1, 7}, {2, 6}, {4, 6}, {5, 7},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void knightEdgeOfBoardBottom() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | |N| | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(1, 6);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {2, 4}, {3, 5}, {3, 7}, {2, 8},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void knightEdgeOfBoardTop() {
        var board = TestFactory.loadBoard("""
                | | |N| | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(8, 3);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {7, 5}, {6, 4}, {6, 2}, {7, 1},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void knightCornerOfBoardBottomRight() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | |N|
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(1, 8);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {2, 6}, {3, 7},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void knightCornerOfBoardTopRight() {
        var board = TestFactory.loadBoard("""
                | | | | | | | |N|
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(8, 8);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {6, 7}, {7, 6},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");

    }

    @Test
    public void knightCornerOfBoardTopLeft() {
        var board = TestFactory.loadBoard("""
                |n| | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(8, 1);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {7, 3}, {6, 2},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");

    }

    @Test
    public void knightCornerOfBoardBottomLeft() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                |n| | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(1, 1);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {2, 3}, {3, 2},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");

    }


    @Test
    public void knightBlocked() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | |R| | | | |
                | | | | | | |P| |
                | | | | |N| | | |
                | | |N| | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(5, 5);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {3, 4}, {3, 6}, {4, 7}, {7, 6}, {6, 3},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void knightCaptureEnemy() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | |n| | | |
                | | |N| | | | | |
                | | | |P| |R| | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var knight = TestFactory.getNewPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        var position = TestFactory.getNewPosition(5, 5);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {7, 6}, {6, 7}, {4, 7}, {3, 6}, {3, 4}, {4, 3}, {6, 3}, {7, 4},
        });

        Assertions.assertEquals(validMoves, knight.pieceMoves(board, position), "Wrong moves");
    }
}
