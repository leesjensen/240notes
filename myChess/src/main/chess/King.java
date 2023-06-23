package chess;

import java.util.Collection;
import java.util.HashSet;

public class King extends Piece {
    public King(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KING);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, pos, -1, 0, moves, false);
        calculateMoves(board, pos, 1, 0, moves, false);
        calculateMoves(board, pos, 0, 1, moves, false);
        calculateMoves(board, pos, 0, -1, moves, false);
        calculateMoves(board, pos, -1, -1, moves, false);
        calculateMoves(board, pos, 1, 1, moves, false);
        calculateMoves(board, pos, -1, 1, moves, false);
        calculateMoves(board, pos, 1, -1, moves, false);

        addCastleMoves(board, pos, moves);

        return moves;
    }

    void addCastleMoves(ChessBoard board, ChessPosition pos, HashSet<ChessMove> moves) {
        var king = board.getPiece(pos);
        var color = king.getTeamColor();
        var requiredRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;
        if (board instanceof Board) {
            var b = (Board) board;
            if (b.isPieceInSquare(requiredRow, 5, king)) {
                var rook = Piece.create(color, PieceType.ROOK);
                if (b.isPieceInSquare(requiredRow, 8, rook) &&
                        Board.isSquareEmpty(b, requiredRow, 6) &&
                        Board.isSquareEmpty(b, requiredRow, 7)) {
                    moves.add(new Move(pos, new Position(requiredRow, 7), null));
                }
                if (b.isPieceInSquare(requiredRow, 1, rook) &&
                        Board.isSquareEmpty(b, requiredRow, 2) &&
                        Board.isSquareEmpty(b, requiredRow, 3) &&
                        Board.isSquareEmpty(b, requiredRow, 4)) {
                    moves.add(new Move(pos, new Position(requiredRow, 3), null));
                }
            }
        }
    }
}
