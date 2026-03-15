package com.example.fasttype.model;

/**
 * Defines a contract for objects that track and expose level progression state.
 *
 * <p>Implemented by game components that manage the player's current level,
 * score and win/loss conditions throughout a game session</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */

public interface LevelProgressable {

    /**
     * Returns the current level number.
     *
     * @return current level
     */

    int getCurrentLevel();

    /**
     * Returns the player's accumulated score.
     *
     * @return total score
     */

    int getScore();

    /**
     * Returns whether the game session is still active.
     *
     * @return {@code true} if the game has not ended
     */

    boolean isGameActive();

    /**
     * Returns whether the player has reached the maximum level (win condition).
     *
     * @return {@code true} if the player won
     */

    boolean isWon();
}
