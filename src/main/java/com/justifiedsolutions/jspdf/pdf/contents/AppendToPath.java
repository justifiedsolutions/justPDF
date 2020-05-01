/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Implements the PDF command <code>l</code> to append a straight line segment to the current path in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public class AppendToPath implements PathConstructionGraphicsOperator {
    private final PDFReal x;
    private final PDFReal y;

    /**
     * Appends a new straight line segment to the current path from the previous point to the specified point.
     *
     * @param x the x value of the next point
     * @param y the y value of the next point
     */
    public AppendToPath(PDFReal x, PDFReal y) {
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
        AppendToPath startPath = (AppendToPath) o;
        return x.equals(startPath.x) &&
                y.equals(startPath.y);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        x.writeToPDF(pdf);
        pdf.write(' ');
        y.writeToPDF(pdf);
        pdf.write(' ');
        pdf.write('l');
        pdf.write('\n');
    }
}
