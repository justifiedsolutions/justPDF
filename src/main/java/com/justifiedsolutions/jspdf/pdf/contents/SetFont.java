/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

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
public class SetFont implements TextStateOperator, CollapsableOperator {
    private final PDFName font;
    private final PDFReal size;

    /**
     * Creates a new operator instance that sets the current working font and font size for the {@link TextObject}.
     *
     * @param font the new font
     * @param size the new font size
     */
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
    public boolean isCollapsable(GraphicsOperator operator) {
        return (operator instanceof SetFont);
    }

    @Override
    public GraphicsOperator collapse(GraphicsOperator operator) {
        return operator;
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !(font.equals(state.getTextFont()) && size.equals(state.getTextFontSize()));
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setTextFont(font);
        state.setTextFontSize(size);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        font.writeToPDF(pdf);
        pdf.write(' ');
        size.writeToPDF(pdf);
        pdf.write(" Tf\n".getBytes(StandardCharsets.US_ASCII));
    }
}
