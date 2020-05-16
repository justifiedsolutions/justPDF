/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.util.Objects;

/**
 * A TextObject encompasses a group of {@link TextOperator}s in a PDF stream.
 *
 * @see "ISO 32000-1:2008, 8.2"
 * @see "ISO 32000-1:2008, 9.4.1"
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
