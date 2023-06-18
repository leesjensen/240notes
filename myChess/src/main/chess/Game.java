package chess;

import java.util.Collection;
import java.util.HashSet;

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
        var possibleMoves = piece.pieceMoves(board, startPosition);

        var validMoves = new HashSet<ChessMove>();

        // Make sure none of the possible moves are illegal.
        for (var move : possibleMoves) {
            var newBoard = new Board(board);
            newBoard.movePiece(move);
            var king = newBoard.getPiece(piece.getTeamColor(), ChessPiece.PieceType.KING);
            if (king == null || newBoard.isUnderAttack(king.getPos()).size() == 0) {
                validMoves.add(move);
            }
        }

        return validMoves;
    }


    @Override
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new InvalidMoveException();
    }

    @Override
    public Boolean isInCheck(TeamColor teamColor) {
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);

        // king under attack.
        // can't kill. If more than 1 then the answer is automatic no.
        // can't move. Got to check every move the king can make by creating new board and checking if under attack.

        return board.isUnderAttack(king.getPos()).size() > 0;
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


}
