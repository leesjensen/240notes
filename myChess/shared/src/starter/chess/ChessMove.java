package chess;

/**
 * Represents moveing a chess piece on a chess board
 */
public interface ChessMove {
    /**
     * @return ChessPosition of starting location
     */
    ChessPosition getStartPosition();

    /**
     * @return ChessPosition of ending location
     */
    ChessPosition getEndPosition();

    /**
     * Get's the type of piece to promote a pawn to if pawn promotion is part of this chess move
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    ChessPiece.PieceType getPromotionPiece();
}
