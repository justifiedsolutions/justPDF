/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.util.Objects;

/**
 * Defines a {@link ColorSpace} that specifies a monochrome device color space for a PDF content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.2"
 */
public class DeviceGray extends DeviceColorSpace {
    private final PDFReal gray;

    /**
     * Creates a new DeviceGray ColorSpace. All values range from {@code 0.0} to {@code 1.0}.
     *
     * @param gray gray value
     */
    public DeviceGray(PDFReal gray) {
        this.gray = checkRange(gray);
    }

    /**
     * Gets the gray level.
     *
     * @return the gray level
     */
    public PDFReal getGray() {
        return gray;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gray);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceGray that = (DeviceGray) o;
        return gray.equals(that.gray);
    }
}
