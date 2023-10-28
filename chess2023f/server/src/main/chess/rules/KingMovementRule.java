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


    void addCastleMoves(ChessBoard board, ChessPosition pos, HashSet<ChessMove> moves) {
        var king = board.getPiece(pos);
        var color = king.getTeamColor();
        var teamRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;

        var kingPos = new PositionImpl(teamRow, 5);
        if (king.equals(board.getPiece(kingPos)) && board.isOriginalPosition(kingPos)) {
            if (board.isOriginalPosition(new PositionImpl(teamRow, 8)) &&
                    board.isSquareEmpty(teamRow, 6) &&
                    board.isSquareEmpty(teamRow, 7)) {
                moves.add(new MoveImpl(pos, new PositionImpl(teamRow, 7), null));
            }
            if (board.isOriginalPosition(new PositionImpl(teamRow, 1)) &&
                    board.isSquareEmpty(teamRow, 2) &&
                    board.isSquareEmpty(teamRow, 3) &&
                    board.isSquareEmpty(teamRow, 4)) {
                moves.add(new MoveImpl(pos, new PositionImpl(teamRow, 3), null));
            }
        }
    }

}
