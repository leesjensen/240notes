package chess;

import chess.rules.Rules;

import java.util.Collection;
import java.util.HashSet;

public class PieceImpl implements ChessPiece {
    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;


    public PieceImpl(ChessGame.TeamColor pieceColor, ChessPiece.PieceType pieceType) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition pos) {
        var piece = board.getPiece(pos);

        if (piece != null) {
            return Rules.movementRule(piece.getPieceType()).moves(board, pos);
        }

        return new HashSet<>();
    }
}
