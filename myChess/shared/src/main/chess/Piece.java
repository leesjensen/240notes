package chess;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Base class for all chess pieces. This class also provides a factory method for creating pieces.
 */
public class Piece implements ChessPiece {

    protected ChessGame.TeamColor pieceColor;
    protected ChessPiece.PieceType type;

    public static Piece create(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
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
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return String.format("%s:%s", type, pieceColor);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Piece piece = (Piece) o;
        return pieceColor == piece.pieceColor && type == piece.type;
    }

    @Override
    public int hashCode() {
        return (1000 * pieceColor.ordinal()) + type.ordinal();
    }


    protected void calculateMoves(ChessBoard board, ChessPosition pos, int rowInc, int colInc, HashSet<ChessMove> moves, boolean allowDistance) {
        int row = pos.getRow() + rowInc;
        int col = pos.getColumn() + colInc;
        while (row > 0 && col > 0 && row < 9 && col < 9) {
            var newPos = new Position(row, col);
            var pieceAt = board.getPiece(newPos);
            if (pieceAt == null || pieceAt.getTeamColor() != pieceColor) {
                moves.add(new Move(pos, newPos, null));
                row += rowInc;
                col += colInc;
            }

            if (!allowDistance || pieceAt != null) {
                break;
            }
        }
    }


    private static final TypeAdapter<Piece> chessPieceTypeAdapter = new TypeAdapter<>() {
        @Override
        public void write(JsonWriter w, Piece chessPiece) throws IOException {
            if (chessPiece != null) {
                w.beginObject();
                w.name("pieceColor");
                w.value(chessPiece.pieceColor.toString());
                w.name("type");
                w.value(chessPiece.type.toString());
                w.endObject();
            } else {
                w.nullValue();
            }
        }

        @Override
        public Piece read(JsonReader jsonReader) {
            Piece chessPiece = new Gson().fromJson(jsonReader, Piece.class);
            if (chessPiece != null) {
                chessPiece = Piece.create(chessPiece.pieceColor, chessPiece.type);
            }
            return chessPiece;
        }
    };

    public static TypeAdapter<Piece> getJsonTypeAdapter() {
        return chessPieceTypeAdapter;
    }

}
