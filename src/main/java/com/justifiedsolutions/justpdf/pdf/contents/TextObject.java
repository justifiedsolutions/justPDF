/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.util.Objects;

/**
 * Specifies a graphics object which represents the Text Object state in the state diagram displayed in Figure 9 of the
 * PDF specification.
 *
 * @see "ISO 32000-1:2008, 8.2"
 * @see "ISO 32000-1:2008, Figure 9"
 */
class TextObject implements GraphicsObject {
    @Override
    public int hashCode() {
        return Objects.hashCode("TextObject");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TextObject);
    }

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
