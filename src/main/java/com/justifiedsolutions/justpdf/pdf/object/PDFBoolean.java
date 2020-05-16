/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Represents a {@code boolean object} in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.2"
 */
public class PDFBoolean implements PDFObject {

    /**
     * The PDFBoolean representing {@code true}.
     */
    public static final PDFBoolean TRUE = new PDFBoolean(true);

    /**
     * The PDFBoolean representing {@code false}.
     */
    public static final PDFBoolean FALSE = new PDFBoolean(false);

    private final boolean value;

    private PDFBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFBoolean that = (PDFBoolean) o;
        return value == that.value;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write(String.valueOf(value).getBytes(StandardCharsets.US_ASCII));
    }
}
