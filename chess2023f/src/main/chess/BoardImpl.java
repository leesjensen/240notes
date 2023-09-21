package chess;

public class BoardImpl implements ChessBoard {

    final private ChessPiece[][] squares = new ChessPiece[8][8];


    public BoardImpl() {
    }

    public BoardImpl(ChessBoard copy) {
        if (copy instanceof BoardImpl c) {
            for (var i = 0; i < 8; i++) {
                System.arraycopy(c.squares[i], 0, squares[i], 0, 8);
            }
        }
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
            squares[0][i] = new PieceImpl(ChessGame.TeamColor.WHITE, pieces[i]);
            squares[1][i] = new PieceImpl(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            squares[2][i] = null;
            squares[3][i] = null;
            squares[4][i] = null;
            squares[5][i] = null;
            squares[6][i] = new PieceImpl(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            squares[7][i] = new PieceImpl(ChessGame.TeamColor.BLACK, pieces[i]);
        }
    }
}
