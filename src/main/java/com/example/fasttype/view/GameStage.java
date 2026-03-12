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
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/fasttype/VocabularyGame.fxml")
        );

        Parent root = loader.load();
        Scene scene = new Scene(root);

        setTitle("JoType – Escritura Rápida");
        setResizable(false);
        setScene(scene);
        getIcons().add(
                new Image(
                        String.valueOf(getClass().getResource(
                                "/com/example/fasttype/image/PinkHeart.png"))));
        show();
    }
}
