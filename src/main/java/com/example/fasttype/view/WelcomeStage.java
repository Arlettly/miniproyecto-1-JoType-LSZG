package com.example.fasttype.view;

import com.example.fasttype.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the welcome screen window of the Fast Typing game.
 *
 * <p>Uses the primary stage passed from {@link com.example.fasttype.Main}.
 * When the player clicks "¡JUGAR!", this stage closes and {@link GameStage}
 * opens.</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public class WelcomeStage {

    /**
     * Constructs and displays the welcome screen on the primary stage.
     *
     * @param primaryStage the primary stage provided by the JavaFX runtime
     * @throws IOException if the FXML resource cannot be found or loaded
     */
    public WelcomeStage(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/fasttype/Welcome-View.fxml")
        );

        // Create the Parent
        Parent root = loader.load();

        // Give the controller a reference to close this stage when "JUGAR" is clicked
        WelcomeController controller = loader.getController();
        controller.setStage(primaryStage);

        // Create a new Scene
        Scene scene = new Scene(root, 320, 500);
        // Add a title to the Stage
        primaryStage.setTitle("JoType – Escritura Rápida");
        // Set the Stage to be resizable
        primaryStage.setResizable(false);
        // Add the Scene to the Stage
        primaryStage.setScene(scene);
        // Add the icon to the Stage
        primaryStage.getIcons().add(
                new Image(
                        String.valueOf(getClass().getResource(
                                "/com/example/fasttype/image/PinkHeart.png"))));
        // We put on a Show on Stage
        primaryStage.show();
    }
}
