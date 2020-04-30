/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.operator.text;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command <code>Tc</code> to set the character spacing in a Text Object in a stream.
 *
 * @see "ISO 32000-1:2008, 9.3.1"
 * @see "ISO 32000-1:2008, 9.3.2"
 */
public class SetCharacterSpacing implements TextOperator {
    private final PDFReal charSpacing;

    /**
     * Creates a new operator that sets the current character spacing for the {@link TextObject}.
     *
     * @param charSpacing the new character spacing
     */
    public SetCharacterSpacing(PDFReal charSpacing) {
        this.charSpacing = Objects.requireNonNull(charSpacing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(charSpacing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetCharacterSpacing that = (SetCharacterSpacing) o;
        return charSpacing.equals(that.charSpacing);
    }

    @Override
    public boolean isCollapsable(TextOperator operator) {
        return (operator instanceof SetCharacterSpacing);
    }

    @Override
    public TextOperator collapse(TextOperator operator) {
        return operator;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        charSpacing.writeToPDF(pdf);
        pdf.write(" Tc\n".getBytes(StandardCharsets.US_ASCII));
    }
}
