package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Board implements ChessBoard {

    final private Piece[][] board = new Piece[8][8];

    public Board() {
    }

    public Board(Board copy) {
        for (var i = 0; i < 8; i++) {
            System.arraycopy(copy.board[i], 0, board[i], 0, 8);
        }
    }

    public void movePiece(ChessMove move) {
        var piece = getPiece(move.getStartPosition());
        removePiece(move.getStartPosition());
        addPiece(move.getEndPosition(), piece);
    }

    public void removePiece(ChessPosition position) {
        board[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = (Piece) piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public void resetBoard() {

    }

    public PiecePlacement getPiece(ChessGame.TeamColor color, ChessPiece.PieceType type) {
        for (var placement : collection()) {
            if (placement.getPiece().getTeamColor() == color && placement.getPiece().getPieceType() == type) {
                return placement;
            }
        }
        return null;
    }

    public Collection<ChessPosition> isUnderAttack(ChessPosition targetPos) {
        var attackers = new ArrayList<ChessPosition>();
        var targetPiece = getPiece(targetPos);

        for (var placement : collection()) {
            if (placement.getPiece().getTeamColor() != targetPiece.getTeamColor()) {
                var moves = placement.getPiece().pieceMoves(this, placement.getPos());
                for (var move : moves) {
                    if (move.getEndPosition().equals(targetPos)) {
                        attackers.add(placement.getPos());
                        break;
                    }
                }
            }
        }
        return attackers;
    }

    static public boolean isSquareEmpty(ChessBoard board, Integer row, Integer col) {
        var pieceAt = board.getPiece(new Position(row, col));
        return pieceAt == null;
    }

    public Collection<PiecePlacement> collection() {
        var result = new ArrayList<PiecePlacement>();

        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    result.add(new PiecePlacement(board[i][j], new Position(i + 1, j + 1)));
                }
            }
        }
        return result;
    }


}
