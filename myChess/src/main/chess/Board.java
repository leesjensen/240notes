package chess;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board implements ChessBoard {

    private ChessPiece[][] board = new Piece[8][8];

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public void resetBoard() {

    }

    public Iterator<ChessPosition> iterator() {
        return new BoardIterator(board);
    }

    private class BoardIterator implements Iterator<ChessPosition> {
        ChessPiece[][] pieces;
        int row = 0, col = 0;
        boolean hasNext;

        BoardIterator(ChessPiece[][] pieces) {
            this.pieces = pieces;
            hasNext = getNext();
        }

        private boolean getNext() {
            for (var i = row; row < 8; row++) {
                for (var j = col; col < 8; col++) {
                    if (pieces[i][j] != null) {
                        row = i;
                        col = j;
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            return hasNext;
        }

        @Override
        public ChessPosition next() {
            if (hasNext) {
                var result = pieces[row][col];
                hasNext = getNext();
                return new Position(row, col);
            } else {
                throw new NoSuchElementException("No more elements left");
            }
        }
    }


}
