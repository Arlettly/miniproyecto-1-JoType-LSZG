package com.example.fasttype.controller;

import com.example.fasttype.view.GameStage;
import com.example.fasttype.view.WelcomeView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the welcome screen (Welcome-View.fxml).
 *
 * <p>Implements {@link WelcomeView} to satisfy the view contract and
 * {@link Initializable} to load the character image at startup.</p>
 *
 * @author JoType
 * @version 1.0
 */
public class WelcomeController implements WelcomeView, Initializable {

    /** The primary stage, used to close the welcome screen. */
    private Stage stage;

    /** Play button injected from FXML. */
    @FXML
    private Button playBtn;

    /** ImageView for the Hello Kitty character illustration. */
    @FXML
    private ImageView characterImage;

    // -------------------------------------------------------------------------
    // Initializable
    // -------------------------------------------------------------------------

    /**
     * Called by JavaFX after the FXML is loaded.
     * Loads the character image from resources.
     *
     * @param location  unused
     * @param resources unused
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream is = getClass().getResourceAsStream(
                    "/com/example/fasttype/image/HelloKitty.png");
            if (is != null) {
                characterImage.setImage(new Image(is));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -------------------------------------------------------------------------
    // WelcomeView Implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FXML
    public void handlePlay() {
        try {
            stage.close();
            new GameStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}