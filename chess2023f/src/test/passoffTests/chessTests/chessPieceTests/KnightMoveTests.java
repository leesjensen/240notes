package passoffTests.chessTests.chessPieceTests;

import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

public class KnightMoveTests {

    @Test
    public void knightMiddleOfBoardWhite() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | |N| | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 5},
                new int[][]{
                        {7, 6}, {6, 7}, {4, 7}, {3, 6}, {3, 4}, {4, 3}, {6, 3}, {7, 4},
                }
        );
    }

    @Test
    public void knightMiddleOfBoardBlack() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | |n| | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 5},
                new int[][]{
                        {7, 6}, {6, 7}, {4, 7}, {3, 6}, {3, 4}, {4, 3}, {6, 3}, {7, 4},
                }
        );
    }


    @Test
    public void knightEdgeOfBoardLeft() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        |n| | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{4, 1},
                new int[][]{{6, 2}, {5, 3}, {3, 3}, {2, 2}}
        );
    }

    @Test
    public void knightEdgeOfBoardRight() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | |n|
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{3, 8},
                new int[][]{{1, 7}, {2, 6}, {4, 6}, {5, 7}}
        );
    }

    @Test
    public void knightEdgeOfBoardBottom() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | |N| | |
                        """,
                new int[]{1, 6},
                new int[][]{{2, 4}, {3, 5}, {3, 7}, {2, 8}}
        );
    }

    @Test
    public void knightEdgeOfBoardTop() {
        TestFactory.validateMoves("""
                        | | |N| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{8, 3},
                new int[][]{{7, 5}, {6, 4}, {6, 2}, {7, 1}}
        );
    }


    @Test
    public void knightCornerOfBoardBottomRight() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | |N|
                        """,
                new int[]{1, 8},
                new int[][]{{2, 6}, {3, 7}}
        );
    }

    @Test
    public void knightCornerOfBoardTopRight() {
        TestFactory.validateMoves("""
                        | | | | | | | |N|
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{8, 8},
                new int[][]{{6, 7}, {7, 6}}
        );
    }

    @Test
    public void knightCornerOfBoardTopLeft() {
        TestFactory.validateMoves("""
                        |n| | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{8, 1},
                new int[][]{{7, 3}, {6, 2}}
        );
    }

    @Test
    public void knightCornerOfBoardBottomLeft() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        |n| | | | | | | |
                        """,
                new int[]{1, 1},
                new int[][]{{2, 3}, {3, 2}}
        );
    }


    @Test
    public void knightBlocked() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | |R| | | | |
                        | | | | | | |P| |
                        | | | | |N| | | |
                        | | |N| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 5},
                new int[][]{{3, 4}, {3, 6}, {4, 7}, {7, 6}, {6, 3}}
        );
    }


    @Test
    public void knightCaptureEnemy() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | |n| | | |
                        | | |N| | | | | |
                        | | | |P| |R| | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 5},
                new int[][]{{7, 6}, {6, 7}, {4, 7}, {3, 6}, {3, 4}, {4, 3}, {6, 3}, {7, 4}}
        );
    }
}
