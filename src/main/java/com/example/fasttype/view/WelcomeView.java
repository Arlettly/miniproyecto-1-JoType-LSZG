package com.example.fasttype.view;

import javafx.stage.Stage;

/**
 * Defines the contract for the welcome screen controller.
 *
 * <p>Implemented by {@link com.example.fasttype.controller.WelcomeController}
 * to decouple the welcome screen logic from its concrete implementation,
 * following the same pattern as {@link GameView}.</p>
 *
 * @author JoType
 * @version 1.0
 */
public interface WelcomeView {

    /**
     * Receives the primary stage so the controller can close it
     * when transitioning to the game screen.
     *
     * @param stage the primary stage provided by the JavaFX runtime
     */
    void setStage(Stage stage);

    /**
     * Handles the action triggered when the player clicks "¡JUGAR!".
     * Closes the welcome screen and opens the game window.
     */
    void handlePlay();
}