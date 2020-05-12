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
 * Implements the PDF command {@code w} to set the line width in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.4"
 * @see "ISO 32000-1:2008, 8.4.3.2"
 */
public class SetLineWidth implements GeneralGraphicsOperator {
    private final PDFReal width;

    /**
     * Creates a new operator that sets the line width.
     *
     * @param width the new line width
     */
    public SetLineWidth(PDFReal width) {
        this.width = Objects.requireNonNull(width);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetLineWidth that = (SetLineWidth) o;
        return width.equals(that.width);
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !width.equals(state.getLineWidth());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setLineWidth(width);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        width.writeToPDF(pdf);
        pdf.write(" w\n".getBytes(StandardCharsets.US_ASCII));
    }
}
