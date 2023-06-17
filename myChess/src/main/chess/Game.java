package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Game implements ChessGame {

    private Board board;

    @Override
    public TeamColor getTeamTurn() {
        return null;
    }

    @Override
    public void setTeamTurn(TeamColor team) {

    }

    @Override
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        var piece = board.getPiece(startPosition);
        return piece.pieceMoves(board, startPosition);
    }


    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public Boolean isInCheck(TeamColor teamColor) {
        return null;
    }

    @Override
    public Boolean isInCheckmate(TeamColor teamColor) {
        return null;
    }

    @Override
    public Boolean isInStalemate(TeamColor teamColor) {
        return null;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = (Board) board;
    }

    @Override
    public ChessBoard getBoard() {
        return null;
    }

    private Collection<ChessPosition> isUnderAttack(ChessPosition targetPos) {
        var attackers = new ArrayList<ChessPosition>();
        var targetPiece = board.getPiece(targetPos);

        var iter = board.iterator();
        while (iter.hasNext()) {
            var attackerPos = iter.next();
            var attackerPiece = board.getPiece(attackerPos);
            if (attackerPiece.getTeamColor() != targetPiece.getTeamColor()) {
                var moves = attackerPiece.pieceMoves(board, attackerPos);
                for (var move : moves) {
                    if (move.getEndPosition().equals(targetPos)) {
                        attackers.add(attackerPos);
                        break;
                    }
                }
            }
        }
        return attackers;
    }

}
