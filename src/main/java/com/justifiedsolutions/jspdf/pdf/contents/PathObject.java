/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

class PathObject implements GraphicsObject {
    @Override
    public boolean isValidOperator(GraphicsOperator operator) {
        return ((operator instanceof PathConstructionGraphicsOperator)
                || operator instanceof PathPaintingGraphicsOperator);
    }

    @Override
    public boolean endsGraphicsObject(GraphicsOperator operator) {
        return (operator instanceof PathPaintingGraphicsOperator);
    }

    @Override
    public GraphicsObject getNewGraphicsObject(GraphicsOperator operator) {
        return new PageDescriptionObject();
    }
}
