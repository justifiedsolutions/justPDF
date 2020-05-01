/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static com.justifiedsolutions.jspdf.pdf.contents.DeviceColorSpace.checkRange;

/**
 * Implements the PDF command <code>G</code> to set the stroke color in the monochrome color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.2"
 */
public class SetGrayStrokeColor implements ColorGraphicsOperator {
    private final PDFReal gray;
    private final ColorSpace colorSpace;

    /**
     * Creates a new operator that sets the stroke color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayStrokeColor(PDFReal gray) {
        this.gray = checkRange(gray);
        this.colorSpace = new DeviceGray(gray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetGrayStrokeColor that = (SetGrayStrokeColor) o;
        return gray.equals(that.gray);
    }

    @Override
    public boolean changesState(GraphicsState currentState) {
        return !colorSpace.equals(currentState.getStrokeColorSpace());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setStrokeColorSpace(colorSpace);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        gray.writeToPDF(pdf);
        pdf.write(" G\n".getBytes(StandardCharsets.US_ASCII));
    }
}