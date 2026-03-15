package com.example.fasttype;

import com.example.fasttype.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Entry point for the Fast Typing (Escritura Rápida) application.
 * Launches the JavaFX runtime and shows the welcome screen via {@link WelcomeStage}.
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public class Main extends Application {

    /**
     * JavaFX lifecycle method. Creates and displays the welcome screen.
     *
     * @param primaryStage the primary stage provided by the JavaFX runtime (not used directly)
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        new WelcomeStage(primaryStage);
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        launch(args);
    }
}