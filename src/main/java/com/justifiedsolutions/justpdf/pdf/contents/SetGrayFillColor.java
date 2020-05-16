/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command {@code g} to set the fill color in the monochrome color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.2"
 */
public class SetGrayFillColor implements ColorGraphicsOperator {
    private final DeviceGray colorSpace;

    /**
     * Creates a new operator that sets the fill color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayFillColor(PDFReal gray) {
        this(new DeviceGray(gray));
    }

    /**
     * Creates a new operator that sets the fill color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayFillColor(DeviceGray gray) {
        this.colorSpace = Objects.requireNonNull(gray);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorSpace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetGrayFillColor that = (SetGrayFillColor) o;
        return colorSpace.equals(that.colorSpace);
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !colorSpace.equals(state.getFillColorSpace());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setFillColorSpace(colorSpace);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        colorSpace.getGray().writeToPDF(pdf);
        pdf.write(" g\n".getBytes(StandardCharsets.US_ASCII));
    }
}
