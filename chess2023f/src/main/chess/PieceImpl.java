package chess;

import chess.rules.Rules;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

public class PieceImpl implements ChessPiece {
    private final ChessGame.TeamColor pieceColor;
    private final ChessPiece.PieceType pieceType;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceImpl piece = (PieceImpl) o;
        return pieceColor == piece.pieceColor && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }
}
