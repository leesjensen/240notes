package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * The rook piece and rules for movement.
 */
public class Rook extends Piece {
    public Rook(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, pos, -1, 0, moves, true);
        calculateMoves(board, pos, 1, 0, moves, true);
        calculateMoves(board, pos, 0, 1, moves, true);
        calculateMoves(board, pos, 0, -1, moves, true);
        return moves;
    }
}
