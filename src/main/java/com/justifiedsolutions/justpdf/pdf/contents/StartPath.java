/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command {@code m} to start a new path in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public final class StartPath extends LocationOperator implements PathConstructionGraphicsOperator, CollapsableOperator {

    /**
     * Creates a new path starting at the specified point.
     *
     * @param x the x value of the starting point
     * @param y the y value of the starting point
     */
    public StartPath(PDFReal x, PDFReal y) {
        super(x, y);
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
        getX().writeToPDF(pdf);
        pdf.write(' ');
        getY().writeToPDF(pdf);
        pdf.write(' ');
        pdf.write('m');
        pdf.write('\n');
    }
}
