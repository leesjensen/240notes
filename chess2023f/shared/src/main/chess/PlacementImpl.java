package chess;

import java.util.Collection;

/**
 * Concrete implementation of the {@link ChessPlacement} interface.
 */
public class PlacementImpl implements ChessPlacement {
    private final ChessPiece piece;
    private final ChessPosition pos;

    public PlacementImpl(ChessPiece piece, ChessPosition pos) {
        this.piece = piece;
        this.pos = pos;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public ChessPosition getPos() {
        return pos;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board) {
        return piece.pieceMoves(board, pos);
    }


    public boolean isAttacked(ChessBoard board) {
        return board.isAttacked(pos, piece.getTeamColor());
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", pos.toString(), piece.getPieceType(), piece.getTeamColor());
    }
}