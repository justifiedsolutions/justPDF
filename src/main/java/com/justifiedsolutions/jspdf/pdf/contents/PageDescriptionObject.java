/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

class PageDescriptionObject implements GraphicsObject {
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
