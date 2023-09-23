package passoffTests.chessTests.chessPieceTests;

import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

public class KingMoveTests {

    @Test
    public void kingMoveUntilEdge() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | |K| | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{3, 6},
                new int[][]{{4, 6}, {4, 7}, {3, 7}, {2, 7}, {2, 6}, {2, 5}, {3, 5}, {4, 5}}
        );
    }


    @Test
    public void kingCaptureEnemy() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |N|n| | | |
                        | | | |k| | | | |
                        | | |P|b|p| | | |
                        | | | | | | | | |
                        """,
                new int[]{3, 4},
                new int[][]{{4, 4}, {3, 5}, {2, 3}, {3, 3}, {4, 3}}
        );
    }


    @Test
    public void kingBlocked() {
        TestFactory.validateMoves("""
                        | | | | | | |r|k|
                        | | | | | | |p|p|
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{8, 8},
                new int[][]{}
        );
    }

}
