package chess;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    @Test
    void fromStringTest() {
        var expectedMove = new Move(new Position(2, 5), new Position(4, 5), null);
        var move = new Move("e2e4");
        assertEquals(expectedMove, move);
    }

    @Test
    void fromStringWithPromotionTest() {
        var expectedMove = new Move(new Position(2, 5), new Position(4, 5), ChessPiece.PieceType.KNIGHT);
        var move = new Move("e2e4n");
        assertEquals(expectedMove, move);
    }
}