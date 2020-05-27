/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

/**
 * Implements the PDF command {@code RG} to set the stroke color in the RGB color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.3"
 */
public final class SetRGBStrokeColor extends DeviceRGBOperator {
    private static final String OPERATOR_CODE = "RG";

    /**
     * Creates a new operator that sets the stroke color in the RGB color space in a content stream.
     *
     * @param red   red
     * @param green green
     * @param blue  blue
     */
    public SetRGBStrokeColor(PDFReal red, PDFReal green, PDFReal blue) {
        super(new DeviceRGB(red, green, blue));
    }

    /**
     * Creates a new operator that sets the stroke color in the RGB color space in a content stream.
     *
     * @param colorSpace the color space
     */
    public SetRGBStrokeColor(DeviceRGB colorSpace) {
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
