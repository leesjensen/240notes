package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * The pawn piece and rules for movement.
 */
public class Pawn extends Piece {
    public Pawn(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.PAWN);
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        var direction = (pieceColor == ChessGame.TeamColor.WHITE ? 1 : -1);

        calculateMoves(board, pos, direction, 0, moves, false);
        calculateMoves(board, pos, direction, -1, moves, true);
        calculateMoves(board, pos, direction, 1, moves, true);
        addEnPassantMoves(board, pos, moves);

        if (pieceColor == ChessGame.TeamColor.WHITE && pos.getRow() == 2 || pieceColor == ChessGame.TeamColor.BLACK && pos.getRow() == 7) {
            if (Board.isSquareEmpty(board, pos.getRow() + direction, pos.getColumn()) && Board.isSquareEmpty(board, pos.getRow() + (direction * 2), pos.getColumn())) {
                moves.add(new Move(pos, new Position(pos.getRow() + (direction * 2), pos.getColumn()), null));
            }
        }

        return moves;
    }


    @Override
    protected void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc, HashSet<ChessMove> moves, boolean attack) {
        int row = pos.getRow() + rowInc;
        int col = pos.getColumn() + colInc;
        if (row > 0 && col > 0 && row < 9 && col < 9) {
            var newPos = new Position(row, col);
            var pieceAt = board.getPiece(newPos);
            if ((attack && pieceAt != null && pieceAt.getTeamColor() != pieceColor)
                    || (!attack && pieceAt == null)) {
                addMoveWithPossiblePromotion(pos, newPos, moves);
            }
        }
    }

    private void addMoveWithPossiblePromotion(ChessPosition pos, ChessPosition newPos, HashSet<ChessMove> moves) {
        if (newPos.getRow() == 1 || newPos.getRow() == 8) {
            moves.add(new Move(pos, newPos, PieceType.QUEEN));
            moves.add(new Move(pos, newPos, PieceType.BISHOP));
            moves.add(new Move(pos, newPos, PieceType.ROOK));
            moves.add(new Move(pos, newPos, PieceType.KNIGHT));
        } else {
            moves.add(new Move(pos, newPos, null));
        }
    }


    void addEnPassantMoves(ChessBoard cBoard, ChessPosition pos, HashSet<ChessMove> moves) {
        if (cBoard instanceof Board board) {
            var pawn = cBoard.getPiece(pos);
            var color = pawn.getTeamColor();
            var passantRow = color == ChessGame.TeamColor.BLACK ? 4 : 5;
            var startRow = color == ChessGame.TeamColor.BLACK ? 2 : 7;
            var attackRow = color == ChessGame.TeamColor.BLACK ? 3 : 6;

            addPassant(pos.getColumn(), pos.getColumn() + 1, passantRow, startRow, attackRow, board, color, moves);
            addPassant(pos.getColumn(), pos.getColumn() - 1, passantRow, startRow, attackRow, board, color, moves);
        }
    }

    void addPassant(int column, int passantColumn, int passantRow, int startRow, int attackRow, Board board, ChessGame.TeamColor color, HashSet<ChessMove> moves) {
        if (passantColumn >= 1 && passantColumn <= 8) {
            var passantMove = new Move(new Position(startRow, passantColumn), new Position(passantRow, passantColumn), null);
            var candidate = board.getPiece(passantMove.getEndPosition());
            if (candidate != null && candidate.getPieceType() == PieceType.PAWN && candidate.getTeamColor() != color) {
                var lastMove = board.getLastMove();
                if (passantMove.equals(lastMove)) {
                    moves.add(new Move(new Position(passantRow, column), new Position(attackRow, passantColumn), null));
                }
            }
        }
    }
}
