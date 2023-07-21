package ui;

import model.GameData;

public interface DisplayHandler {
    void updateBoard(GameData game);

    void printMessage(String message);

}
