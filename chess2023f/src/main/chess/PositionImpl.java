package chess;

/**
 * Concrete implementation of the {@link ChessPosition} interface.
 */
public class PositionImpl implements ChessPosition {

    private final int row;
    private final int col;

    public PositionImpl(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return col;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var position = (PositionImpl) o;
        return (row == position.row && col == position.col);
    }

    @Override
    public int hashCode() {
        return ((10 * row) + col);
    }


    @Override
    public String toString() {
        return String.format("%d%d", row, col);
    }
}
