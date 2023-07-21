package chess;

import java.util.Locale;

public class Move implements ChessMove {

    Position start;

    Position end;

    ChessPiece.PieceType promotionPiece;

    public Move(String notation) {
        notation = notation.toLowerCase(Locale.ROOT);
        if (notation.length() >= 4) {
            int colStart = notation.charAt(0) - 'a' + 1;
            int rowStart = notation.charAt(1) - '1' + 1;
            int colEnd = notation.charAt(2) - 'a' + 1;
            int rowEnd = notation.charAt(3) - '1' + 1;

            start = new Position(rowStart, colStart);
            end = new Position(rowEnd, colEnd);
        }

        if (notation.length() == 5) {
            promotionPiece = switch (notation.charAt(4)) {
                case 'q' -> ChessPiece.PieceType.QUEEN;
                case 'b' -> ChessPiece.PieceType.BISHOP;
                case 'n' -> ChessPiece.PieceType.KNIGHT;
                case 'r' -> ChessPiece.PieceType.ROOK;
                default -> null;
            };
        }
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
