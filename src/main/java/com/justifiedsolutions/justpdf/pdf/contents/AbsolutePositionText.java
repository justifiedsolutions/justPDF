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
 * Uses the PDF command {@code Tm} to move the text state to the specified point from the origin of the page.
 *
 * @see "ISO 32000-1:2008, 9.4.2"
 */
public final class AbsolutePositionText extends LocationOperator implements TextPositioningOperator, CollapsableOperator {

    /**
     * Creates a new operator instance that translates the text position to Tx, Ty for the {@link TextObject}.
     *
     * @param tx the distance on the x-axis from the origin
     * @param ty the distance on the y-axis from the origin
     */
    public AbsolutePositionText(PDFReal tx, PDFReal ty) {
        super(tx, ty);
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
        getX().writeToPDF(pdf);
        pdf.write(' ');
        getY().writeToPDF(pdf);
        pdf.write(" Tm\n".getBytes(StandardCharsets.US_ASCII));
    }
}
