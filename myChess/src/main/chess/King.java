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
        var teamRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;
        if (board instanceof Board) {
            var b = (Board) board;
            if (b.isPieceInSquare(teamRow, 5, king)) {
                var rook = Piece.create(color, PieceType.ROOK);
                if (b.isPieceInSquare(teamRow, 8, rook) &&
                        Board.isSquareEmpty(b, teamRow, 6) &&
                        Board.isSquareEmpty(b, teamRow, 7)) {
                    moves.add(new Move(pos, new Position(teamRow, 7), null));
                }
                if (b.isPieceInSquare(teamRow, 1, rook) &&
                        Board.isSquareEmpty(b, teamRow, 2) &&
                        Board.isSquareEmpty(b, teamRow, 3) &&
                        Board.isSquareEmpty(b, teamRow, 4)) {
                    moves.add(new Move(pos, new Position(teamRow, 3), null));
                }
            }
        }
    }
}
