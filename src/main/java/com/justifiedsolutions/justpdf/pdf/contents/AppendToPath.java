/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command {@code l} to append a straight line segment to the current path in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public final class AppendToPath extends LocationOperator implements PathConstructionGraphicsOperator {

    /**
     * Appends a new straight line segment to the current path from the previous point to the specified point.
     *
     * @param x the x value of the next point
     * @param y the y value of the next point
     */
    public AppendToPath(PDFReal x, PDFReal y) {
        super(x, y);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        getX().writeToPDF(pdf);
        pdf.write(' ');
        getY().writeToPDF(pdf);
        pdf.write(' ');
        pdf.write('l');
        pdf.write('\n');
    }
}
