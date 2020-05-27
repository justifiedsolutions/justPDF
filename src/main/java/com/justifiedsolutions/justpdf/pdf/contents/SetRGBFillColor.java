/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

/**
 * Implements the PDF command {@code rg} to set the fill color in the RGB color space in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.6.4.3"
 */
public final class SetRGBFillColor extends DeviceRGBOperator {
    private static final String OPERATOR_CODE = "rg";

    /**
     * Creates a new operator that sets the fill color in the RGB color space in a content stream.
     *
     * @param red   red
     * @param green green
     * @param blue  blue
     */
    public SetRGBFillColor(PDFReal red, PDFReal green, PDFReal blue) {
        super(new DeviceRGB(red, green, blue));
    }

    /**
     * Creates a new operator that sets the fill color in the RGB color space in a content stream.
     *
     * @param colorSpace the color space
     */
    public SetRGBFillColor(DeviceRGB colorSpace) {
        super(colorSpace);
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
