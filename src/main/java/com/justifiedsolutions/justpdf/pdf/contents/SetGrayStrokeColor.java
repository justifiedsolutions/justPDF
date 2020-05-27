/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

/**
 * Implements the PDF command {@code G} to set the stroke color in the monochrome color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.2"
 */
public final class SetGrayStrokeColor extends DeviceGrayOperator {
    private static final String OPERATOR_CODE = "G";

    /**
     * Creates a new operator that sets the stroke color in the monochrome color space in a content stream.
     *
     * @param gray gray
     */
    public SetGrayStrokeColor(PDFReal gray) {
        super(new DeviceGray(gray));
    }

    /**
     * Creates a new operator that sets the stroke color in the monochrome color space in a content stream.
     *
     * @param colorSpace the color space
     */
    public SetGrayStrokeColor(DeviceGray colorSpace) {
        super(colorSpace);
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !getColorSpace().equals(state.getStrokeColorSpace());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setStrokeColorSpace(getColorSpace());
    }

    @Override
    protected String getOperatorCode() {
        return OPERATOR_CODE;
    }
}
