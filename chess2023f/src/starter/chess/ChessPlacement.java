package chess;

import java.util.Collection;

public interface ChessPlacement {
    ChessPiece getPiece();

    ChessPosition getPos();

    Collection<ChessMove> pieceMoves(ChessBoard board);

    boolean isAttacked(ChessBoard board);
}
