package chess;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Collection;
import java.util.HashSet;

public class Game implements ChessGame {

    private Board board;
    private TeamColor turn;


    public Game() {
        this.board = new Board();
        this.turn = TeamColor.WHITE;
    }

    public Game(ChessGame copy) {
        this.board = new Board(copy.getBoard());
        this.turn = copy.getTeamTurn();
    }

    public static Game Create(String serializedGame) {
        return createSerializer().fromJson(serializedGame, Game.class);
    }

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

        throw new InvalidMoveException(String.format("Move %s is not valid", move));
    }

    @Override
    public Boolean isInCheck(TeamColor teamColor) {
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);

        return king.isAttacked(board);
    }

    @Override
    public Boolean isInCheckmate(TeamColor teamColor) {
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);

        // Is the king being attacked?
        if (!king.isAttacked(board)) {
            return false;
        }

        // Try every move on the board and see if it gets us out of this.
        for (var placement : board.collection()) {
            if (placement.getPiece().getTeamColor() == teamColor) {
                for (var move : placement.pieceMoves(board)) {
                    var newBoard = new Board(board);
                    newBoard.movePiece(move);
                    var kingPlacement = newBoard.getPiece(teamColor, ChessPiece.PieceType.KING);
                    if (!kingPlacement.isAttacked(newBoard)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Boolean isInStalemate(TeamColor teamColor) {
        // Is the king being attacked?
        var king = board.getPiece(teamColor, ChessPiece.PieceType.KING);
        if (king.isAttacked(board)) {
            return false;
        }
        // Does the player have any valid moves?
        for (var placement : board.collection()) {
            if (placement.getPiece().getTeamColor() == teamColor) {
                if (validMoves(placement.getPos()).size() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void setBoard(ChessBoard board) {
        this.board = new Board(board);
    }

    @Override
    public ChessBoard getBoard() {
        return board;
    }


    @Override
    public String toString() {
        return createSerializer().toJson(this);
    }


    private static Gson createSerializer() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Piece.class, Piece.getJsonTypeAdapter());
        builder.enableComplexMapKeySerialization();
        return builder.create();
    }
}
