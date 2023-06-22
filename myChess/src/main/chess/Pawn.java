package chess;

import java.util.Collection;
import java.util.HashSet;

public class Pawn extends Piece {
    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.PAWN);
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        var direction = (pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1);

        calculateMoves(board, pos, direction * 1, 0, moves, false);
        calculateMoves(board, pos, direction * 1, -1, moves, true);
        calculateMoves(board, pos, direction * 1, 1, moves, true);

        if (pieceColor == ChessGame.TeamColor.WHITE && pos.getRow() == 2 || pieceColor == ChessGame.TeamColor.BLACK && pos.getRow() == 7) {
            if (Board.isSquareEmpty(board, pos.getRow() + (direction * 1), pos.getColumn()) && Board.isSquareEmpty(board, pos.getRow() + (direction * 2), pos.getColumn())) {
                moves.add(new Move(pos, new Position(pos.getRow() + (direction * 2), pos.getColumn()), null));
            }
        }

        return moves;
    }


    @Override
    protected void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc, HashSet<ChessMove> moves, boolean attack) {
        int row = pos.getRow() + rowInc;
        int col = pos.getColumn() + colInc;
        if (row > 0 && col > 0 && row < 9 && col < 9) {
            var newPos = new Position(row, col);
            var pieceAt = board.getPiece(newPos);
            if ((attack && pieceAt != null && pieceAt.getTeamColor() != pieceColor)
                    || (!attack && pieceAt == null)) {
                addMoves(pos, newPos, moves);
            }
        }
    }

    private void addMoves(ChessPosition pos, ChessPosition newPos, HashSet<ChessMove> moves) {
        if (newPos.getRow() == 1 || newPos.getRow() == 8) {
            moves.add(new Move(pos, newPos, PieceType.QUEEN));
            moves.add(new Move(pos, newPos, PieceType.BISHOP));
            moves.add(new Move(pos, newPos, PieceType.ROOK));
            moves.add(new Move(pos, newPos, PieceType.KNIGHT));
        } else {
            moves.add(new Move(pos, newPos, null));
        }
    }
}
