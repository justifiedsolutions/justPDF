/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Represents a {@code real object} inside a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.3"
 */
public class PDFReal implements PDFObject {

    private final float value;

    /**
     * Creates a PDFReal representing the specified float value.
     *
     * @param value the float value
     */
    public PDFReal(float value) {
        this.value = value;
    }

    /**
     * Returns the value of the PDFReal as a Java {@code float}.
     *
     * @return the float value
     */
    public float getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFReal pdfReal = (PDFReal) o;
        return Float.compare(pdfReal.value, value) == 0;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        DecimalFormat df = new DecimalFormat("#");
        df.setMinimumFractionDigits(0);
        df.setMaximumFractionDigits(5);
        df.setMinimumIntegerDigits(0);
        df.setDecimalSeparatorAlwaysShown(false);
        df.setRoundingMode(RoundingMode.HALF_UP);

        String strValue = df.format(value);
        pdf.write(strValue.getBytes(StandardCharsets.US_ASCII));
    }
}
