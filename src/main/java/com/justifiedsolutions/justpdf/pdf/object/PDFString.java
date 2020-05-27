/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Represents a {@code string object} inside a PDF document.
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

    /**
     * Returns the value of the PDFString as a Java {@link String}.
     *
     * @return the string value
     */
    public String getValue() {
        return value;
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

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('(');
        for (char character : value.toCharArray()) {
            byte[] bytes = escapeValue(character, StandardCharsets.UTF_16BE);
            if (bytes.length > 0) {
                pdf.write(bytes);
            }
        }
        pdf.write(')');
    }

    /**
     * Escapes the specified character using PDF string escape rules. All unescaped characters will be encoded using the
     * specified {@link Charset}.
     *
     * @param c       the character to escape
     * @param charset the Charset to use on un-escaped chars
     * @return the escaped char
     */
    protected byte[] escapeValue(char c, Charset charset) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        switch (c) {
            case '\n':
                bytes.writeBytes("\\n".getBytes(StandardCharsets.US_ASCII));
                break;
            case '\r':
                bytes.writeBytes("\\r".getBytes(StandardCharsets.US_ASCII));
                break;
            case '\t':
                bytes.writeBytes("\\t".getBytes(StandardCharsets.US_ASCII));
                break;
            case '\b':
                bytes.writeBytes("\\b".getBytes(StandardCharsets.US_ASCII));
                break;
            case '\f':
                bytes.writeBytes("\\f".getBytes(StandardCharsets.US_ASCII));
                break;
            case '(':
            case ')':
            case '\\':
                String cString = "\\" + c;
                bytes.writeBytes(cString.getBytes(StandardCharsets.US_ASCII));
                break;
            default:
                bytes.writeBytes(String.valueOf(c).getBytes(charset));
                break;
        }
        return bytes.toByteArray();
    }

}
