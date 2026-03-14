package com.example.fasttype.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Adapter class that listens to keyboard events on the answer input field.
 *
 * <p>Implements {@link EventHandler}{@code <KeyEvent>} and acts as an adapter
 * between raw JavaFX key events and the game's submit logic. Specifically,
 * it intercepts the {@code ENTER} key to trigger answer validation without
 * requiring the player to click the submit button.</p>
 *
 * <p>Using an adapter class here satisfies the course requirement of applying
 * adapter-style event handling to modularize keyboard interaction logic.</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */

public class KeyboardAdapter implements EventHandler<KeyEvent> {

    private final Runnable onEnterPressed;

    //-----------------------------------------------------
    //Constructor
    //-----------------------------------------------------

    /**
     * Constructs a KeyboardAdapter with the given submit callback.
     *
     * @param onEnterPressed the action to run when ENTER is pressed
     */

    public KeyboardAdapter (Runnable onEnterPressed){
        this.onEnterPressed = onEnterPressed;
    }

    //-----------------------------------------------------
    //EventHandler Implementation
    //-----------------------------------------------------

    /**
     * Handles a {@link KeyEvent}. If the key pressed is {@link KeyCode#ENTER},
     * the submit callback is invoked.
     *
     * @param event the keyboard event captured on the input field
     */

    @Override
    public void handle(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            onEnterPressed.run();
        }
    }
}
