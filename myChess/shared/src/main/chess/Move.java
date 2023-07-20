package chess;

import java.util.Locale;

public class Move implements ChessMove {

    Position start;

    Position end;

    ChessPiece.PieceType promotionPiece;

    public static Move CreateMove(String notation) {
        if (notation.length() == 4) {
            notation = notation.toLowerCase(Locale.ROOT);
            int rowStart = 'a' - notation.charAt(0);
            int colStart = notation.charAt(1);
            int rowEnd = 'a' - notation.charAt(2);
            int colEnd = notation.charAt(3);

            return new Move(new Position(rowStart, colStart), new Position(rowEnd, colEnd), null);
        }
        return null;
    }

    public Move(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionType) {
        this.start = new Position(start.getRow(), start.getColumn());
        this.end = new Position(end.getRow(), end.getColumn());
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
