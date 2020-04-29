/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.operator.text;

import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command <code>Tf</code> to set the font in a Text Object in a stream.
 *
 * @see "ISO 32000-1:2008, 9.2.2"
 */
public class SetFont implements TextOperator {
    private final PDFName font;
    private final PDFReal size;

    public SetFont(PDFName font, PDFReal size) {
        this.font = Objects.requireNonNull(font);
        this.size = Objects.requireNonNull(size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, size);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetFont setFont = (SetFont) o;
        return font.equals(setFont.font) &&
                size.equals(setFont.size);
    }

    @Override
    public boolean isCollapsable(TextOperator operator) {
        return (operator instanceof SetFont);
    }

    @Override
    public TextOperator collapse(TextOperator operator) {
        return operator;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        font.writeToPDF(pdf);
        pdf.write(' ');
        size.writeToPDF(pdf);
        pdf.write(" Tf\n".getBytes(StandardCharsets.US_ASCII));
    }
}
