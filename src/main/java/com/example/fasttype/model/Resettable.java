package com.example.fasttype.model;

/**
 * Defines a contract for objects that can be reset to their initial state.
 *
 * <p>Implemented by game components that support a full restart without
 * requiring the creation of a new instance</p>
 *
 * @author Lesly Zapata
 * @version 1.0
 */
public interface Resettable {
    /**
     * Reset the implementing object to its initial state
     */

    void reset();
}
