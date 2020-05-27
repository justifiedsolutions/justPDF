/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.contents.GraphicsOperator;

import java.io.OutputStream;
import java.util.Objects;

/**
 * A dummy {@link GraphicsOperator} that holds a font wrapper. It's intended to hold the place of a {@link
 * com.justifiedsolutions.justpdf.pdf.contents.SetFont} command until the proper font alias can be obtained in the
 * flow.
 */
class FontWrapperOperator implements GraphicsOperator {
    private final PDFFontWrapper fontWrapper;

    /**
     * Creates a new operator wrapping the specified {@link PDFFontWrapper}.
     *
     * @param fontWrapper the font wrapper
     */
    FontWrapperOperator(PDFFontWrapper fontWrapper) {
        this.fontWrapper = Objects.requireNonNull(fontWrapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fontWrapper);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FontWrapperOperator that = (FontWrapperOperator) o;
        return fontWrapper.equals(that.fontWrapper);
    }

    @Override
    public void writeToPDF(OutputStream pdf) {
        // no op
    }

    /**
     * Gets the font wrapper this operator was created with.
     *
     * @return the font wrapper
     */
    PDFFontWrapper getFontWrapper() {
        return fontWrapper;
    }
}
