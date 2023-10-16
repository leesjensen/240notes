package chess;

import chess.rules.Rules;
import com.google.gson.Gson;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Concrete implementation of the {@link ChessPiece} interface.
 */
public class PieceImpl implements ChessPiece {
    public final ChessGame.TeamColor pieceColor;
    public final ChessPiece.PieceType pieceType;


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
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
