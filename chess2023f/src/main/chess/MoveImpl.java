package chess;

public class MoveImpl implements ChessMove {

    ChessPosition start;
    ChessPosition end;
    ChessPiece.PieceType promotionType;

    public MoveImpl(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        this.start = new PositionImpl(start.getRow(), start.getColumn());
        this.end = new PositionImpl(end.getRow(), end.getColumn());
        this.promotionType = promotionPiece;
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
        return promotionType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoveImpl move = (MoveImpl) o;
        return (start.equals(move.start) && end.equals(move.end) && promotionType == move.promotionType);
    }

    @Override
    public int hashCode() {
        return (1000 * start.hashCode()) + end.hashCode();
    }


    @Override
    public String toString() {
        var p = (promotionType == null ? "" : ":" + promotionType);
        return String.format("%s:%s%s", start.toString(), end.toString(), p);
    }
}
