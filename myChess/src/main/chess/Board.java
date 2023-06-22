package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Board implements ChessBoard {

    final private ChessPiece[][] board = new ChessPiece[8][8];

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
        var pieces = new ChessPiece.PieceType[]{
                ChessPiece.PieceType.ROOK,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.QUEEN,
                ChessPiece.PieceType.KING,
                ChessPiece.PieceType.BISHOP,
                ChessPiece.PieceType.KNIGHT,
                ChessPiece.PieceType.ROOK
        };
        for (var i = 0; i < 8; i++) {
            board[0][i] = Piece.Create(ChessGame.TeamColor.WHITE, pieces[i]);
            board[1][i] = Piece.Create(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            board[6][i] = Piece.Create(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            board[7][i] = Piece.Create(ChessGame.TeamColor.BLACK, pieces[i]);
        }
    }

    public PiecePlacement getPiece(ChessGame.TeamColor color, ChessPiece.PieceType type) {
        for (var placement : collection()) {
            if (placement.getPiece().getTeamColor() == color && placement.getPiece().getPieceType() == type) {
                return placement;
            }
        }
        return null;
    }

    /**
     * @param targetPos for attackers.
     * @return the pieces of the opposite color that are attacking the given square.
     */
    public Collection<ChessPosition> getAttackers(ChessPosition targetPos) {
        var attackers = new ArrayList<ChessPosition>();
        var targetPiece = getPiece(targetPos);

        for (var candidateAttacker : collection()) {
            if (candidateAttacker.getPiece().getTeamColor() != targetPiece.getTeamColor()) {
                var moves = candidateAttacker.pieceMoves(this);
                for (var move : moves) {
                    if (move.getEndPosition().equals(targetPos)) {
                        attackers.add(candidateAttacker.getPos());
                        break;
                    }
                }
            }
        }
        return attackers;
    }

    /**
     * @return true if the move is legal
     */
    public boolean isMoveLegal(ChessMove move) {
        var piece = getPiece(move.getStartPosition());
        var newBoard = new Board(this);
        newBoard.movePiece(move);
        var king = newBoard.getPiece(piece.getTeamColor(), ChessPiece.PieceType.KING);
        if (king == null || newBoard.getAttackers(king.getPos()).size() == 0) {
            return true;
        }
        return false;
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
