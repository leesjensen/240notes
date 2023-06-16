package chess;

import java.util.Collection;
import java.util.Collections;

public class Piece implements ChessPiece {

    ChessGame.TeamColor pieceColor;
    ChessPiece.PieceType type;


    public static ChessPiece Create(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        return switch (type) {
            case KING -> new King(pieceColor);
            case QUEEN -> new Queen(pieceColor);
            case BISHOP -> new Bishop(pieceColor);
            case KNIGHT -> new Knight(pieceColor);
            case ROOK -> new Rook(pieceColor);
            case PAWN -> new Pawn(pieceColor);
        };
    }

    protected Piece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    @Override
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    @Override
    public PieceType getPieceType() {
        return type;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return Collections.<ChessMove>emptyList();
    }
}
