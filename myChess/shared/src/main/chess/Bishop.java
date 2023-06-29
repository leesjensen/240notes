package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * The bishop piece and rules for movement.
 */
public class Bishop extends Piece {
    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, pos, -1, -1, moves, true);
        calculateMoves(board, pos, 1, -1, moves, true);
        calculateMoves(board, pos, -1, 1, moves, true);
        calculateMoves(board, pos, 1, 1, moves, true);
        return moves;
    }
}
