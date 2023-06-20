package chess;

import java.util.Collection;
import java.util.HashSet;

public class Game implements ChessGame {

    private Board board;
    private TeamColor turn;

    @Override
    public TeamColor getTeamTurn() {
        return turn;
    }

    @Override
    public void setTeamTurn(TeamColor team) {
        this.turn = team;
    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        var piece = board.getPiece(startPosition);
        var possibleMoves = piece.pieceMoves(board, startPosition);

        var validMoves = new HashSet<ChessMove>();

        // Make sure none of the possible moves are illegal.
        for (var move : possibleMoves) {
            if (board.isMoveLegal(move)) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }


    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        var piece = board.getPiece(move.getStartPosition());
        if (piece != null) {
            if (piece.getTeamColor() == turn) {
                var validMoves = piece.pieceMoves(board, move.getStartPosition());
                if (validMoves.contains(move)) {
                    if (board.isMoveLegal(move)) {
                        board.movePiece(move);
                        turn = (turn == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE);
                        return;
                    }
                }
            }
        }

        throw new InvalidMoveException();
    }

    @Override
    public Boolean isInCheck(TeamColor teamColor) {
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);

        return board.getAttackers(king.getPos()).size() > 0;
    }

    @Override
    public Boolean isInCheckmate(TeamColor teamColor) {
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);

        // Am I being attacked?
        var attackers = board.getAttackers(king.getPos());
        if (attackers.size() > 0) {
            // Can I move away?
            var kingMoves = validMoves(king.getPos());
            if (kingMoves.size() == 0) {
                // Am I being attacked by more than one person?
                if (attackers.size() > 1) {
                    return true;
                }

                // Can I kill the single attacker?
                var attacker = attackers.iterator().next();
                var attackerAttackers = board.getAttackers(attacker);
                if (attackerAttackers.size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean isInStalemate(TeamColor teamColor) {
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);

        // Am I being attacked?
        var attackers = board.getAttackers(king.getPos());
        if (attackers.size() == 0) {
            // Do I have any valid moves?
            var kingMoves = validMoves(king.getPos());
            if (kingMoves.size() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = (Board) board;
    }

    @Override
    public ChessBoard getBoard() {
        return null;
    }


}
