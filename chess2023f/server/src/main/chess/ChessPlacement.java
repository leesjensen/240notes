package chess;

import java.util.Collection;

/**
 * Aggregates a piece and its position so that it can be represented independently of a board.
 */
public interface ChessPlacement {
    ChessPiece getPiece();

    ChessPosition getPos();

    Collection<ChessMove> pieceMoves(ChessBoard board);

    boolean isAttacked(ChessBoard board);
}
