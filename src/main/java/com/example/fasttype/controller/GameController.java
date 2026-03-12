package com.example.fasttype.controller;

import com.example.fasttype.model.GameState;
import com.example.fasttype.view.GameView;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Main controller for the Fast Typing game.
 *
 * <p>Implements both {@link Initializable} (for FXML lifecycle) and
 * {@link GameView} (the view contract). Wires all event handlers, delegates
 * game logic to {@link GameState}, and drives UI updates.</p>
 *
 * <p>Event handling is modularized using:</p>
 * <ul>
 *   <li>{@link TimerHandler} – inner class-based timer ticks</li>
 *   <li>{@link KeyboardAdapter} – Enter key listener on the input field</li>
 *   <li>{@link MouseHoverAdapter} – hover effects on the submit button</li>
 * </ul>
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public class GameController implements Initializable, GameView {

    // -------------------------------------------------------------------------
    // FXML Injected Components
    // -------------------------------------------------------------------------

    /** Label showing the current level. */
    @FXML private Label levelLabel;

    /** Label showing the countdown timer. */
    @FXML private Label timerLabel;

    /** Progress bar reflecting remaining time. */
    @FXML private ProgressBar timerBar;

    /** Label displaying the target word or phrase. */
    @FXML private Label targetWord;

    /** Background rectangle of the feedback message. */
    @FXML private Rectangle feedbackBg;

    /** Label showing success or error feedback. */
    @FXML private Label feedbackLabel;

    /** Container for the feedback elements (for fade animations). */
    @FXML private StackPane feedbackPane;

    /** Input field where the player types the answer. */
    @FXML private TextField answerField;

    /** Button to submit the current answer. */
    @FXML private Button submitBtn;

    /** Label displaying the accumulated score. */
    @FXML private Label scoreLabel;

    /** Overlay panel shown on game over. */
    @FXML private VBox gameOverPane;

    /** Label in the game-over screen for the result title. */
    @FXML private Label gameOverTitle;

    /** Label in the game-over screen showing levels completed. */
    @FXML private Label gameOverLevels;

    /** Label in the game-over screen showing the final score. */
    @FXML private Label gameOverScore;

    // -------------------------------------------------------------------------
    // Internal State
    // -------------------------------------------------------------------------

    /** The game model holding all game logic and state. */
    private GameState gameState;

    /** Timer handler that manages the per-level countdown. */
    private TimerHandler timerHandler;

    /** Flag to prevent double-submission during feedback display. */
    private boolean submitting;

    // -------------------------------------------------------------------------
    // Initializable
    // -------------------------------------------------------------------------

    /**
     * Called by JavaFX after the FXML is loaded.
     * Wires all event handlers and starts the first game.
     *
     * @param location  the URL location (unused)
     * @param resources the resource bundle (unused)
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameState = new GameState();

        timerHandler = new TimerHandler(gameState, this, this::handleTimeUp);

        // Keyboard: ENTER submits the answer
        answerField.addEventHandler(
                javafx.scene.input.KeyEvent.KEY_PRESSED,
                new KeyboardAdapter(this::handleSubmit)
        );

        // Mouse: hover effect on the submit button rectangle
        MouseHoverAdapter.attach(
                submitBtn,
                () -> submitBtn.setStyle(submitBtn.getStyle() + "-fx-opacity: 0.85;"),
                () -> submitBtn.setStyle(submitBtn.getStyle().replace("-fx-opacity: 0.85;", ""))
        );

        startLevel();
    }

    // -------------------------------------------------------------------------
    // FXML Event Handlers
    // -------------------------------------------------------------------------

    /**
     * Handles the "¡LISTO!" button click.
     * Delegates to the central submit logic.
     */
    @FXML
    private void handleSubmit() {
        if (submitting || !gameState.isGameActive()) return;
        submitting = true;

        timerHandler.stop();
        String answer = answerField.getText();
        boolean correct = gameState.submitAnswer(answer);

        if (correct) {
            if (gameState.isWon()) {
                showGameOver(GameState.MAX_LEVEL, gameState.getScore(), true);
            } else {
                showFeedback("¡Correcto! 🎉", true);
                updateScore(gameState.getScore());
                // Pause briefly then load next level
                javafx.animation.PauseTransition pause =
                        new javafx.animation.PauseTransition(Duration.millis(900));
                pause.setOnFinished(e -> {
                    submitting = false;
                    startLevel();
                });
                pause.play();
            }
        } else {
            showFeedback("❌ Incorrecto", false);
            javafx.animation.PauseTransition pause =
                    new javafx.animation.PauseTransition(Duration.millis(1200));
            pause.setOnFinished(e -> showGameOver(gameState.getCurrentLevel() - 1,
                    gameState.getScore(), false));
            pause.play();
        }
    }

    /**
     * Called by {@link TimerHandler} when the countdown reaches zero.
     * Validates whatever the player has typed so far.
     */
    private void handleTimeUp() {
        if (submitting || !gameState.isGameActive()) return;
        submitting = true;

        String answer = answerField.getText();
        boolean correct = gameState.timeUp(answer);

        if (correct) {
            showFeedback("¡Correcto! ⏱", true);
            updateScore(gameState.getScore());
            javafx.animation.PauseTransition pause =
                    new javafx.animation.PauseTransition(Duration.millis(900));
            pause.setOnFinished(e -> {
                submitting = false;
                startLevel();
            });
            pause.play();
        } else {
            showFeedback("⏰ Tiempo agotado", false);
            javafx.animation.PauseTransition pause =
                    new javafx.animation.PauseTransition(Duration.millis(1200));
            pause.setOnFinished(e -> showGameOver(gameState.getCurrentLevel() - 1,
                    gameState.getScore(), false));
            pause.play();
        }
    }

    /**
     * Handles the "Reiniciar" button action from the game-over overlay.
     */
    @FXML
    private void handleRestart() {
        gameState.resetGame();
        resetUI();
        startLevel();
    }

    // -------------------------------------------------------------------------
    // GameView Implementation
    // -------------------------------------------------------------------------

    /** {@inheritDoc} */
    @Override
    public void updateLevel(int level) {
        levelLabel.setText("★ Nivel: " + level);
    }

    /** {@inheritDoc} */
    @Override
    public void updateTimer(int seconds, int maxSeconds) {
        Platform.runLater(() -> {
            timerLabel.setText(seconds + "s");
            double progress = maxSeconds > 0 ? (double) seconds / maxSeconds : 0;
            timerBar.setProgress(progress);

            // Change color as time runs low
            if (progress <= 0.25) {
                timerLabel.setStyle(timerLabel.getStyle().replace("#FF4444", "#CC0000")
                        .replace("#333333", "#CC0000"));
            }
        });
    }

    /** {@inheritDoc} */
    @Override
    public void showWord(String word) {
        targetWord.setText(word);

        // Pop animation on new word
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), targetWord);
        scale.setFromX(0.7);
        scale.setFromY(0.7);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();
    }

    /** {@inheritDoc} */
    @Override
    public void showFeedback(String message, boolean success) {
        feedbackLabel.setText(message);
        feedbackBg.setFill(success ? Color.web("#4CAF50") : Color.web("#EE1111"));
        feedbackPane.setVisible(true);
        feedbackPane.setOpacity(1.0);

        FadeTransition fade = new FadeTransition(Duration.millis(200), feedbackPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    /** {@inheritDoc} */
    @Override
    public void clearFeedback() {
        feedbackPane.setVisible(false);
        answerField.clear();
        answerField.requestFocus();
    }

    /** {@inheritDoc} */
    @Override
    public void updateScore(int score) {
        scoreLabel.setText(String.format("%,d", score));
    }

    /** {@inheritDoc} */
    @Override
    public void showGameOver(int levelsCompleted, int finalScore, boolean won) {
        timerHandler.stop();
        gameOverPane.setVisible(true);

        gameOverTitle.setText(won ? "🏆 ¡GANASTE!" : "💀 FIN DEL JUEGO");
        gameOverTitle.setStyle(gameOverTitle.getStyle()
                + (won ? "-fx-text-fill: #4CAF50;" : "-fx-text-fill: #EE1111;"));
        gameOverLevels.setText("Niveles completados: " + levelsCompleted);
        gameOverScore.setText("Puntuación final: " + String.format("%,d", finalScore));

        FadeTransition fade = new FadeTransition(Duration.millis(400), gameOverPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    /** {@inheritDoc} */
    @Override
    public void resetUI() {
        gameOverPane.setVisible(false);
        clearFeedback();
        updateScore(0);
        submitting = false;
    }

    // -------------------------------------------------------------------------
    // Private Helpers
    // -------------------------------------------------------------------------

    /**
     * Loads the current level's word into the UI and starts the timer.
     */
    private void startLevel() {
        clearFeedback();
        updateLevel(gameState.getCurrentLevel());
        showWord(gameState.getCurrentWord());
        updateTimer(gameState.getRemainingTime(), gameState.getCurrentMaxTime());
        timerHandler.start();
    }
}