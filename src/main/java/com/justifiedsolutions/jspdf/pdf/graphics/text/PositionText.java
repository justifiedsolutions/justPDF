/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.graphics.text;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command <code>Td</code> to move the text state to the specified point.
 *
 * @see "ISO 32000-1:2008, 9.4.2"
 */
public class PositionText implements TextOperator {
    private final PDFReal tx;
    private final PDFReal ty;

    /**
     * Creates a new operator instance that translates the text position to Tx, Ty for the {@link TextObject}.
     *
     * @param tx the distance on the x-axis from the last set text position
     * @param ty the distance on the y-axis from the last set text position
     */
    public PositionText(PDFReal tx, PDFReal ty) {
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
        PositionText that = (PositionText) o;
        return tx.equals(that.tx) &&
                ty.equals(that.ty);
    }

    @Override
    public boolean isCollapsable(TextOperator operator) {
        return (operator instanceof PositionText);
    }

    @Override
    public TextOperator collapse(TextOperator operator) {
        PositionText other = (PositionText) operator;
        float newTx = tx.getValue() + other.tx.getValue();
        float newTy = ty.getValue() + other.ty.getValue();
        return new PositionText(new PDFReal(newTx), new PDFReal(newTy));
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        tx.writeToPDF(pdf);
        pdf.write(' ');
        ty.writeToPDF(pdf);
        pdf.write(" Td\n".getBytes(StandardCharsets.US_ASCII));
    }
}
