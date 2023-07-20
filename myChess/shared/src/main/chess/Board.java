package chess;

import java.util.*;

public class Board implements ChessBoard {

    final private Piece[][] board = new Piece[8][8];

    final private ArrayList<ChessMove> history = new ArrayList<>();

    public Board() {
    }

    public Board(ChessBoard copy) {
        if (copy instanceof Board c) {
            for (var i = 0; i < 8; i++) {
                System.arraycopy(c.board[i], 0, board[i], 0, 8);
            }
        }
    }

    public void movePiece(ChessMove move) {
        var piece = getPiece(move.getStartPosition());
        removePiece(move.getStartPosition());
        addPiece(move.getEndPosition(), piece);

        history.add(move);
    }

    public List<ChessMove> getHistory() {
        return history;
    }

    private void removePiece(ChessPosition position) {
        board[position.getRow() - 1][position.getColumn() - 1] = null;
    }

    @Override
    public void addPiece(ChessPosition position, ChessPiece piece) {
        board[position.getRow() - 1][position.getColumn() - 1] = (Piece) piece;
    }

    @Override
    public ChessPiece getPiece(ChessPosition position) {
        return board[position.getRow() - 1][position.getColumn() - 1];
    }

    @Override
    public void resetBoard() {
        history.clear();

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
            board[0][i] = Piece.create(ChessGame.TeamColor.WHITE, pieces[i]);
            board[1][i] = Piece.create(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            board[6][i] = Piece.create(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            board[7][i] = Piece.create(ChessGame.TeamColor.BLACK, pieces[i]);
        }
    }

    public PiecePlacement getPiece(ChessGame.TeamColor color, ChessPiece.PieceType type) {
        for (var placement : collection()) {
            if (placement.getPiece().getTeamColor() == color && placement.getPiece().getPieceType() == type) {
                return placement;
            }
        }
        return null;
    }


    public boolean isAttacked(ChessPosition targetPos, ChessGame.TeamColor targetColor) {
        return !getAttackers(targetPos, targetColor).isEmpty();
    }

    /**
     * @param targetPos for attackers.
     * @return the pieces of the opposite color that are attacking the given square.
     */
    public Collection<ChessPosition> getAttackers(ChessPosition targetPos, ChessGame.TeamColor targetColor) {
        var attackers = new ArrayList<ChessPosition>();

        for (var candidateAttacker : collection()) {
            if (candidateAttacker.getPiece().getTeamColor() != targetColor) {
                var moves = candidateAttacker.pieceMoves(this);
                for (var move : moves) {
                    if (move.getEndPosition().equals(targetPos)) {
                        attackers.add(candidateAttacker.getPos());
                        break;
                    }
                }
            }
        }
        return attackers;
    }

    /**
     * isMoveLegal makes the move on a new board and checks to see if the
     * king is in check after the move.
     *
     * @return true if the move is legal
     */
    public boolean isMoveLegal(ChessMove move) {
        var piece = getPiece(move.getStartPosition());

        // Test if this move was a castle, and if so is it valid.
        if (isMoveCastle(piece, move)) {
            if (!isCastleValid(piece, move)) {
                return false;
            }
        }

        // Test if this move causes the team's king to be put in check.
        var newBoard = new Board(this);
        newBoard.movePiece(move);
        var king = newBoard.getPiece(piece.getTeamColor(), ChessPiece.PieceType.KING);
        return king == null || !king.isAttacked(newBoard);
    }

    boolean isMoveCastle(ChessPiece piece, ChessMove move) {
        if (piece.getPieceType() == ChessPiece.PieceType.KING) {
            return Math.abs(move.getStartPosition().getColumn() - move.getEndPosition().getColumn()) == 2;
        }
        return false;
    }


    boolean isCastleValid(ChessPiece king, ChessMove move) {
        var color = king.getTeamColor();
        var teamRow = color == ChessGame.TeamColor.BLACK ? 8 : 1;
        // Make sure king not being attacked.
        if (posNotAttacked(teamRow, 5, color)) {
            var castleLeft = move.getEndPosition().getColumn() == 3;
            if (castleLeft) {
                return posNotAttacked(teamRow, 3, color) && posNotAttacked(teamRow, 4, color);
            } else {
                return posNotAttacked(teamRow, 6, color) && posNotAttacked(teamRow, 7, color);
            }
        }
        return false;
    }

    boolean isOriginalPosition(ChessPosition pos) {
        for (var bh : getHistory()) {
            if (bh.getStartPosition().equals(pos)) {
                return false;
            }
        }
        return true;
    }


    ChessMove getLastMove() {
        return history.get(history.size() - 1);
    }

    boolean posNotAttacked(int row, int col, ChessGame.TeamColor color) {
        var pos = new Position(row, col);
        return !isAttacked(pos, color);
    }

    static public boolean isSquareEmpty(ChessBoard board, int row, int col) {
        var pieceAt = board.getPiece(new Position(row, col));
        return pieceAt == null;
    }

    public Collection<PiecePlacement> collection() {
        var result = new ArrayList<PiecePlacement>();

        for (var i = 0; i < 8; i++) {
            for (var j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    result.add(new PiecePlacement(board[i][j], new Position(i + 1, j + 1)));
                }
            }
        }
        return result;
    }


    private static final int BLACK = 0;
    private static final int RED = 1;
    private static final int GREEN = 2;
    private static final int YELLOW = 3;
    private static final int BLUE = 4;
    private static final int MAGENTA = 5;
    private static final int CYAN = 6;
    private static final int WHITE = 7;

    private static final String COLOR_RESET = "\u001b[0m";

    /**
     * Set both the foreground and background color. Foreground is 3, background is 4.
     */
    private static String color(int FG, int BG) {
        return String.format("\u001b[3%d;4%dm", FG, BG);
    }

    /**
     * Set the foreground color.
     */
    private static String color(int FG) {
        return String.format("\u001b[1;3%dm", FG);
    }

    private static final String BORDER = color(BLACK, YELLOW);

    private static final String BOARD_BLACK = color(WHITE, BLACK);
    private static final String BOARD_WHITE = color(BLACK, WHITE);

    private static final String BLACK_PIECE = color(RED);
    private static final String WHITE_PIECE = color(GREEN);

    Map<ChessPiece.PieceType, String> pieceMap = Map.of(
            ChessPiece.PieceType.KING, "K",
            ChessPiece.PieceType.QUEEN, "Q",
            ChessPiece.PieceType.BISHOP, "B",
            ChessPiece.PieceType.KNIGHT, "N",
            ChessPiece.PieceType.ROOK, "R",
            ChessPiece.PieceType.PAWN, "P"
    );

    @Override
    public String toString() {
        return toString(ChessGame.TeamColor.WHITE);
    }


    public String toString(ChessGame.TeamColor playerColor) {
        var sb = new StringBuilder();
        var currentSquare = BOARD_WHITE;
        var rows = new int[]{7, 6, 5, 4, 3, 2, 1, 0};
        var columns = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
        var columnsLetters = "    a  b  c  d  e  f  g  h    ";
        if (playerColor == ChessGame.TeamColor.BLACK) {
            columnsLetters = "    h  g  f  e  d  c  b  a    ";
            rows = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
            columns = new int[]{7, 6, 5, 4, 3, 2, 1, 0};
        }
        sb.append(BORDER).append(columnsLetters).append(COLOR_RESET).append("\n");
        for (var i : rows) {
            var row = " " + (i + 1) + " ";
            sb.append(BORDER).append(row).append(COLOR_RESET);
            for (var j : columns) {
                var piece = board[i][j];
                if (piece != null) {
                    var color = (piece.getTeamColor() == ChessGame.TeamColor.WHITE) ? WHITE_PIECE : BLACK_PIECE;
                    var p = pieceMap.get(piece.getPieceType());
                    sb.append(currentSquare).append(color).append(" ").append(p).append(" ").append(COLOR_RESET);
                } else {
                    sb.append(currentSquare).append("   ").append(COLOR_RESET);
                }
                currentSquare = currentSquare.equals(BOARD_BLACK) ? BOARD_WHITE : BOARD_BLACK;
            }
            sb.append(BORDER).append(row).append(COLOR_RESET);
            sb.append('\n');
            currentSquare = currentSquare.equals(BOARD_BLACK) ? BOARD_WHITE : BOARD_BLACK;
        }
        sb.append(BORDER).append(columnsLetters).append(COLOR_RESET).append("\n");

        return sb.toString();
    }
}
