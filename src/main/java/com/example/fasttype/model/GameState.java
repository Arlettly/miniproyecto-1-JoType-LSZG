package com.example.fasttype.model;

/**
 * Represents the current state of a Fast Typing game session.
 * Encapsulates level progression, scoring, timing, and word management.
 * This class follows a pure Model pattern with no JavaFX dependencies.
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public class GameState {

    // -------------------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------------------

    /** Initial time limit per level, in seconds. */
    public static final int INITIAL_TIME_SECONDS = 20;

    /** Minimum possible time limit per level, in seconds. */
    public static final int MIN_TIME_SECONDS = 2;

    /** Number of levels between each time reduction. */
    public static final int LEVELS_PER_TIME_REDUCTION = 5;

    /** Amount by which time is reduced every threshold, in seconds. */
    public static final int TIME_REDUCTION_AMOUNT = 2;

    /** Maximum level a player can reach (win condition). */
    public static final int MAX_LEVEL = 45;

    // -------------------------------------------------------------------------
    // State Fields
    // -------------------------------------------------------------------------

    /** Current level number (starts at 1). */
    private int currentLevel;

    /** Total accumulated score. */
    private int score;

    /** Remaining time for the current level, in seconds. */
    private int remainingTime;

    /** Maximum time allowed for the current level, in seconds. */
    private int currentMaxTime;

    /** The word or phrase the player must type in the current level. */
    private String currentWord;

    /** Whether the game is currently active (not over). */
    private boolean gameActive;

    /** Source of words/phrases for the game. */
    private final WordBank wordBank;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a new GameState and starts the game from level 1.
     */
    public GameState() {
        wordBank = new WordBank();
        resetGame();
    }

    // -------------------------------------------------------------------------
    // Public Methods
    // -------------------------------------------------------------------------

    /**
     * Resets the entire game back to its initial state (level 1, full time).
     */
    public void resetGame() {
        currentLevel = 1;
        score = 0;
        currentMaxTime = INITIAL_TIME_SECONDS;
        remainingTime = currentMaxTime;
        gameActive = true;
        currentWord = wordBank.getWordForLevel(currentLevel);
    }

    /**
     * Validates the player's typed answer against the current word.
     * If correct, advances the level and updates score.
     * If incorrect, ends the game.
     *
     * @param playerAnswer the text entered by the player
     * @return {@code true} if the answer was correct, {@code false} otherwise
     */
    public boolean submitAnswer(String playerAnswer) {
        if (!gameActive) return false;

        boolean correct = currentWord.equals(playerAnswer.trim());

        if (correct) {
            advanceLevel();
        } else {
            gameActive = false;
        }

        return correct;
    }

    /**
     * Called when the timer reaches zero.
     * Validates the current answer (if any) and ends the game if incorrect.
     *
     * @param playerAnswer the text currently in the input field
     * @return {@code true} if the answer was correct before time ran out
     */
    public boolean timeUp(String playerAnswer) {
        if (!gameActive) return false;

        boolean correct = currentWord.equals(playerAnswer.trim());

        if (correct) {
            advanceLevel();
        } else {
            gameActive = false;
        }

        return correct;
    }

    /**
     * Decrements the remaining time by one second.
     * Has no effect if the game is not active.
     */
    public void tickTimer() {
        if (gameActive && remainingTime > 0) {
            remainingTime--;
        }
    }

    /**
     * Checks whether the timer has reached zero.
     *
     * @return {@code true} if remaining time is zero or less
     */
    public boolean isTimeUp() {
        return remainingTime <= 0;
    }

    /**
     * Checks whether the player has won by reaching the maximum level.
     *
     * @return {@code true} if the current level exceeds the maximum
     */
    public boolean isWon() {
        return currentLevel > MAX_LEVEL;
    }

    // -------------------------------------------------------------------------
    // Private Helpers
    // -------------------------------------------------------------------------

    /**
     * Advances the game to the next level.
     * Updates the score, adjusts time every 5 levels, and loads a new word.
     */
    private void advanceLevel() {
        // Score: base points + time bonus
        score += 100 + (remainingTime * 10);
        currentLevel++;

        // Check win condition
        if (currentLevel > MAX_LEVEL) {
            gameActive = false;
            return;
        }

        // Reduce time every LEVELS_PER_TIME_REDUCTION levels
        if ((currentLevel - 1) % LEVELS_PER_TIME_REDUCTION == 0 && currentLevel > 1) {
            currentMaxTime = Math.max(MIN_TIME_SECONDS, currentMaxTime - TIME_REDUCTION_AMOUNT);
        }

        remainingTime = currentMaxTime;
        currentWord = wordBank.getWordForLevel(currentLevel);
    }

    // -------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------

    /**
     * Returns the current level number.
     *
     * @return current level
     */
    public int getCurrentLevel() { return currentLevel; }

    /**
     * Returns the player's accumulated score.
     *
     * @return total score
     */
    public int getScore() { return score; }

    /**
     * Returns the remaining seconds in the current level.
     *
     * @return remaining time in seconds
     */
    public int getRemainingTime() { return remainingTime; }

    /**
     * Returns the maximum time allowed for the current level.
     *
     * @return max time in seconds for this level
     */
    public int getCurrentMaxTime() { return currentMaxTime; }

    /**
     * Returns the word or phrase the player must type.
     *
     * @return current target word/phrase
     */
    public String getCurrentWord() { return currentWord; }

    /**
     * Returns whether the game is still active.
     *
     * @return {@code true} if the game has not ended
     */
    public boolean isGameActive() { return gameActive; }
}