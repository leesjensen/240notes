package model;

import chess.ChessGame;

/**
 * Represents the serialization of a game. This includes who the players are, and the game itself.
 */
public record GameData(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game) {
}
