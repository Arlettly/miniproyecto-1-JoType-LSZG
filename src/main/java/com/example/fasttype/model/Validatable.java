package com.example.fasttype.model;

/**
 * Defines a contract for objects that can validate a player's typed answer
 *
 * <p>Implemented by game components responsible for comparing the player's
 * input against the expected word or phrase</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public interface Validatable {

    /**
     * Validates the player's answer against the current target word.
     *
     * @param playerAnswer the tex entered by the player
     * @return {@code true} inf the answer is correct, {@code false} otherwise
     */

    boolean submitAnswer(String playerAnswer);

    /**
     * Validates the player's answer when the timer expires.
     *
     * @param playerAnswer the text currently in the input field
     * @return {@code true} if the answer was correct before the time ran out
     */

    boolean timeUp(String playerAnswer);
}
