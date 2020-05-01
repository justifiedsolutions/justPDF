/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.util.Objects;

/**
 * Defines a {@link ColorSpace} that specifies a CMYK device color space for a PDF content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.4"
 */
public class DeviceCMYK extends DeviceColorSpace {
    private final PDFReal cyan;
    private final PDFReal magenta;
    private final PDFReal yellow;
    private final PDFReal black;

    /**
     * Creates a new DeviceCMYK ColorSpace. All values range from <code>0.0</code> to <code>1.0</code>.
     *
     * @param cyan    cyan color
     * @param magenta magenta color
     * @param yellow  yellow color
     * @param black   black color
     */
    public DeviceCMYK(PDFReal cyan, PDFReal magenta, PDFReal yellow, PDFReal black) {
        this.cyan = checkRange(cyan);
        this.magenta = checkRange(magenta);
        this.yellow = checkRange(yellow);
        this.black = checkRange(black);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cyan, magenta, yellow, black);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceCMYK that = (DeviceCMYK) o;
        return cyan.equals(that.cyan) &&
                magenta.equals(that.magenta) &&
                yellow.equals(that.yellow) &&
                black.equals(that.black);
    }
}
