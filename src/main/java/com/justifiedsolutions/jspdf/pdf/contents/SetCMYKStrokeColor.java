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

/**
 * Implements the PDF command <code>K</code> to set the stroke color in the CMYK color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.4"
 */
public class SetCMYKStrokeColor implements ColorGraphicsOperator {
    private final DeviceCMYK colorSpace;

    /**
     * Creates a new operator that sets the stroke color in the CMYK color space in a content stream.
     *
     * @param cyan    cyan
     * @param magenta magenta
     * @param yellow  yellow
     * @param black   black
     */
    public SetCMYKStrokeColor(PDFReal cyan, PDFReal magenta, PDFReal yellow, PDFReal black) {
        this.colorSpace = new DeviceCMYK(cyan, magenta, yellow, black);
    }

    /**
     * Creates a new operator that sets the stroke color in the CMYK color space in a content stream.
     *
     * @param colorSpace the color space
     */
    public SetCMYKStrokeColor(DeviceCMYK colorSpace) {
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
        SetCMYKStrokeColor that = (SetCMYKStrokeColor) o;
        return colorSpace.equals(that.colorSpace);
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !colorSpace.equals(state.getStrokeColorSpace());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setStrokeColorSpace(colorSpace);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        colorSpace.getCyan().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getMagenta().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getYellow().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getBlack().writeToPDF(pdf);
        pdf.write(" K\n".getBytes(StandardCharsets.US_ASCII));
    }
}
