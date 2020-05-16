/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Implements the PDF command {@code m} to start a new path in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public class StartPath implements PathConstructionGraphicsOperator, CollapsableOperator {
    private final PDFReal x;
    private final PDFReal y;

    /**
     * Creates a new path starting at the specified point.
     *
     * @param x the x value of the starting point
     * @param y the y value of the starting point
     */
    public StartPath(PDFReal x, PDFReal y) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StartPath startPath = (StartPath) o;
        return x.equals(startPath.x) &&
                y.equals(startPath.y);
    }

    @Override
    public boolean isCollapsable(GraphicsOperator operator) {
        return (operator instanceof StartPath);
    }

    @Override
    public GraphicsOperator collapse(GraphicsOperator operator) {
        return operator;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        x.writeToPDF(pdf);
        pdf.write(' ');
        y.writeToPDF(pdf);
        pdf.write(' ');
        pdf.write('m');
        pdf.write('\n');
    }
}
