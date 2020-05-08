/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Represents a <code>name object</code> in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.5"
 */
public class PDFName implements PDFObject, Comparable<PDFName> {

    private final String value;

    /**
     * Creates a new PDFName representing the specified name.
     *
     * @param value the specified name
     */
    public PDFName(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFName pdfName = (PDFName) o;
        return value.equals(pdfName.value);
    }

    @Override
    public String toString() {
        return "PDFName{value='" + value + "'}";
    }

    @Override
    public int compareTo(PDFName o) {
        return value.compareTo(o.value);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('/');
        for (char c : value.toCharArray()) {
            String encoded = encodeChar(c);
            pdf.write(encoded.getBytes(StandardCharsets.US_ASCII));
        }
    }

    private String encodeChar(char c) {
        String result = String.valueOf(c);
        if (c < 33 || c > 126 || c == 35) {
            result = "#" + Integer.toHexString(c);
        }
        return result;
    }
}
