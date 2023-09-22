package chess;

import java.util.ArrayList;
import java.util.Collection;

import static chess.ChessPiece.*;
import static chess.ChessGame.*;

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
    public boolean isAttacked(ChessPosition targetPos, ChessGame.TeamColor targetColor) {
        return false;
        //return !getAttackers(targetPos, targetColor).isEmpty();
    }
}
