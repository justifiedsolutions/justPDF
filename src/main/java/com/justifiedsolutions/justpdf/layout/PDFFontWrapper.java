/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.pdf.contents.ColorSpace;
import com.justifiedsolutions.justpdf.pdf.contents.DeviceGray;
import com.justifiedsolutions.justpdf.pdf.contents.DeviceRGB;
import com.justifiedsolutions.justpdf.pdf.contents.GraphicsOperator;
import com.justifiedsolutions.justpdf.pdf.contents.SetGrayFillColor;
import com.justifiedsolutions.justpdf.pdf.contents.SetRGBFillColor;
import com.justifiedsolutions.justpdf.pdf.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.font.PDFFontType1;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Wrapper for a {@link PDFFont} that specifies not only the font, but the size
 * and color as well.
 */
final class PDFFontWrapper {
    private static final Map<com.justifiedsolutions.justpdf.api.font.PDFFont, PDFFontWrapper> CACHE = new HashMap<>();
    private final PDFFont font;
    private final PDFReal size;
    private final ColorSpace color;

    private final FontWrapperOperator operator = new FontWrapperOperator(this);

    private PDFFontWrapper(PDFFont font, PDFReal size, ColorSpace color) {
        this.font = Objects.requireNonNull(font);
        this.size = Objects.requireNonNull(size);
        this.color = Objects.requireNonNull(color);
    }

    /**
     * Gets an instance of a {@link PDFFontWrapper} given a specific API
     * {@link com.justifiedsolutions.justpdf.api.font.Font}.
     *
     * @param apiFont the API Font
     * @return the font wrapper that represents the font
     */
    static PDFFontWrapper getInstance(com.justifiedsolutions.justpdf.api.font.Font apiFont) {
        if (apiFont instanceof com.justifiedsolutions.justpdf.api.font.PDFFont) {
            com.justifiedsolutions.justpdf.api.font.PDFFont apiPDFFont = (com.justifiedsolutions.justpdf.api.font.PDFFont) apiFont;
            return CACHE.computeIfAbsent(apiPDFFont, k -> {
                PDFFont font = getFont(apiPDFFont.getName());
                PDFReal size = new PDFReal(apiPDFFont.getSize());
                ColorSpace colorSpace = getColorSpace(apiPDFFont.getColor());
                return new PDFFontWrapper(font, size, colorSpace);
            });
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
     * Gets the width of the specified string using this font and font size.
     *
     * @param string the string
     * @return the string width
     */
    float getStringWidth(String string) {
        float result = 0f;
        char[] chars = string.toCharArray();
        for (char c : chars) {
            result += getCharacterWidth(c);
        }
        return result;
    }

    /**
     * Gets the minimum leading required for this font in this font size.
     *
     * @return the minimum leading
     */
    float getMinimumLeading() {
        return size.getValue() * (font.getMinimumLeading() / 1000f);
    }

    /**
     * Gets the {@link GraphicsOperator} that represents this
     * {@code PDFFontWrapper}.
     *
     * @return the operator
     */
    GraphicsOperator getOperator() {
        return operator;
    }

    /**
     * Gets the operator for setting the {@link ColorSpace} for this font.
     *
     * @return the operator to set the color
     */
    GraphicsOperator getColorSpaceOperator() {
        ColorSpace colorSpace = getColor();
        if (colorSpace instanceof DeviceGray) {
            return new SetGrayFillColor((DeviceGray) colorSpace);
        } else if (colorSpace instanceof DeviceRGB) {
            return new SetRGBFillColor((DeviceRGB) colorSpace);
        }
        throw new IllegalArgumentException("Invalid ColorSpace");
    }

}
