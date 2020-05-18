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
 * Implements the PDF command {@code rg} to set the fill color in the RGB color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.3"
 */
public class SetRGBFillColor implements ColorGraphicsOperator {
    private final DeviceRGB colorSpace;

    /**
     * Creates a new operator that sets the fill color in the RGB color space in a content stream.
     *
     * @param red   red
     * @param green green
     * @param blue  blue
     */
    public SetRGBFillColor(PDFReal red, PDFReal green, PDFReal blue) {
        this(new DeviceRGB(red, green, blue));
    }

    /**
     * Creates a new operator that sets the fill color in the RGB color space in a content stream.
     *
     * @param colorSpace the color space
     */
    public SetRGBFillColor(DeviceRGB colorSpace) {
        this.colorSpace = Objects.requireNonNull(colorSpace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorSpace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetRGBFillColor that = (SetRGBFillColor) o;
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
        colorSpace.getRed().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getGreen().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getBlue().writeToPDF(pdf);
        pdf.write(" rg\n".getBytes(StandardCharsets.US_ASCII));
    }
}