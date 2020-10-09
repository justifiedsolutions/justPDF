/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Represents a {@code string object} inside a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.4"
 */
public class PDFString implements PDFObject {

    private static final Encoding WIN_ANSI_ENCODING = new WinAnsiEncoding();

    private final String value;

    /**
     * Creates a new PDFString representing the specified value.
     *
     * @param value the String value
     */
    public PDFString(String value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns the value of the PDFString as a Java {@link String}.
     *
     * @return the string value
     */
    public String getValue() {
        return value;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('(');
        pdf.write(encode());
        pdf.write(')');
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PDFString pdfString = (PDFString) o;
        return value.equals(pdfString.value);
    }

    /**
     * Encodes the value into a byte array.
     *
     * @return the encoded value
     */
    protected byte[] encode() {
        return WIN_ANSI_ENCODING.encodeString(getValue());
    }
}
