/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF {@link GraphicsOperator} {@code re} to create a rectangular path in a PDF content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public final class CreateRectangularPath implements PathConstructionGraphicsOperator {
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
        rectangle.getWidth().writeToPDF(pdf);
        pdf.write(' ');
        rectangle.getHeight().writeToPDF(pdf);
        pdf.write(" re\n".getBytes(StandardCharsets.US_ASCII));
    }
}
