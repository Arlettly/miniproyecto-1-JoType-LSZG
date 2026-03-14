package com.example.fasttype.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main game window.
 * Extends {@link Stage} to encapsulate the FXML loading, scene setup,
 * and window configuration in a single reusable class.
 *
 * @author Lesly Zapata
 * @version 1.0
 */

public class GameStage extends Stage {

    /**
     * Constructs the game window by loading the FXML layout,
     * configuring the scene, and displaying the stage.
     *
     * @throws IOException if the FXML resource cannot be found or loaded
     */

    public GameStage() throws IOException {
        // Import the View
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/fasttype/VocabularyGame.fxml")
        );

        // Create the Parent
        Parent root = loader.load();
        // Create a new Scene
        Scene scene = new Scene(root);

        // Add a title to the Stage
        setTitle("JoType – Escritura Rápida");
        // Set the Stage to be resizable
        setResizable(false);
        // Add the Scene to the Stage
        setScene(scene);
        // Add the icon to the Stage
        getIcons().add(
                new Image(
                        String.valueOf(getClass().getResource(
                                "/com/example/fasttype/image/PinkHeart.png"))));
        // We put on a Show on Stage
        show();
    }
}
