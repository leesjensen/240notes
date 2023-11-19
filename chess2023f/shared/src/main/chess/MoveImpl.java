package chess;

import java.util.Locale;

/**
 * Concrete implementation of the {@link ChessMove} interface.
 */
public class MoveImpl implements ChessMove {

    ChessPosition start;
    ChessPosition end;
    ChessPiece.PieceType promotionType;

    public MoveImpl(ChessPosition start, ChessPosition end, ChessPiece.PieceType promotionPiece) {
        this.start = new PositionImpl(start.getRow(), start.getColumn());
        this.end = new PositionImpl(end.getRow(), end.getColumn());
        this.promotionType = promotionPiece;
    }

    public MoveImpl(String notation) throws Exception {
        notation = notation.toLowerCase(Locale.ROOT);
        if (notation.length() >= 4) {
            int colStart = notation.charAt(0) - 'a' + 1;
            int rowStart = notation.charAt(1) - '1' + 1;
            int colEnd = notation.charAt(2) - 'a' + 1;
            int rowEnd = notation.charAt(3) - '1' + 1;

            start = new PositionImpl(rowStart, colStart);
            end = new PositionImpl(rowEnd, colEnd);
            if (notation.length() == 5) {
                promotionType = switch (notation.charAt(4)) {
                    case 'q' -> ChessPiece.PieceType.QUEEN;
                    case 'b' -> ChessPiece.PieceType.BISHOP;
                    case 'n' -> ChessPiece.PieceType.KNIGHT;
                    case 'r' -> ChessPiece.PieceType.ROOK;
                    default -> null;
                };
            }
            return;
        }
        throw new Exception("Invalid notation");
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
