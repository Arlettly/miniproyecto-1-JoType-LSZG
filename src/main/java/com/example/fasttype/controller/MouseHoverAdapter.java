package com.example.fasttype.controller;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Adapter class that adds hover (enter/exit) visual effects to JavaFX nodes.
 *
 * <p>Implements {@link EventHandler}{@code <MouseEvent>} and provides a
 * reusable way to attach enter and exit hover callbacks to any {@link Node}.
 * This satisfies the course requirement of using adapter classes to modularize
 * mouse event handling logic across multiple UI components.</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public class MouseHoverAdapter implements EventHandler<MouseEvent> {

    /** Callback invoked when the mouse enters the node. */
    private final Runnable onEnter;

    /** Callback invoked when the mouse exits the node. */
    private final Runnable onExit;

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    /**
     * Constructs a MouseHoverAdapter with enter and exit callbacks.
     *
     * @param onEnter the action to run when the mouse enters the node
     * @param onExit  the action to run when the mouse exits the node
     */
    public MouseHoverAdapter(Runnable onEnter, Runnable onExit) {
        this.onEnter = onEnter;
        this.onExit = onExit;
    }

    // -------------------------------------------------------------------------
    // Factory Method
    // -------------------------------------------------------------------------

    /**
     * Attaches both MOUSE_ENTERED and MOUSE_EXITED handlers to the given node
     * using a single MouseHoverAdapter instance per event type.
     *
     * @param node    the JavaFX node to attach hover effects to
     * @param onEnter action when mouse enters
     * @param onExit  action when mouse exits
     */
    public static void attach(Node node, Runnable onEnter, Runnable onExit) {
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, new MouseHoverAdapter(onEnter, null));
        node.addEventHandler(MouseEvent.MOUSE_EXITED,  new MouseHoverAdapter(null, onExit));
    }

    // -------------------------------------------------------------------------
    // EventHandler Implementation
    // -------------------------------------------------------------------------

    /**
     * Handles the mouse event. Delegates to the appropriate callback
     * based on the event type (ENTERED or EXITED).
     *
     * @param event the mouse event captured on the node
     */
    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_ENTERED && onEnter != null) {
            onEnter.run();
        } else if (event.getEventType() == MouseEvent.MOUSE_EXITED && onExit != null) {
            onExit.run();
        }
    }
}