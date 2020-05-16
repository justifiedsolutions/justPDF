/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.util.Objects;

class PathObject implements GraphicsObject {
    @Override
    public int hashCode() {
        return Objects.hashCode("PathObject");
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof PathObject);
    }

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
