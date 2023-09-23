package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;

public class PawnMoveTests {

    @Test
    public void pawnMiddleOfBoardWhite() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |P| | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(4, 4);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {5, 4},
        });

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void pawnMiddleOfBoardBlack() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |p| | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(4, 4);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {3, 4},
        });

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void pawnInitialMoveWhite() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | |P| | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(2, 5);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {3, 5}, {4, 5}
        });

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void pawnInitialMoveBlack() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | |p| | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(7, 3);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {6, 3}, {5, 3}
        });

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void pawnPromotionWhite() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | |P| | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(7, 3);
        var endPositions = new int[][]{{8, 3}};
        validatePromotion(position, endPositions, board);
    }


    @Test
    public void edgePromotionBlack() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | |p| | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(2, 3);
        var endPositions = new int[][]{{1, 3}};
        validatePromotion(position, endPositions, board);
    }


    @Test
    public void pawnPromotionCapture() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | |p| | | | | | |
                |N| | | | | | | |
                """);

        var position = TestFactory.getNewPosition(2, 2);
        var endPositions = new int[][]{{1, 1}, {1, 2}};
        validatePromotion(position, endPositions, board);
    }


    @Test
    public void pawnAdvanceBlockedWhite() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |n| | | | |
                | | | |P| | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(4, 4);
        var pawn = board.getPiece(position);
        var validMoves = new HashSet<ChessMove>();

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void pawnAdvanceBlockedBlack() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |p| | | | |
                | | | |r| | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(5, 4);
        var pawn = board.getPiece(position);
        var validMoves = new HashSet<ChessMove>();

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void pawnAdvanceBlockedDoubleMoveWhite() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | |p| |
                | | | | | | | | |
                | | | | | | |P| |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(2, 7);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{{3, 7}});

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void pawnAdvanceBlockedDoubleMoveBlack() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | |p| | | | | |
                | | |p| | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(7, 3);
        var pawn = board.getPiece(position);
        var validMoves = new HashSet<ChessMove>();

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }


    @Test
    public void pawnCaptureWhite() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | |r| |N| | | |
                | | | |P| | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(4, 4);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {5, 3}, {5, 4}
        });

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }

    @Test
    public void pawnCaptureBlack() {
        var board = TestFactory.loadBoard("""
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | | | | | | |
                | | | |p| | | | |
                | | | |n|R| | | |
                | | | | | | | | |
                | | | | | | | | |
                """);

        var position = TestFactory.getNewPosition(4, 4);
        var pawn = board.getPiece(position);
        var validMoves = TestFactory.loadMoves(position, new int[][]{
                {3, 5}
        });

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, position), "Wrong moves");
    }


    private void validatePromotion(ChessPosition start, int[][] endPositions, ChessBoard board) {
        var pawn = board.getPiece(start);
        var validMoves = new HashSet<ChessMove>();
        for (var endPosition : endPositions) {
            var end = TestFactory.getNewPosition(endPosition[0], endPosition[1]);
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.QUEEN));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.BISHOP));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.ROOK));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.KNIGHT));
        }

        Assertions.assertEquals(validMoves, pawn.pieceMoves(board, start), "Wrong moves");
    }


}
