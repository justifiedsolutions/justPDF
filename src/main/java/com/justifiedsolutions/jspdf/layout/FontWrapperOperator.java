/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.pdf.contents.GraphicsOperator;

import java.io.OutputStream;
import java.util.Objects;

class FontWrapperOperator implements GraphicsOperator {
    private final PDFFontWrapper fontWrapper;

    FontWrapperOperator(PDFFontWrapper fontWrapper) {
        this.fontWrapper = Objects.requireNonNull(fontWrapper);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fontWrapper);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FontWrapperOperator that = (FontWrapperOperator) o;
        return fontWrapper.equals(that.fontWrapper);
    }

    @Override
    public void writeToPDF(OutputStream pdf) {
        // no op
    }

    PDFFontWrapper getFontWrapper() {
        return fontWrapper;
    }
}
