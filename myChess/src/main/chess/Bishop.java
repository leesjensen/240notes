package chess;

import java.util.HashSet;
import java.util.Collection;

public class Bishop extends Piece {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, pos, -1, -1, moves);
        calculateMoves(board, pos, 1, -1, moves);
        calculateMoves(board, pos, -1, 1, moves);
        calculateMoves(board, pos, 1, 1, moves);
        return moves;
    }

    private void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc, HashSet<ChessMove> moves) {
        int row = pos.getRow() + rowInc;
        int col = pos.getColumn() + colInc;
        while (row > 0 && col > 0 && row < 9 && col < 9) {
            var newPos = new Position(row, col);
            moves.add(new Move(pos, newPos, null));
            row += rowInc;
            col += colInc;

            if (board.getPiece(newPos) != null) {
                break;
            }
        }
    }

    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }
}
