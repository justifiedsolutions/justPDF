/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.pdf.contents.ColorSpace;
import com.justifiedsolutions.jspdf.pdf.contents.DeviceGray;
import com.justifiedsolutions.jspdf.pdf.contents.DeviceRGB;
import com.justifiedsolutions.jspdf.pdf.font.PDFFont;
import com.justifiedsolutions.jspdf.pdf.font.PDFFontType1;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.awt.*;
import java.util.Objects;

class PDFFontWrapper {
    private final PDFFont font;
    private final PDFReal size;
    private final ColorSpace color;

    private PDFFontWrapper(PDFFont font, PDFReal size, ColorSpace color) {
        this.font = Objects.requireNonNull(font);
        this.size = Objects.requireNonNull(size);
        this.color = Objects.requireNonNull(color);
    }

    static PDFFontWrapper getInstance(com.justifiedsolutions.jspdf.api.font.Font apiFont) {
        if (apiFont instanceof com.justifiedsolutions.jspdf.api.font.PDFFont) {
            com.justifiedsolutions.jspdf.api.font.PDFFont apiPDFFont =
                    (com.justifiedsolutions.jspdf.api.font.PDFFont) apiFont;
            PDFFont font = getFont(apiPDFFont.getName());
            PDFReal size = new PDFReal(apiPDFFont.getSize());
            ColorSpace colorSpace = getColorSpace(apiPDFFont.getColor());
            return new PDFFontWrapper(font, size, colorSpace);
        }
        throw new IllegalArgumentException("Unsupported Font");
    }

    private static PDFFont getFont(com.justifiedsolutions.jspdf.api.font.PDFFont.FontName apiFontName) {
        PDFFontType1.FontName fontName = PDFFontType1.FontName.valueOf(apiFontName.name());
        return PDFFontType1.getInstance(fontName);
    }

    private static ColorSpace getColorSpace(Color color) {
        ColorSpace result;
        if ((color.getRed() == color.getGreen()) && (color.getRed() == color.getBlue())) {
            result = new DeviceGray(new PDFReal(color.getRed() / 255f));
        } else {
            result = new DeviceRGB(new PDFReal(color.getRed() / 255f), new PDFReal(color.getGreen() / 255f),
                    new PDFReal(color.getBlue() / 255f));
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(font, size, color);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFFontWrapper wrapper = (PDFFontWrapper) o;
        return font.equals(wrapper.font) &&
                size.equals(wrapper.size) &&
                color.equals(wrapper.color);
    }

    PDFFont getFont() {
        return font;
    }

    PDFReal getSize() {
        return size;
    }

    ColorSpace getColor() {
        return color;
    }

    float getCharacterWidth(int character) {
        return (size.getValue() * font.getCharacterWidth(character)) / 1000f;
    }

    float getMinimumLeading() {
        return (float) Math.ceil((size.getValue() * font.getMinimumLeading()) / 1000f);
    }
}
