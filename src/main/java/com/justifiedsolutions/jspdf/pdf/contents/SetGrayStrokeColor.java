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
 * Implements the PDF command <code>G</code> to set the stroke color in the monochrome color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.2"
 */
public class SetGrayStrokeColor implements ColorGraphicsOperator {
    private final DeviceGray colorSpace;

    /**
     * Creates a new operator that sets the stroke color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayStrokeColor(PDFReal gray) {
        this(new DeviceGray(gray));
    }

    /**
     * Creates a new operator that sets the stroke color in the monochrome color space in a content stream.
     *
     * @param colorSpace the color space
     */
    public SetGrayStrokeColor(DeviceGray colorSpace) {
        this.colorSpace = Objects.requireNonNull(colorSpace);
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
        colorSpace.getGray().writeToPDF(pdf);
        pdf.write(" G\n".getBytes(StandardCharsets.US_ASCII));
    }
}
