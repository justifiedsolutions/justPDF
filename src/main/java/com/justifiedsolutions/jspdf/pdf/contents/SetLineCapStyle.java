/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command {@code J} to set the line cap style in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.4"
 * @see "ISO 32000-1:2008, 8.4.3.3"
 */
public class SetLineCapStyle implements GeneralGraphicsOperator {
    private final LineCapStyle lineCapStyle;

    /**
     * Creates a new operator that sets the line cap style.
     *
     * @param lineCapStyle the new line cap style
     */
    public SetLineCapStyle(LineCapStyle lineCapStyle) {
        this.lineCapStyle = Objects.requireNonNull(lineCapStyle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineCapStyle);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetLineCapStyle that = (SetLineCapStyle) o;
        return lineCapStyle.equals(that.lineCapStyle);
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !lineCapStyle.equals(state.getLineCap());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setLineCap(lineCapStyle);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        lineCapStyle.style().writeToPDF(pdf);
        pdf.write(" J\n".getBytes(StandardCharsets.US_ASCII));
    }
}
