package chess;

import java.util.Collection;
import java.util.HashSet;

public class Rook extends Piece {
    public Rook(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, pos, -1, 0, moves);
        calculateMoves(board, pos, 1, 0, moves);
        calculateMoves(board, pos, 0, 1, moves);
        calculateMoves(board, pos, 0, -1, moves);
        return moves;
    }

    private void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc, HashSet<ChessMove> moves) {
        int row = pos.getRow() + rowInc;
        int col = pos.getColumn() + colInc;
        while (row > 0 && col > 0 && row < 9 && col < 9) {
            var newPos = new Position(row, col);
            var pieceAt = board.getPiece(newPos);
            if (pieceAt == null || pieceAt.getTeamColor() != pieceColor) {
                moves.add(new Move(pos, newPos, null));
                row += rowInc;
                col += colInc;
            }

            if (pieceAt != null) {
                break;
            }
        }
    }
}
