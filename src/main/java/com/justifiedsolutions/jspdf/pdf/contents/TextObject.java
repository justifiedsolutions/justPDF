/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

/**
 * A TextObject encompasses a group of {@link TextOperator}s in a PDF stream.
 *
 * @see "ISO 32000-1:2008, 8.2"
 * @see "ISO 32000-1:2008, 9.4.1"
 */
class TextObject implements GraphicsObject {

    @Override
    public boolean isValidOperator(GraphicsOperator operator) {
        if (operator instanceof BeginText) {
            return false;
        }

        return ((operator instanceof GeneralGraphicsOperator) || (operator instanceof ColorGraphicsOperator)
                || (operator instanceof TextOperator));
    }

    @Override
    public boolean endsGraphicsObject(GraphicsOperator operator) {
        return (operator instanceof EndText);
    }

    @Override
    public GraphicsObject getNewGraphicsObject(GraphicsOperator operator) {
        return new PageDescriptionObject();
    }
}
