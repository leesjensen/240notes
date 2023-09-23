package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;

public class PawnMoveTests {

    @Test
    public void pawnMiddleOfBoardWhite() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |P| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{4, 4},
                new int[][]{{5, 4}}
        );
    }

    @Test
    public void pawnMiddleOfBoardBlack() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |p| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{4, 4},
                new int[][]{{3, 4}}
        );
    }


    @Test
    public void pawnInitialMoveWhite() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | |P| | | |
                        | | | | | | | | |
                        """,
                new int[]{2, 5},
                new int[][]{{3, 5}, {4, 5}}
        );
    }

    @Test
    public void pawnInitialMoveBlack() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | |p| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{7, 3},
                new int[][]{{6, 3}, {5, 3}}
        );
    }


    @Test
    public void pawnPromotionWhite() {
        validatePromotion("""
                        | | | | | | | | |
                        | | |P| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{7, 3},
                new int[][]{{8, 3}}
        );
    }


    @Test
    public void edgePromotionBlack() {
        validatePromotion("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | |p| | | | | |
                        | | | | | | | | |
                        """,
                new int[]{2, 3},
                new int[][]{{1, 3}}
        );
    }


    @Test
    public void pawnPromotionCapture() {
        validatePromotion("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | |p| | | | | | |
                        |N| | | | | | | |
                        """,
                new int[]{2, 2},
                new int[][]{{1, 1}, {1, 2}}
        );
    }


    @Test
    public void pawnAdvanceBlockedWhite() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |n| | | | |
                        | | | |P| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{4, 4},
                new int[][]{}
        );
    }

    @Test
    public void pawnAdvanceBlockedBlack() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |p| | | | |
                        | | | |r| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 4},
                new int[][]{}
        );
    }


    @Test
    public void pawnAdvanceBlockedDoubleMoveWhite() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | |p| |
                        | | | | | | | | |
                        | | | | | | |P| |
                        | | | | | | | | |
                        """,
                new int[]{2, 7},
                new int[][]{{3, 7}}
        );
    }

    @Test
    public void pawnAdvanceBlockedDoubleMoveBlack() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | |p| | | | | |
                        | | |p| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{7, 3},
                new int[][]{}
        );
    }


    @Test
    public void pawnCaptureWhite() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | |r| |N| | | |
                        | | | |P| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{4, 4},
                new int[][]{{5, 3}, {5, 4}}
        );
    }

    @Test
    public void pawnCaptureBlack() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |p| | | | |
                        | | | |n|R| | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{4, 4},
                new int[][]{{3, 5}}
        );
    }

    private void validatePromotion(String boardText, int[] startPosition, int[][] endPositions) {

        var board = TestFactory.loadBoard(boardText);
        var start = TestFactory.getNewPosition(startPosition[0], startPosition[1]);
        var testPiece = board.getPiece(start);
        var validMoves = new HashSet<ChessMove>();
        for (var endPosition : endPositions) {
            var end = TestFactory.getNewPosition(endPosition[0], endPosition[1]);
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.QUEEN));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.BISHOP));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.ROOK));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.KNIGHT));
        }

        Assertions.assertEquals(validMoves, testPiece.pieceMoves(board, start), "Wrong moves");
    }

}
