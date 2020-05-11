/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.util.Objects;

/**
 * Defines a {@link ColorSpace} that specifies a RGB device color space for a PDF content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.3"
 */
public class DeviceRGB extends DeviceColorSpace {
    private final PDFReal red;
    private final PDFReal green;
    private final PDFReal blue;

    /**
     * Creates a new DeviceRGB ColorSpace. All values range from <code>0.0</code> to <code>1.0</code>.
     *
     * @param red   red color
     * @param green green color
     * @param blue  blue color
     */
    public DeviceRGB(PDFReal red, PDFReal green, PDFReal blue) {
        this.red = checkRange(red);
        this.green = checkRange(green);
        this.blue = checkRange(blue);
    }

    /**
     * Gets the red level.
     *
     * @return the red level
     */
    public PDFReal getRed() {
        return red;
    }

    /**
     * Gets the green level.
     *
     * @return the green level
     */
    public PDFReal getGreen() {
        return green;
    }

    /**
     * Gets the blue level.
     *
     * @return the blue level
     */
    public PDFReal getBlue() {
        return blue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceRGB deviceRGB = (DeviceRGB) o;
        return red.equals(deviceRGB.red) &&
                green.equals(deviceRGB.green) &&
                blue.equals(deviceRGB.blue);
    }
}
