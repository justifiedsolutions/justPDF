/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.util.Objects;

/**
 * Superclass to all operators that work on {@link DeviceColorSpace}s.
 */
abstract class DeviceColorSpaceOperator implements ColorGraphicsOperator {
    private final DeviceColorSpace colorSpace;

    /**
     * Creates a new operator that sets the stroke color in the Gray color space in a content stream.
     *
     * @param colorSpace the color space
     */
    protected DeviceColorSpaceOperator(DeviceColorSpace colorSpace) {
        this.colorSpace = Objects.requireNonNull(colorSpace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorSpace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceColorSpaceOperator that = (DeviceColorSpaceOperator) o;
        return colorSpace.equals(that.colorSpace);
    }

    /**
     * Gets the {@link DeviceColorSpace} for the operator.
     *
     * @return the colorspace
     */
    protected DeviceColorSpace getDeviceColorSpace() {
        return colorSpace;
    }

    /**
     * Gets the operator code for writing to a PDF.
     *
     * @return the code
     */
    protected abstract String getOperatorCode();

}
