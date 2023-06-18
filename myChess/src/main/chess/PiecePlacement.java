package chess;

public class PiecePlacement {
    Piece piece;
    Position pos;

    public PiecePlacement(Piece piece, Position pos) {
        this.piece = piece;
        this.pos = pos;
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getPos() {
        return pos;
    }

}
