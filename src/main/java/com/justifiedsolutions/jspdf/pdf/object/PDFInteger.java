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
 * Represents a <code>integer object</code> inside a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.3"
 */
public class PDFInteger implements PDFObject {

    private final int value;

    /**
     * Creates a PDFInteger representing the specified int value.
     *
     * @param value the int value
     */
    public PDFInteger(int value) {
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
        PDFInteger that = (PDFInteger) o;
        return value == that.value;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write(String.valueOf(value).getBytes(StandardCharsets.UTF_8));
    }
}
