/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFString;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command <code>Tj</code> to write a text string in a Text Object in a stream.
 *
 * @see "ISO 32000-1:2008, 9.4.3"
 */
public class ShowText implements TextShowingOperator {
    private final PDFString text;

    /**
     * Creates a new instance of an operator that shows the specified text in the {@link TextObject}.
     *
     * @param text the text to show
     */
    public ShowText(PDFString text) {
        this.text = Objects.requireNonNull(text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShowText showText = (ShowText) o;
        return text.equals(showText.text);
    }

    @Override
    public boolean isCollapsable(TextOperator operator) {
        return (operator instanceof ShowText);
    }

    @Override
    public TextOperator collapse(TextOperator operator) {
        ShowText other = (ShowText) operator;
        String newValue = text.getValue() + other.text.getValue();
        PDFString newText = new PDFString(newValue);
        return new ShowText(newText);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        text.writeToPDF(pdf);
        pdf.write("Tj\n".getBytes(StandardCharsets.US_ASCII));
    }
}
