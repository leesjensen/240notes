package chess;

import java.util.*;

import static chess.ChessPiece.*;
import static chess.ChessGame.*;

public class BoardImpl implements ChessBoard {
    final private ChessPiece[][] squares = new ChessPiece[8][8];
    final private ArrayList<ChessMove> history = new ArrayList<>();


    public BoardImpl() {
    }


    public BoardImpl(ChessBoard board) {
        if (board instanceof BoardImpl copy) {
            for (var i = 0; i < 8; i++) {
                System.arraycopy(copy.squares[i], 0, squares[i], 0, 8);
            }
        }
    }

    public List<ChessMove> getHistory() {
        return history;
    }


    public void movePiece(ChessMove move) {
        var piece = getPiece(move.getStartPosition());

        // Handle promotion
        if (move.getPromotionPiece() != null) {
            piece = new PieceImpl(piece.getTeamColor(), move.getPromotionPiece());
        }
        // Handle castle
        else if ((piece.getPieceType() == PieceType.KING) &&
                (Math.abs(move.getStartPosition().getColumn() - move.getEndPosition().getColumn()) == 2)) {
            castle(move);
        }
        // Handle en passant
        else if ((piece.getPieceType() == PieceType.PAWN) &&
                (move.getStartPosition().getColumn() != move.getEndPosition().getColumn()) &&
                (getPiece(move.getEndPosition()) == null)) {
            enPassant(move);
        }

        removePiece(move.getStartPosition());
        addPiece(move.getEndPosition(), piece);

        history.add(move);
    }

    public void enPassant(ChessMove move) {
        var killRow = move.getEndPosition().getRow() == 6 ? 5 : 4;
        var killPosition = new PositionImpl(killRow, move.getEndPosition().getColumn());

        removePiece(killPosition);
    }

    public void castle(ChessMove move) {
        var row = move.getEndPosition().getRow();
        var rookMove = new MoveImpl(new PositionImpl(row, 1), new PositionImpl(row, 4), null);
        if (move.getEndPosition().getColumn() == 7) {
            rookMove = new MoveImpl(new PositionImpl(row, 8), new PositionImpl(row, 6), null);
        }
        movePiece(rookMove);
    }

    private void removePiece(ChessPosition position) {
        squares[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public void resetBoard() {
        var pieces = new PieceType[]{
                PieceType.ROOK,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.QUEEN,
                PieceType.KING,
                PieceType.BISHOP,
                PieceType.KNIGHT,
                PieceType.ROOK
        };
        for (var i = 0; i < 8; i++) {
            squares[0][i] = new PieceImpl(TeamColor.WHITE, pieces[i]);
            squares[1][i] = new PieceImpl(TeamColor.WHITE, PieceType.PAWN);
            squares[2][i] = null;
            squares[3][i] = null;
            squares[4][i] = null;
            squares[5][i] = null;
            squares[6][i] = new PieceImpl(TeamColor.BLACK, PieceType.PAWN);
            squares[7][i] = new PieceImpl(TeamColor.BLACK, pieces[i]);
        }
    }

    @Override
    public ChessPlacement getPlacement(ChessGame.TeamColor color, ChessPiece.PieceType type) {
        for (var placement : collection()) {
            if (placement.getPiece().getTeamColor() == color && placement.getPiece().getPieceType() == type) {
                return placement;
            }
        }
        return null;
    }

    @Override
    public boolean isAttacked(ChessPosition targetPos, ChessGame.TeamColor targetColor) {
        return !getAttackers(targetPos, targetColor).isEmpty();
    }

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

    public boolean isMoveLegal(ChessMove move) {
        var piece = getPiece(move.getStartPosition());

        // Test if this move was a castle, and if so is it valid.
        if (isMoveCastle(piece, move)) {
            if (!isCastleValid(piece, move)) {
                return false;
            }
        }

        // Test if this move causes the team's king to be put in check.
        var newBoard = new BoardImpl(this);
        newBoard.movePiece(move);
        var king = newBoard.getPlacement(piece.getTeamColor(), ChessPiece.PieceType.KING);
        return king == null || !king.isAttacked(newBoard);
    }


    private boolean isMoveCastle(ChessPiece piece, ChessMove move) {
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            return Math.abs(move.getStartPosition().getColumn() - move.getEndPosition().getColumn()) == 2;
        }
        return false;
    }


    private boolean isCastleValid(ChessPiece king, ChessMove move) {
        var color = king.getTeamColor();
        var teamRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;
        // Make sure king not being attacked.
        if (posNotAttacked(teamRow, 5, color)) {
            var castleLeft = move.getEndPosition().getColumn() == 3;
            if (castleLeft) {
                return posNotAttacked(teamRow, 3, color) && posNotAttacked(teamRow, 4, color);
            } else {
                return posNotAttacked(teamRow, 6, color) && posNotAttacked(teamRow, 7, color);
            }
        }
        return false;
    }


    @Override
    public boolean isOriginalPosition(ChessPosition pos) {
        for (var bh : getHistory()) {
            if (bh.getStartPosition().equals(pos)) {
                return false;
            }
        }
        return true;
    }


    @Override
    public ChessMove getLastMove() {
        return history.get(history.size() - 1);
    }

    private boolean posNotAttacked(int row, int col, ChessGame.TeamColor color) {
        var pos = new PositionImpl(row, col);
        return !isAttacked(pos, color);
    }


    @Override
    public boolean isSquareEmpty(int row, int col) {
        var pieceAt = getPiece(new PositionImpl(row, col));
        return pieceAt == null;
    }


    @Override
    public Collection<ChessPlacement> collection() {
        var result = new ArrayList<ChessPlacement>();

        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                if (squares[i][j] != null) {
                    result.add(new PlacementImpl(squares[i][j], new PositionImpl(i + 1, j + 1)));
                }
            }
        }
        return result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardImpl board = (BoardImpl) o;
        return Arrays.deepEquals(squares, board.squares);
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.deepHashCode(squares);
    }
}
