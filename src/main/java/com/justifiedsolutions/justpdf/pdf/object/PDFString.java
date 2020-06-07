/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a {@code string object} inside a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.4"
 */
public class PDFString implements PDFObject {

    /**
     * A list of characters that must be escaped when used in any kind of PDF string.
     */
    protected static final List<Character> ESCAPED_CHARACTERS = new ArrayList<>();

    static {
        ESCAPED_CHARACTERS.add('\n');
        ESCAPED_CHARACTERS.add('\r');
        ESCAPED_CHARACTERS.add('\t');
        ESCAPED_CHARACTERS.add('\b');
        ESCAPED_CHARACTERS.add('\f');
        ESCAPED_CHARACTERS.add('(');
        ESCAPED_CHARACTERS.add(')');
        ESCAPED_CHARACTERS.add('\\');
    }

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
        String text = "(" + escape(value) + ")";
        pdf.write(text.getBytes(StandardCharsets.UTF_16));
    }

    private String escape(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (ESCAPED_CHARACTERS.contains(c)) {
                result.append('\\');
            }
            result.append(c);
        }
        return result.toString();
    }

}
