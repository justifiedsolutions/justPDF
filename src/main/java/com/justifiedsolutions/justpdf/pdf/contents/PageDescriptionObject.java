/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.util.Objects;

/**
 * Specifies a graphics object which represents the Page Description state in the state diagram displayed in Figure 9 of
 * the PDF specification.
 *
 * @see "ISO 32000-1:2008, 8.2"
 * @see "ISO 32000-1:2008, Figure 9"
 */
class PageDescriptionObject implements GraphicsObject {
    @Override
    public int hashCode() {
        return Objects.hashCode("PageDescriptionObject");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PageDescriptionObject);
    }

    @Override
    public boolean isValidOperator(GraphicsOperator operator) {
        return ((operator instanceof GeneralGraphicsOperator)
                || (operator instanceof SpecialGraphicsOperator)
                || (operator instanceof ColorGraphicsOperator)
                || (operator instanceof TextStateOperator)
                || (operator instanceof BeginText)
                || (operator instanceof StartPath)
                || (operator instanceof CreateRectangularPath));
    }

    @Override
    public boolean endsGraphicsObject(GraphicsOperator operator) {
        return ((operator instanceof BeginText)
                || (operator instanceof StartPath) || (operator instanceof CreateRectangularPath));
    }

    @Override
    public GraphicsObject getNewGraphicsObject(GraphicsOperator operator) {
        if (operator instanceof BeginText) {
            return new TextObject();
        }

        if ((operator instanceof StartPath) || (operator instanceof CreateRectangularPath)) {
            return new PathObject();
        }

        return null;
    }
}
