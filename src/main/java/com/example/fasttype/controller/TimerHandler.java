package com.example.fasttype.controller;

import com.example.fasttype.model.GameState;
import com.example.fasttype.view.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Manages the per-level countdown timer using a JavaFX {@link Timeline}.
 *
 * <p>Uses an inner {@link TickHandler} class that implements the
 * {@link javafx.event.EventHandler} interface to respond to each timer tick.
 * This structure demonstrates the use of inner classes and event handling
 * as required by the course guidelines.</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */

public class    TimerHandler {

    /** The JavaFX timeline that fires every second. */
    private Timeline timeline;

    /** Reference to the shared game state. */
    private final GameState gameState;

    /** Reference to the view interface for UI updates. */
    private final GameView gameView;

    /** Callback invoked when the timer runs out and the game must respond. */
    private final Runnable onTimeUp;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a TimerHandler.
     *
     * @param gameState the model to update on each tick
     * @param gameView  the view to refresh each second
     * @param onTimeUp  a callback executed when the timer reaches zero
     */

    public TimerHandler(GameState gameState, GameView gameView, Runnable onTimeUp) {
        this.gameState = gameState;
        this.gameView = gameView;
        this.onTimeUp = onTimeUp;
        buildTimeline();
    }

    // -------------------------------------------------------------------------
    // Inner Class – Tick Handler
    // -------------------------------------------------------------------------

    /**
     * Handles each one-second tick of the game timer.
     * Updates the model and view on every tick, and fires the time-up callback
     * when the countdown reaches zero.
     *
     * <p>Implemented as a named inner class to satisfy the course requirement
     * of using inner classes for event handling modularization.</p>
     */

    private class TickHandler implements javafx.event.EventHandler<javafx.event.ActionEvent> {

        /**
         * Called once per second by the {@link Timeline}.
         * Decrements the timer in the model and refreshes the view.
         *
         * @param event the action event fired by the timeline (unused directly)
         */

        @Override
        public void handle(javafx.event.ActionEvent event) {
            gameState.tickTimer();
            gameView.updateTimer(
                    gameState.getRemainingTime(),
                    gameState.getCurrentMaxTime()
            );

            if (gameState.isTimeUp()) {
                stop();
                onTimeUp.run();
            }
        }
    }

    // -------------------------------------------------------------------------
    // Public Methods
    // -------------------------------------------------------------------------

    /**
     * Starts (or restarts) the countdown timer from the current remaining time.
     */

    public void start() {
        stop();
        buildTimeline();
        timeline.play();
    }

    /**
     * Stops the countdown timer without resetting the model state.
     */

    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    // -------------------------------------------------------------------------
    // Private Helpers
    // -------------------------------------------------------------------------

    /**
     * Builds the JavaFX {@link Timeline} with a one-second {@link KeyFrame}
     * that repeats indefinitely, using the inner {@link TickHandler}.
     */

    private void buildTimeline() {
        TickHandler handler = new TickHandler();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), handler);
        timeline = new Timeline();
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
    }
}