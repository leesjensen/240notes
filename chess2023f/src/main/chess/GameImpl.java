package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * Concrete implementation of the {@link ChessGame} interface.
 * The game is an aggregation of the physical board and the rules
 * of chess.
 */
public class GameImpl implements ChessGame {


    private ChessBoard board;
    private TeamColor turn;


    public GameImpl() {
        this.board = new BoardImpl();
        this.turn = TeamColor.WHITE;
    }


    @Override
    public TeamColor getTeamTurn() {
        return turn;
    }

    @Override
    public void setTeamTurn(TeamColor turn) {
        this.turn = turn;
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

        throw new InvalidMoveException(String.format("Move %s is not valid", move));
    }

    @Override
    public boolean isInCheck(TeamColor teamColor) {
        var king = board.getPlacement(teamColor, ChessPiece.PieceType.KING);

        return king.isAttacked(board);
    }

    @Override
    public boolean isInCheckmate(TeamColor teamColor) {
        var king = board.getPlacement(teamColor, ChessPiece.PieceType.KING);

        // Is the king being attacked?
        if (!king.isAttacked(board)) {
            return false;
        }

        // Try every move on the board and see if it gets us out of this.
        for (var placement : board.collection()) {
            if (placement.getPiece().getTeamColor() == teamColor) {
                for (var move : placement.pieceMoves(board)) {
                    ChessBoard newBoard = new BoardImpl(board);
                    newBoard.movePiece(move);
                    var kingPlacement = newBoard.getPlacement(teamColor, ChessPiece.PieceType.KING);
                    if (!kingPlacement.isAttacked(newBoard)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean isInStalemate(TeamColor teamColor) {
        // Is the king being attacked?
        var king = board.getPlacement(teamColor, ChessPiece.PieceType.KING);
        if (king.isAttacked(board)) {
            return false;
        }
        // Does the player have any valid moves?
        for (var placement : board.collection()) {
            if (placement.getPiece().getTeamColor() == teamColor) {
                if (!validMoves(placement.getPos()).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }
}
