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
 * Implements the PDF command <code>rg</code> to set the fill color in the RGB color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.3"
 */
public class SetRGBFillColor implements ColorGraphicsOperator {
    private final PDFReal red;
    private final PDFReal green;
    private final PDFReal blue;
    private final ColorSpace colorSpace;

    /**
     * Creates a new operator that sets the fill color in the RGB color space in a content stream.
     *
     * @param red   red
     * @param green green
     * @param blue  blue
     */
    public SetRGBFillColor(PDFReal red, PDFReal green, PDFReal blue) {
        this.red = checkRange(red);
        this.green = checkRange(green);
        this.blue = checkRange(blue);
        this.colorSpace = new DeviceRGB(red, green, blue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetRGBFillColor that = (SetRGBFillColor) o;
        return red.equals(that.red) &&
                green.equals(that.green) &&
                blue.equals(that.blue);
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
        red.writeToPDF(pdf);
        pdf.write(' ');
        green.writeToPDF(pdf);
        pdf.write(' ');
        blue.writeToPDF(pdf);
        pdf.write(" rg\n".getBytes(StandardCharsets.US_ASCII));
    }
}
