package com.example.fasttype.controller;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardAdapter implements EventHandler<KeyEvent> {

    private final Runnable onEnterPressed;

    //-----------------------------------------------------
    //Constructor
    //-----------------------------------------------------

    public KeyboardAdapter (Runnable onEnterPressed){
        this.onEnterPressed = onEnterPressed;
    }

    //-----------------------------------------------------
    //EventHandler Implementation
    //-----------------------------------------------------

    @Override
    public void handle(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            onEnterPressed.run();
        }
    }
}
