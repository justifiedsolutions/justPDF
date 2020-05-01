/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

/**
 * This is a type of {@link GraphicsOperator} that alters a value in the {@link GraphicsState}.
 *
 * @see "ISO 32000-1:2008, 8.4"
 */
public interface GraphicsStateOperator extends GraphicsOperator {

    /**
     * Determines if applying this {@link GraphicsOperator} would alter a value in the {@link GraphicsState}.
     *
     * @param currentState the current GraphicsState
     * @return true if the state would change if this operator were added
     */
    boolean changesState(GraphicsState currentState);

    /**
     * Alters the {@link GraphicsState} by applying this {@link GraphicsOperator}.
     *
     * @param state the current state
     */
    void changeState(GraphicsState state);
}
