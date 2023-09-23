package passoffTests.chessTests.chessPieceTests;

import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

public class BishopMoveTests {

    @Test
    public void bishopMoveUntilEdge() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |B| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 4},
                new int[][]{
                        {6, 5}, {7, 6}, {8, 7},
                        {4, 5}, {3, 6}, {2, 7}, {1, 8},
                        {4, 3}, {3, 2}, {2, 1},
                        {6, 3}, {7, 2}, {8, 1},
                }
        );
    }


    @Test
    public void bishopCaptureEnemy() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | |Q| | | | |
                        | | | | | | | | |
                        | |b| | | | | | |
                        |r| | | | | | | |
                        | | | | | | | | |
                        | | | | |P| | | |
                        | | | | | | | | |
                        """,
                new int[]{5, 2},
                new int[][]{
                        {6, 3}, {7, 4},
                        {4, 3}, {3, 4}, {2, 5},
                        // none
                        {6, 1},
                }
        );
    }


    @Test
    public void bishopBlocked() {
        TestFactory.validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | |R| |P| |
                        | | | | | |B| | |
                        """,
                new int[]{1, 6},
                new int[][]{}
        );
    }

}
