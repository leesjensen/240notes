package chess;


import java.util.Collection;
import java.util.HashSet;

public class Knight extends Piece {
    public Knight(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }


    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var moves = new HashSet<ChessMove>();
        calculateMoves(board, pos, 2, 1, moves, false);
        calculateMoves(board, pos, 2, -1, moves, false);
        calculateMoves(board, pos, 1, 2, moves, false);
        calculateMoves(board, pos, -1, 2, moves, false);
        calculateMoves(board, pos, -2, -1, moves, false);
        calculateMoves(board, pos, -2, 1, moves, false);
        calculateMoves(board, pos, -1, -2, moves, false);
        calculateMoves(board, pos, 1, -2, moves, false);
        return moves;
    }
}