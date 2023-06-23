package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Board implements ChessBoard {

    final private ChessPiece[][] board = new ChessPiece[8][8];

    final private HashSet<Board> history = new HashSet<>();

    public Board() {
    }

    public Board(ChessBoard copy) {
        if (copy instanceof Board) {
            var c = (Board) copy;
            for (var i = 0; i < 8; i++) {
                System.arraycopy(c.board[i], 0, board[i], 0, 8);
            }

            addHistory();
        }
    }

    public void movePiece(ChessMove move) {
        var piece = getPiece(move.getStartPosition());
        removePiece(move.getStartPosition());
        addPiece(move.getEndPosition(), piece);

        addHistory();
    }

    private void addHistory() {
        var bh = new Board();
        for (var i = 0; i < 8; i++) {
            System.arraycopy(board[i], 0, bh.board[i], 0, 8);
        }
        history.add(bh);
    }

    public Collection<Board> getHistory() {
        return history;
    }

    private void removePiece(ChessPosition position) {
        board[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public void resetBoard() {
        history.clear();

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
            board[0][i] = Piece.create(ChessGame.TeamColor.WHITE, pieces[i]);
            board[1][i] = Piece.create(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            board[6][i] = Piece.create(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            board[7][i] = Piece.create(ChessGame.TeamColor.BLACK, pieces[i]);
        }

        history.add(new Board(this));
    }

    public PiecePlacement getPiece(ChessGame.TeamColor color, ChessPiece.PieceType type) {
        for (var placement : collection()) {
            if (placement.getPiece().getTeamColor() == color && placement.getPiece().getPieceType() == type) {
                return placement;
            }
        }
        return null;
    }


    public boolean isAttacked(ChessPosition targetPos, ChessGame.TeamColor targetColor) {
        return !getAttackers(targetPos, targetColor).isEmpty();
    }

    /**
     * @param targetPos for attackers.
     * @return the pieces of the opposite color that are attacking the given square.
     */
    public Collection<ChessPosition> getAttackers(ChessPosition targetPos, ChessGame.TeamColor targetColor) {
        var attackers = new ArrayList<ChessPosition>();

        for (var candidateAttacker : collection()) {
            if (candidateAttacker.getPiece().getTeamColor() != targetColor) {
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
     * isMoveLegal makes the move on a new board and checks to see if the
     * king is in check after the move.
     *
     * @return true if the move is legal
     */
    public boolean isMoveLegal(ChessMove move) {
        var piece = getPiece(move.getStartPosition());

        // Test if this move was a castle, and if so is it valid.
        if (isMoveCastle(piece, move)) {
            if (!isCastleValid(piece, move)) {
                return false;
            }
        }

        // Test if this move causes the team's king to be put in check.
        var newBoard = new Board(this);
        newBoard.movePiece(move);
        var king = newBoard.getPiece(piece.getTeamColor(), ChessPiece.PieceType.KING);
        return king == null || !king.isAttacked(newBoard);
    }

    boolean isMoveCastle(ChessPiece piece, ChessMove move) {
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            if (Math.abs(move.getStartPosition().getColumn() - move.getEndPosition().getColumn()) == 2) {
                return true;
            }
        }
        return false;
    }


    boolean isCastleValid(ChessPiece king, ChessMove move) {
        var color = king.getTeamColor();
        var requiredRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;
        if (king.getPieceType() == ChessPiece.PieceType.KING && posNotAttacked(requiredRow, 5, color)) {
            // Go through the history and make sure nothing moved.
            var castleLeft = move.getEndPosition().getColumn() == 3;
            for (var bh : getHistory()) {
                if (!bh.isPieceInSquare(requiredRow, 5, king)) {
                    return false;
                }
                var rook = bh.getPiece(new Position(requiredRow, (castleLeft ? 1 : 8)));
                if (rook == null || rook.getPieceType() != ChessPiece.PieceType.ROOK) {
                    return false;
                }
            }

            // Make sure nothing is attacking the king squares and that they are empty.
            if (castleLeft) {
                if (!Board.isSquareEmpty(this, requiredRow, 2) || posAttackedOrNotEmpty(requiredRow, 3, color) || posAttackedOrNotEmpty(requiredRow, 4, color)) {
                    return false;
                }
            } else {
                if (posAttackedOrNotEmpty(requiredRow, 6, color) || posAttackedOrNotEmpty(requiredRow, 7, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean posNotAttacked(int row, int col, ChessGame.TeamColor color) {
        var pos = new Position(row, col);
        return !isAttacked(pos, color);
    }

    boolean posAttackedOrNotEmpty(int row, int col, ChessGame.TeamColor color) {
        var pos = new Position(row, col);
        return isAttacked(pos, color) || getPiece(pos) != null;
    }


    public boolean isPieceInSquare(int row, int col, ChessPiece piece) {
        var pieceAt = getPiece(new Position(row, col));
        return piece.equals(pieceAt);
    }

    static public boolean isSquareEmpty(ChessBoard board, int row, int col) {
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
