package chess;

import java.util.Collection;

public class PiecePlacement {
    private final ChessPiece piece;
    private final Position pos;

    public PiecePlacement(ChessPiece piece, Position pos) {
        this.piece = piece;
        this.pos = pos;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public Position getPos() {
        return pos;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board) {
        return piece.pieceMoves(board, pos);
    }

    @Override
    public String toString() {
        return String.format("%s:%s:%s", pos.toString(), piece.getPieceType(), piece.getTeamColor());
    }
}
