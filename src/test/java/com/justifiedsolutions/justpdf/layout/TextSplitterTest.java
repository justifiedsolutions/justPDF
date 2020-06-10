/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextSplitterTest {

    @Test
    public void splitNoHyphenDoesNotFit() {
        float lineWidth = 79;
        String text = "hyphenation";
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, 12));
        TextSplitter splitter = new TextSplitter(lineWidth);
        String actual = splitter.split(text, wrapper, false);
        assertEquals("", actual);
    }

    @Test
    public void splitHyphen() {
        float lineWidth = 79;
        String text = "hyphenation";
        PDFFontWrapper wrapper = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER, 12));
        TextSplitter splitter = new TextSplitter(lineWidth);
        String actual = splitter.split(text, wrapper, true);
        assertEquals("hyphena-", actual);
    }
}