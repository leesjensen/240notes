package chess;

import java.util.Collection;

public class Game implements ChessGame {

    private ChessBoard board;

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
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return null;
    }
}
