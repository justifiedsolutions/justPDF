/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Implements the PDF command {@code Td} to move the text state to the specified point.
 *
 * @see "ISO 32000-1:2008, 9.4.2"
 */
public final class PositionText extends LocationOperator implements TextPositioningOperator, CollapsableOperator {

    /**
     * Creates a new operator instance that translates the text position to Tx, Ty for the {@link TextObject}.
     *
     * @param tx the distance on the x-axis from the last set text position
     * @param ty the distance on the y-axis from the last set text position
     */
    public PositionText(PDFReal tx, PDFReal ty) {
        super(tx, ty);
    }

    @Override
    public boolean isCollapsable(GraphicsOperator operator) {
        return (operator instanceof PositionText);
    }

    @Override
    public GraphicsOperator collapse(GraphicsOperator operator) {
        PositionText other = (PositionText) operator;
        float newTx = getX().getValue() + other.getX().getValue();
        float newTy = getY().getValue() + other.getY().getValue();
        return new PositionText(new PDFReal(newTx), new PDFReal(newTy));
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        getX().writeToPDF(pdf);
        pdf.write(' ');
        getY().writeToPDF(pdf);
        pdf.write(" Td\n".getBytes(StandardCharsets.US_ASCII));
    }
}
