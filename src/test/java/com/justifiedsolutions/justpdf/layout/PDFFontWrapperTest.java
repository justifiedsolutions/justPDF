/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.contents.ColorSpace;
import com.justifiedsolutions.justpdf.pdf.contents.DeviceGray;
import com.justifiedsolutions.justpdf.pdf.contents.DeviceRGB;
import com.justifiedsolutions.justpdf.pdf.contents.SetGrayFillColor;
import com.justifiedsolutions.justpdf.pdf.contents.SetRGBFillColor;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PDFFontWrapperTest {

    private static final Map<PDFFont.FontName, PDFName> FONT_MAP = new HashMap<>();

    static {
        FONT_MAP.put(PDFFont.FontName.COURIER, new PDFName("Courier"));
        FONT_MAP.put(PDFFont.FontName.COURIER_BOLD, new PDFName("Courier-Bold"));
        FONT_MAP.put(PDFFont.FontName.COURIER_OBLIQUE, new PDFName("Courier-Oblique"));
        FONT_MAP.put(PDFFont.FontName.COURIER_BOLD_OBLIQUE, new PDFName("Courier-BoldOblique"));
        FONT_MAP.put(PDFFont.FontName.HELVETICA, new PDFName("Helvetica"));
        FONT_MAP.put(PDFFont.FontName.HELVETICA_BOLD, new PDFName("Helvetica-Bold"));
        FONT_MAP.put(PDFFont.FontName.HELVETICA_OBLIQUE, new PDFName("Helvetica-Oblique"));
        FONT_MAP.put(PDFFont.FontName.HELVETICA_BOLD_OBLIQUE, new PDFName("Helvetica-BoldOblique"));
        FONT_MAP.put(PDFFont.FontName.SYMBOL, new PDFName("Symbol"));
        FONT_MAP.put(PDFFont.FontName.TIMES_ROMAN, new PDFName("Times-Roman"));
        FONT_MAP.put(PDFFont.FontName.TIMES_BOLD, new PDFName("Times-Bold"));
        FONT_MAP.put(PDFFont.FontName.TIMES_ITALIC, new PDFName("Times-Italic"));
        FONT_MAP.put(PDFFont.FontName.TIMES_BOLD_ITALIC, new PDFName("Times-BoldItalic"));
        FONT_MAP.put(PDFFont.FontName.ZAPFDINGBATS, new PDFName("ZapfDingbats"));
    }

    @Test
    void getInstanceDefault() {
        for (PDFFont.FontName font : FONT_MAP.keySet()) {
            PDFName fontName = FONT_MAP.get(font);
            PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(font));
            assertEquals(fontName, wrapper.getFont().get(new PDFName("BaseFont")), fontName.toString());
            assertEquals(new PDFReal(PDFFont.DEFAULT_SIZE), wrapper.getSize());
            assertEquals(new DeviceGray(new PDFReal(0)), wrapper.getColor());
        }
    }

    @Test
    void getInstanceCourierRed() {
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, PDFFont.DEFAULT_SIZE,
                Color.RED));
        PDFName fontName = new PDFName("Courier");
        assertEquals(fontName, wrapper.getFont().get(new PDFName("BaseFont")));
        assertEquals(new PDFReal(PDFFont.DEFAULT_SIZE), wrapper.getSize());
        ColorSpace expectedColor = new DeviceRGB(new PDFReal(1), new PDFReal(0), new PDFReal(0));
        assertEquals(expectedColor, wrapper.getColor());
    }

    @Test
    void getInstanceTimesGreen() {
        PDFFontWrapper wrapper = PDFFontWrapper
                .getInstance(new PDFFont(PDFFont.FontName.TIMES_ROMAN, PDFFont.DEFAULT_SIZE,
                        Color.GREEN));
        PDFName fontName = new PDFName("Times-Roman");
        assertEquals(fontName, wrapper.getFont().get(new PDFName("BaseFont")));
        assertEquals(new PDFReal(PDFFont.DEFAULT_SIZE), wrapper.getSize());
        ColorSpace expectedColor = new DeviceRGB(new PDFReal(0), new PDFReal(1), new PDFReal(0));
        assertEquals(expectedColor, wrapper.getColor());
    }

    @Test
    void getInstanceSymbolBlue() {
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.SYMBOL, PDFFont.DEFAULT_SIZE,
                Color.BLUE));
        PDFName fontName = new PDFName("Symbol");
        assertEquals(fontName, wrapper.getFont().get(new PDFName("BaseFont")));
        assertEquals(new PDFReal(PDFFont.DEFAULT_SIZE), wrapper.getSize());
        ColorSpace expectedColor = new DeviceRGB(new PDFReal(0), new PDFReal(0), new PDFReal(1));
        assertEquals(expectedColor, wrapper.getColor());
    }

    @Test
    void getCharacterWidth() {
        float size = 12f;
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, size));
        float expected = 7.2f;
        assertEquals(expected, wrapper.getCharacterWidth('c'), .00001);
    }

    @Test
    void getStringWidth() {
        float size = 12f;
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, size));
        float expected = 7.2f * 3f;
        assertEquals(expected, wrapper.getStringWidth("cat"), .00001);
    }

    @Test
    void getColorSpaceOperatorRGB() {
        PDFReal zero = new PDFReal(0);
        PDFReal one = new PDFReal(1);
        DeviceRGB blue = new DeviceRGB(zero, zero, one);
        SetRGBFillColor operator = new SetRGBFillColor(blue);
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, 12f, Color.BLUE));
        assertEquals(operator, wrapper.getColorSpaceOperator());
    }

    @Test
    void getColorSpaceOperatorGray() {
        SetGrayFillColor operator = new SetGrayFillColor(new DeviceGray(new PDFReal(0)));
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, 12f, Color.BLACK));
        assertEquals(operator, wrapper.getColorSpaceOperator());
    }

    @Test
    void getMinimumLeading() {
        float size = 12f;
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.HELVETICA, size));
        float expected = 8.616f;
        assertEquals(expected, wrapper.getMinimumLeading());
    }
}