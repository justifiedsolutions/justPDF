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
 * Represents a <code>string object</code> inside a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.4"
 */
public class PDFString implements PDFObject {

    private final String value;

    /**
     * Creates a new PDFString representing the specified value.
     *
     * @param value the String value
     */
    public PDFString(String value) {
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
        PDFString pdfString = (PDFString) o;
        return value.equals(pdfString.value);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('(');
        byte[] bytes = escapeValue().getBytes(StandardCharsets.UTF_16BE);
        if (bytes.length > 0) {
            pdf.write(bytes);
        }
        pdf.write(')');
    }

    protected String escapeValue() {
        StringBuilder result = new StringBuilder();
        for (char c : value.toCharArray()) {
            switch (c) {
                case '\n':
                    result.append("\\n");
                    break;
                case '\r':
                    result.append("\\r");
                    break;
                case '\t':
                    result.append("\\t");
                    break;
                case '\b':
                    result.append("\\b");
                    break;
                case '\f':
                    result.append("\\f");
                    break;
                case '(':
                case ')':
                case '\\':
                    result.append('\\').append(c);
                    break;
                default:
                    result.append(c);

            }
        }
        return result.toString();
    }

}
