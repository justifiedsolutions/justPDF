/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.util.Objects;

/**
 * Abstract class that represents a {@link ColorSpace} for a device.
 *
 * @see "ISO 32000-1:2008, 8.6.4"
 */
public abstract class DeviceColorSpace implements ColorSpace {

    /**
     * Checks the specified color is within the range of 0 to 1.
     *
     * @param color the color to check
     * @return the color if in range
     * @throws NullPointerException     if the the color is null
     * @throws IllegalArgumentException if the color is not in range
     */
    protected PDFReal checkRange(PDFReal color) {
        Objects.requireNonNull(color);
        float value = color.getValue();
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Invalid Color Range.");
        }
        return color;
    }
}
