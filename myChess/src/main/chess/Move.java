package chess;

public class Move implements ChessMove {

    ChessPosition start;

    ChessPosition end;

    ChessPiece.PieceType promotionPiece;

    public Move(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionType) {
        this.start = start;
        this.end = end;
        this.promotionPiece = promotionType;
    }

    @Override
    public ChessPosition getStartPosition() {
        return start;
    }

    @Override
    public ChessPosition getEndPosition() {
        return end;
    }

    @Override
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return (start.equals(move.start) && end.equals(move.end) && promotionPiece == move.promotionPiece);
    }

    @Override
    public int hashCode() {
        return (1000 * start.hashCode()) + end.hashCode();
    }


    @Override
    public String toString() {
        var p = (promotionPiece == null ? "" : ":" + promotionPiece);
        return String.format("%s:%s%s", start.toString(), end.toString(), p);
    }
}
