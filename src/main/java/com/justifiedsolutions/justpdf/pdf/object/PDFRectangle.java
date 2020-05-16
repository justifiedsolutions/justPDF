/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Represents a {@code rectangle object} in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.9.5"
 */
public class PDFRectangle implements PDFObject {
    private final PDFReal llx, lly, urx, ury;

    /**
     * Creates a rectangle given the x,y coordinates for the lower left and upper right corners of the rectangle.
     *
     * @param llx lower left x
     * @param lly lower left y
     * @param urx upper right x
     * @param ury upper right y
     */
    public PDFRectangle(PDFReal llx, PDFReal lly, PDFReal urx, PDFReal ury) {
        this.llx = Objects.requireNonNull(llx);
        this.lly = Objects.requireNonNull(lly);
        this.urx = Objects.requireNonNull(urx);
        this.ury = Objects.requireNonNull(ury);
    }

    /**
     * Creates a rectangle given the x,y coordinates for the lower left and upper right corners of the rectangle.
     *
     * @param llx lower left x
     * @param lly lower left y
     * @param urx upper right x
     * @param ury upper right y
     */
    public PDFRectangle(float llx, float lly, float urx, float ury) {
        this.llx = new PDFReal(llx);
        this.lly = new PDFReal(lly);
        this.urx = new PDFReal(urx);
        this.ury = new PDFReal(ury);
    }

    public PDFReal getLLx() {
        return llx;
    }

    public PDFReal getLLy() {
        return lly;
    }

    public PDFReal getURx() {
        return urx;
    }

    public PDFReal getURy() {
        return ury;
    }

    public PDFReal getWidth() {
        float width = Math.max(llx.getValue(), urx.getValue()) - Math.min(llx.getValue(), urx.getValue());
        return new PDFReal(width);
    }

    public PDFReal getHeight() {
        float height = Math.max(lly.getValue(), ury.getValue()) - Math.min(lly.getValue(), ury.getValue());
        return new PDFReal(height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(llx, lly, urx, ury);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFRectangle that = (PDFRectangle) o;
        return llx.equals(that.llx) &&
                lly.equals(that.lly) &&
                urx.equals(that.urx) &&
                ury.equals(that.ury);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        PDFArray array = new PDFArray();
        array.add(llx);
        array.add(lly);
        array.add(urx);
        array.add(ury);
        array.writeToPDF(pdf);
    }
}
