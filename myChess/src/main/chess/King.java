package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * The king piece and rules for movement.
 */
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

    void addCastleMoves(ChessBoard cBoard, ChessPosition pos, HashSet<ChessMove> moves) {
        if (cBoard instanceof Board board) {
            var king = board.getPiece(pos);
            var color = king.getTeamColor();
            var teamRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;

            var kingPos = new Position(teamRow, 5);
            if (king.equals(board.getPiece(kingPos)) && board.isOriginalPosition(kingPos)) {
                if (board.isOriginalPosition(new Position(teamRow, 8)) &&
                        Board.isSquareEmpty(board, teamRow, 6) &&
                        Board.isSquareEmpty(board, teamRow, 7)) {
                    moves.add(new Move(pos, new Position(teamRow, 7), null));
                }
                if (board.isOriginalPosition(new Position(teamRow, 1)) &&
                        Board.isSquareEmpty(board, teamRow, 2) &&
                        Board.isSquareEmpty(board, teamRow, 3) &&
                        Board.isSquareEmpty(board, teamRow, 4)) {
                    moves.add(new Move(pos, new Position(teamRow, 3), null));
                }
            }
        }
    }
}
