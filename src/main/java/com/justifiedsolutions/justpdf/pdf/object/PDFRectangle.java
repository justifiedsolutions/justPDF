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
public final class PDFRectangle implements PDFObject {
    private final PDFReal llx;
    private final PDFReal lly;
    private final PDFReal urx;
    private final PDFReal ury;

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

    /**
     * Gets the lower left x coordinate.
     *
     * @return llx
     */
    public PDFReal getLLx() {
        return llx;
    }

    /**
     * Gets the lower left y coordinate.
     *
     * @return lly
     */
    public PDFReal getLLy() {
        return lly;
    }

    /**
     * Gets the upper right x coordinate.
     *
     * @return urx
     */
    public PDFReal getURx() {
        return urx;
    }

    /**
     * Gets the upper right y coordinate.
     *
     * @return ury
     */
    public PDFReal getURy() {
        return ury;
    }

    /**
     * Gets the width of the rectangle.
     *
     * @return the width
     */
    public PDFReal getWidth() {
        float width = Math.max(llx.getValue(), urx.getValue()) - Math.min(llx.getValue(), urx.getValue());
        return new PDFReal(width);
    }

    /**
     * Gets the height of the rectangle.
     *
     * @return the height
     */
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
