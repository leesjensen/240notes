package ui;

import chess.Game;

public interface DisplayHandler {
    void updateBoard(Game game);

    void printMessage(String message);

}
