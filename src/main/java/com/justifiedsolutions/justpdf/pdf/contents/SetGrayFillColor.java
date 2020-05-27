/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

/**
 * Implements the PDF command {@code g} to set the fill color in the monochrome color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.2"
 */
public final class SetGrayFillColor extends DeviceGrayOperator {
    private static final String OPERATOR_CODE = "g";

    /**
     * Creates a new operator that sets the fill color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayFillColor(PDFReal gray) {
        super(new DeviceGray(gray));
    }

    /**
     * Creates a new operator that sets the fill color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayFillColor(DeviceGray gray) {
        super(gray);
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !getColorSpace().equals(state.getFillColorSpace());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setFillColorSpace(getColorSpace());
    }

    @Override
    protected String getOperatorCode() {
        return OPERATOR_CODE;
    }

}
