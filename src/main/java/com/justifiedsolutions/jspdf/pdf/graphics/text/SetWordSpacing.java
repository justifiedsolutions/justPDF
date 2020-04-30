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
 * Implements the PDF command <code>Tw</code> to set the word spacing in a Text Object in a stream.
 *
 * @see "ISO 32000-1:2008, 9.3.1"
 * @see "ISO 32000-1:2008, 9.3.3"
 */
public class SetWordSpacing implements TextOperator {
    private final PDFReal wordSpacing;

    /**
     * Creates a new operator that sets the current word spacing for the {@link TextObject}.
     *
     * @param wordSpacing the new word spacing
     */
    public SetWordSpacing(PDFReal wordSpacing) {
        this.wordSpacing = Objects.requireNonNull(wordSpacing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordSpacing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetWordSpacing that = (SetWordSpacing) o;
        return wordSpacing.equals(that.wordSpacing);
    }

    @Override
    public boolean isCollapsable(TextOperator operator) {
        return (operator instanceof SetWordSpacing);
    }

    @Override
    public TextOperator collapse(TextOperator operator) {
        return operator;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        wordSpacing.writeToPDF(pdf);
        pdf.write(" Tw\n".getBytes(StandardCharsets.US_ASCII));
    }
}
