package chess;

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

    @Override
    public String toString() {
        return String.format("%s:%s:%s", pos.toString(), piece.getPieceType(), piece.getTeamColor());
    }
}
