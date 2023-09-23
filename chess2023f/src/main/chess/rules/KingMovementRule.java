package chess.rules;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class KingMovementRule extends MovementRule {
    @Override
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition position) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, position, -1, 0, moves, false);
        calculateMoves(board, position, 1, 0, moves, false);
        calculateMoves(board, position, 0, 1, moves, false);
        calculateMoves(board, position, 0, -1, moves, false);
        calculateMoves(board, position, -1, -1, moves, false);
        calculateMoves(board, position, 1, 1, moves, false);
        calculateMoves(board, position, -1, 1, moves, false);
        calculateMoves(board, position, 1, -1, moves, false);

        addCastleMoves(board, position, moves);

        return moves;
    }


    void addCastleMoves(ChessBoard cBoard, ChessPosition pos, HashSet<ChessMove> moves) {
        if (cBoard instanceof BoardImpl board) {
            var king = board.getPiece(pos);
            var color = king.getTeamColor();
            var teamRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;

            var kingPos = new PositionImpl(teamRow, 5);
            if (king.equals(board.getPiece(kingPos)) && board.isOriginalPosition(kingPos)) {
                if (board.isOriginalPosition(new PositionImpl(teamRow, 8)) &&
                        BoardImpl.isSquareEmpty(board, teamRow, 6) &&
                        BoardImpl.isSquareEmpty(board, teamRow, 7)) {
                    moves.add(new MoveImpl(pos, new PositionImpl(teamRow, 7), null));
                }
                if (board.isOriginalPosition(new PositionImpl(teamRow, 1)) &&
                        BoardImpl.isSquareEmpty(board, teamRow, 2) &&
                        BoardImpl.isSquareEmpty(board, teamRow, 3) &&
                        BoardImpl.isSquareEmpty(board, teamRow, 4)) {
                    moves.add(new MoveImpl(pos, new PositionImpl(teamRow, 3), null));
                }
            }
        }
    }

}
