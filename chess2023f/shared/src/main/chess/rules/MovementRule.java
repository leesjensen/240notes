package chess.rules;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public abstract class MovementRule {

    protected void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc, HashSet<ChessMove> moves, boolean allowDistance) {
        var pieceColor = board.getPiece(pos).getTeamColor();
        int row = pos.getRow() + rowInc;
        int col = pos.getColumn() + colInc;
        while (row > 0 && col > 0 && row < 9 && col < 9) {
            var newPos = new PositionImpl(row, col);
            var pieceAt = board.getPiece(newPos);
            if (pieceAt == null || pieceAt.getTeamColor() != pieceColor) {
                moves.add(new MoveImpl(pos, newPos, null));
                row += rowInc;
                col += colInc;
            }

            if (!allowDistance || pieceAt != null) {
                break;
            }
        }
    }

    public abstract Collection<ChessMove> moves(ChessBoard board, ChessPosition position);
}
