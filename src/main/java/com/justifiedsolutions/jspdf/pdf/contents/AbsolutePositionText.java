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
 * Uses the PDF command <code>Tm</code> to move the text state to the specified point from the origin of the page.
 *
 * @see "ISO 32000-1:2008, 9.4.2"
 */
public class AbsolutePositionText implements TextPositioningOperator, CollapsableOperator {
    private final PDFReal tx;
    private final PDFReal ty;

    /**
     * Creates a new operator instance that translates the text position to Tx, Ty for the {@link TextObject}.
     *
     * @param tx the distance on the x-axis from the origin
     * @param ty the distance on the y-axis from the origin
     */
    public AbsolutePositionText(PDFReal tx, PDFReal ty) {
        this.tx = Objects.requireNonNull(tx);
        this.ty = Objects.requireNonNull(ty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tx, ty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsolutePositionText that = (AbsolutePositionText) o;
        return tx.equals(that.tx) &&
                ty.equals(that.ty);
    }

    @Override
    public boolean isCollapsable(GraphicsOperator operator) {
        return (operator instanceof AbsolutePositionText);
    }

    @Override
    public GraphicsOperator collapse(GraphicsOperator operator) {
        return operator;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write("1 0 0 1 ".getBytes(StandardCharsets.US_ASCII));
        tx.writeToPDF(pdf);
        pdf.write(' ');
        ty.writeToPDF(pdf);
        pdf.write(" Tm\n".getBytes(StandardCharsets.US_ASCII));
    }
}
