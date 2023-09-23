package passoffTests;

import chess.*;
import org.junit.jupiter.api.Assertions;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Used for testing your code
 * Add in code using your classes for each method for each FIXME
 */
public class TestFactory {

    //Chess Functions
    //------------------------------------------------------------------------------------------------------------------
    public static ChessBoard getNewBoard() {
        // FIXME
        return new BoardImpl();
    }

    public static ChessGame getNewGame() {
        // FIXME
        return new GameImpl();
    }

    public static ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        // FIXME
        return new PieceImpl(pieceColor, type);
    }

    public static ChessPosition getNewPosition(Integer row, Integer col) {
        // FIXME
        return new PositionImpl(row, col);
    }

    public static ChessMove getNewMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        // FIXME
        return new MoveImpl(startPosition, endPosition, promotionPiece);
    }
    //------------------------------------------------------------------------------------------------------------------


    //Server API's
    //------------------------------------------------------------------------------------------------------------------
    public static String getServerPort() {
        return "8080";
    }
    //------------------------------------------------------------------------------------------------------------------


    //Websocket Tests
    //------------------------------------------------------------------------------------------------------------------
    public static Long getMessageTime() {
        /*
        Changing this will change how long tests will wait for the server to send messages.
        3000 Milliseconds (3 seconds) will be enough for most computers. Feel free to change as you see fit,
        just know increasing it can make tests take longer to run.
        (On the flip side, if you've got a good computer feel free to decrease it)
         */
        return 3000L;
    }
    //------------------------------------------------------------------------------------------------------------------


    static public void validateMoves(String boardText, int[] testCoordinates, int[][] moves) {
        var board = loadBoard(boardText);
        var position = getNewPosition(testCoordinates[0], testCoordinates[1]);
        var testPiece = board.getPiece(position);
        var validMoves = loadMoves(position, moves);

        Assertions.assertEquals(validMoves, testPiece.pieceMoves(board, position), "Wrong moves");
    }

    final static Map<Character, ChessPiece.PieceType> charToTypeMap = Map.of(
            'p', ChessPiece.PieceType.PAWN,
            'n', ChessPiece.PieceType.KNIGHT,
            'r', ChessPiece.PieceType.ROOK,
            'q', ChessPiece.PieceType.QUEEN,
            'k', ChessPiece.PieceType.KING,
            'b', ChessPiece.PieceType.BISHOP
    );

    public static ChessBoard loadBoard(String boardText) {
        var board = getNewBoard();
        int row = 8;
        int column = 1;
        for (var c : boardText.toCharArray()) {
            switch (c) {
                case '\n' -> {
                    column = 1;
                    row--;
                }
                case ' ' -> column++;
                case '|' -> {
                }
                default -> {
                    ChessGame.TeamColor color = Character.isLowerCase(c) ? ChessGame.TeamColor.BLACK : ChessGame.TeamColor.WHITE;
                    var type = charToTypeMap.get(Character.toLowerCase(c));
                    var position = TestFactory.getNewPosition(row, column);
                    var piece = TestFactory.getNewPiece(color, type);
                    board.addPiece(position, piece);
                    column++;
                }
            }
        }
        return board;
    }

    public static Set<ChessMove> loadMoves(ChessPosition startPosition, int[][] endPositions) {
        var validMoves = new HashSet<ChessMove>();
        for (var endPosition : endPositions) {
            validMoves.add(TestFactory.getNewMove(startPosition, TestFactory.getNewPosition(endPosition[0], endPosition[1]), null));
        }
        return validMoves;
    }
}
