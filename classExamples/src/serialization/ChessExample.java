package serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

public class ChessExample {

    public static void main(String[] args) throws Exception {
        var board = new ChessBoard();
        var json = new Gson().toJson(board);
        System.out.println(json);

        var noAdaptorBoard = new Gson().fromJson(json, ChessBoard.class);
        var adaptorBoard = createSerializer().fromJson(json, ChessBoard.class);

        System.out.println(adaptorBoard.getPiece(1, 1).getClass());
        System.out.println(noAdaptorBoard.getPiece(1, 1).getClass());
    }

    public static Gson createSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(ChessPiece.class,
                (JsonDeserializer<ChessPiece>) (el, type, ctx) -> {
                    ChessPiece chessPiece = null;
                    if (el.isJsonObject()) {
                        String pieceType = el.getAsJsonObject().get("type").getAsString();
                        switch (PieceType.valueOf(pieceType)) {
                            case PAWN -> chessPiece = ctx.deserialize(el, Pawn.class);
                            case ROOK -> chessPiece = ctx.deserialize(el, Rook.class);
                            case KNIGHT -> chessPiece = ctx.deserialize(el, Knight.class);
                            case BISHOP -> chessPiece = ctx.deserialize(el, Bishop.class);
                            case QUEEN -> chessPiece = ctx.deserialize(el, Queen.class);
                            case KING -> chessPiece = ctx.deserialize(el, King.class);
                        }
                    }
                    return chessPiece;
                });

        return gsonBuilder.create();
    }

    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    public enum TeamColor {
        WHITE,
        BLACK
    }

    public static class ChessPiece {
        public final TeamColor color;
        public final PieceType type;

        public ChessPiece(TeamColor color, PieceType type) {
            this.color = color;
            this.type = type;
        }

    }

    public static class ChessBoard {
        final private ChessPiece[][] squares = new ChessPiece[8][8];
        final Class[] pieceClasses = new Class[]{
                Rook.class,
                Knight.class,
                Bishop.class,
                Queen.class,
                King.class,
                Bishop.class,
                Knight.class,
                Rook.class
        };

        public ChessBoard() throws Exception {


            for (var i = 0; i < 8; i++) {
                squares[0][i] = (ChessPiece) pieceClasses[0].getDeclaredConstructor(TeamColor.class).newInstance(TeamColor.WHITE);
                squares[1][i] = new Pawn(TeamColor.WHITE);
                squares[2][i] = null;
                squares[3][i] = null;
                squares[4][i] = null;
                squares[5][i] = null;
                squares[6][i] = new Pawn(TeamColor.WHITE);
                squares[7][i] = (ChessPiece) pieceClasses[0].getDeclaredConstructor(TeamColor.class).newInstance(TeamColor.BLACK);
            }
        }

        public Object getPiece(int row, int col) {
            return squares[row][col];
        }
    }

    public static class Pawn extends ChessPiece {
        public Pawn(TeamColor color) {
            super(color, PieceType.PAWN);
        }
    }

    public static class Rook extends ChessPiece {
        public Rook(TeamColor color) {
            super(color, PieceType.ROOK);
        }
    }

    public static class Bishop extends ChessPiece {
        public Bishop(TeamColor color) {
            super(color, PieceType.BISHOP);
        }
    }

    public static class Knight extends ChessPiece {
        public Knight(TeamColor color) {
            super(color, PieceType.KNIGHT);
        }
    }

    public static class Queen extends ChessPiece {
        public Queen(TeamColor color) {
            super(color, PieceType.QUEEN);
        }
    }

    public static class King extends ChessPiece {
        public King(TeamColor color) {
            super(color, PieceType.KING);
        }
    }

}
