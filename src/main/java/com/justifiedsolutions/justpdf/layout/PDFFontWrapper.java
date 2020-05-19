/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.contents.ColorSpace;
import com.justifiedsolutions.justpdf.pdf.contents.DeviceGray;
import com.justifiedsolutions.justpdf.pdf.contents.DeviceRGB;
import com.justifiedsolutions.justpdf.pdf.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.font.PDFFontType1;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.awt.*;
import java.util.Objects;

/**
 * Wrapper for a {@link PDFFont} that specifies not only the font, but the size and color as well.
 */
class PDFFontWrapper {
    private final PDFFont font;
    private final PDFReal size;
    private final ColorSpace color;

    private PDFFontWrapper(PDFFont font, PDFReal size, ColorSpace color) {
        this.font = Objects.requireNonNull(font);
        this.size = Objects.requireNonNull(size);
        this.color = Objects.requireNonNull(color);
    }

    /**
     * Gets an instance of a {@link PDFFontWrapper} given a specific API {@link com.justifiedsolutions.justpdf.api.font.Font}.
     *
     * @param apiFont the API Font
     * @return the font wrapper that represents the font
     */
    static PDFFontWrapper getInstance(com.justifiedsolutions.justpdf.api.font.Font apiFont) {
        if (apiFont instanceof com.justifiedsolutions.justpdf.api.font.PDFFont) {
            com.justifiedsolutions.justpdf.api.font.PDFFont apiPDFFont =
                    (com.justifiedsolutions.justpdf.api.font.PDFFont) apiFont;
            PDFFont font = getFont(apiPDFFont.getName());
            PDFReal size = new PDFReal(apiPDFFont.getSize());
            ColorSpace colorSpace = getColorSpace(apiPDFFont.getColor());
            return new PDFFontWrapper(font, size, colorSpace);
        }
        throw new IllegalArgumentException("Unsupported Font");
    }

    private static PDFFont getFont(com.justifiedsolutions.justpdf.api.font.PDFFont.FontName apiFontName) {
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

    /**
     * Gets the {@link PDFFont} being wrapped.
     *
     * @return the PDFFont
     */
    PDFFont getFont() {
        return font;
    }

    /**
     * Gets the font size.
     *
     * @return the font size
     */
    PDFReal getSize() {
        return size;
    }

    /**
     * Gets the font color.
     *
     * @return the font color
     */
    ColorSpace getColor() {
        return color;
    }

    /**
     * Gets the width of the specified character using this font and font size.
     *
     * @param character the character
     * @return the character width
     */
    float getCharacterWidth(int character) {
        return size.getValue() * (font.getCharacterWidth(character) / 1000f);
    }

    /**
     * Gets the minimum leading required for this font in this font size.
     *
     * @return the minimum leading
     */
    float getMinimumLeading() {
        return size.getValue() * (font.getMinimumLeading() / 1000f);
    }
}
