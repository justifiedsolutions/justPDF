/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFInteger;

/**
 * Specifies the valid line cap styles in a PDF graphics state.
 *
 * @see "ISO 32000-1:2008, 8.4.3.3"
 */
public enum LineCapStyle {
    BUTT_CAP(new PDFInteger(0)),
    ROUND_CAP(new PDFInteger(1)),
    PROJECTING_SQUARE(new PDFInteger(2));

    private final PDFInteger style;

    LineCapStyle(PDFInteger style) {
        this.style = style;
    }

    /**
     * Gets the {@link PDFInteger} that represents the style.
     *
     * @return the style ID
     */
    public PDFInteger style() {
        return style;
    }
}
