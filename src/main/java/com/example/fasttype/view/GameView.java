package com.example.fasttype.view;

/**
 * Defines the contract for updating the game's visual interface.
 * Implemented by {@link GameController} to decouple the controller logic
 * from direct JavaFX component references.
 *
 * <p>This interface is used as part of the event-oriented architecture,
 * where the controller notifies the view of state changes via this contract.</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public interface GameView {

    /**
     * Updates the displayed level badge.
     *
     * @param level the current level number to display
     */
    void updateLevel(int level);

    /**
     * Updates the countdown timer display and progress bar.
     *
     * @param seconds     remaining seconds to show
     * @param maxSeconds  maximum seconds for this level (used to compute progress ratio)
     */
    void updateTimer(int seconds, int maxSeconds);

    /**
     * Displays the target word or phrase the player must type.
     *
     * @param word the word or phrase to display
     */
    void showWord(String word);

    /**
     * Displays a feedback message with a visual style depending on success or failure.
     *
     * @param message the feedback text (e.g., "¡Correcto!", "Incorrecto")
     * @param success {@code true} for a green/positive style, {@code false} for red/negative
     */
    void showFeedback(String message, boolean success);

    /**
     * Hides the feedback label and clears the input field.
     */
    void clearFeedback();

    /**
     * Updates the score display.
     *
     * @param score the current accumulated score
     */
    void updateScore(int score);

    /**
     * Transitions the UI to the game-over screen, showing a summary.
     *
     * @param levelsCompleted number of levels the player successfully completed
     * @param finalScore      the player's final score
     * @param won             {@code true} if the player won (reached max level)
     */
    void showGameOver(int levelsCompleted, int finalScore, boolean won);

    /**
     * Resets all UI components to their initial state for a new game.
     */
    void resetUI();
}