/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF {@link GraphicsOperator} <code>re</code> to create a rectangular path in a PDF content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public class CreateRectangularPath implements PathConstructionGraphicsOperator {
    private final PDFRectangle rectangle;

    /**
     * Creates a new operator that creates a rectangular path in a content stream. The origin point is the lower left
     * point of the rectangle.
     *
     * @param rectangle the path to draw
     */
    public CreateRectangularPath(PDFRectangle rectangle) {
        this.rectangle = Objects.requireNonNull(rectangle);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        rectangle.getLLx().writeToPDF(pdf);
        pdf.write(' ');
        rectangle.getLLy().writeToPDF(pdf);
        pdf.write(' ');
        calculateWidth().writeToPDF(pdf);
        pdf.write(' ');
        calculateHeight().writeToPDF(pdf);
        pdf.write(" re\n".getBytes(StandardCharsets.US_ASCII));
    }

    private PDFReal calculateWidth() {
        float llx = rectangle.getLLx().getValue();
        float urx = rectangle.getURx().getValue();
        float width = Math.max(llx, urx) - Math.min(llx, urx);
        return new PDFReal(width);
    }

    private PDFReal calculateHeight() {
        float lly = rectangle.getLLy().getValue();
        float ury = rectangle.getURy().getValue();
        float height = Math.max(lly, ury) - Math.min(lly, ury);
        return new PDFReal(height);
    }
}
