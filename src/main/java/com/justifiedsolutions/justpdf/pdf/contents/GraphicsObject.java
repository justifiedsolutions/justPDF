/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

/**
 * Specifies a graphics object which represents a state in the state diagram displayed in Figure 9 of the PDF
 * specification.
 *
 * @see "ISO 32000-1:2008, 8.2"
 * @see "ISO 32000-1:2008, Figure 9"
 */
interface GraphicsObject {

    /**
     * Determines if the specified {@link GraphicsOperator} is valid for the current {@link GraphicsObject}.
     *
     * @param operator the operator to check
     * @return {@code true} if the operator is valid, {@code false} otherwise
     */
    boolean isValidOperator(GraphicsOperator operator);

    /**
     * Determines if the specified {@link GraphicsOperator} ends the current {@link GraphicsObject}.
     *
     * @param operator the operator to check
     * @return {@code true} if the operator ends the state, {@code false} otherwise
     */
    boolean endsGraphicsObject(GraphicsOperator operator);

    /**
     * Creates a new {@link GraphicsObject} that replaces the current one. The new object is determined by the specified
     * {@link GraphicsOperator}. This method should only only be called if {@link #endsGraphicsObject(GraphicsOperator)}
     * returns {@code true}.
     *
     * @param operator the opertor that ends the current state
     * @return the new state
     */
    GraphicsObject getNewGraphicsObject(GraphicsOperator operator);
}
