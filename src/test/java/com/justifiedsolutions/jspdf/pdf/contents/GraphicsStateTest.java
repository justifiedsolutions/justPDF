/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GraphicsStateTest {

    private GraphicsState state;

    @BeforeEach
    public void setup() {
        state = new GraphicsState();
    }

    @Test
    public void copyConstructor() {
        state.setLineWidth(new PDFReal(2));
        state.setLineCap(LineCapStyle.ROUND_CAP);
        state.setFillColorSpace(new DeviceRGB(new PDFReal(.5f), new PDFReal(.5f), new PDFReal(.5f)));
        state.setStrokeColorSpace(new DeviceRGB(new PDFReal(.6f), new PDFReal(.6f), new PDFReal(.6f)));
        state.setCharacterSpacing(new PDFReal(.3f));
        state.setWordSpacing(new PDFReal(.4f));
        state.setLeading(new PDFReal(1.2f));
        state.setTextFont(new PDFName("F2"));
        state.setTextFontSize(new PDFReal(12));

        assertEquals(state, new GraphicsState(state));
    }

    @Test
    public void lineWidth() {
        assertEquals(new PDFReal(1), state.getLineWidth());
        PDFReal value = new PDFReal(.5f);
        state.setLineWidth(value);
        assertEquals(value, state.getLineWidth());
    }

    @Test
    public void lineCap() {
        assertEquals(LineCapStyle.BUTT_CAP, state.getLineCap());
        state.setLineCap(LineCapStyle.ROUND_CAP);
        assertEquals(LineCapStyle.ROUND_CAP, state.getLineCap());
    }

    @Test
    public void fillColorSpace() {
        assertEquals(new DeviceGray(new PDFReal(0)), state.getFillColorSpace());
        ColorSpace cs = new DeviceRGB(new PDFReal(1), new PDFReal(1), new PDFReal(1));
        state.setFillColorSpace(cs);
        assertEquals(cs, state.getFillColorSpace());
    }

    @Test
    public void strokeColorSpace() {
        assertEquals(new DeviceGray(new PDFReal(0)), state.getStrokeColorSpace());
        ColorSpace cs = new DeviceRGB(new PDFReal(1), new PDFReal(1), new PDFReal(1));
        state.setStrokeColorSpace(cs);
        assertEquals(cs, state.getStrokeColorSpace());
    }

    @Test
    public void characterSpacing() {
        assertEquals(new PDFReal(0), state.getCharacterSpacing());
        PDFReal value = new PDFReal(.5f);
        state.setCharacterSpacing(value);
        assertEquals(value, state.getCharacterSpacing());
    }

    @Test
    public void wordSpacing() {
        assertEquals(new PDFReal(0), state.getWordSpacing());
        PDFReal value = new PDFReal(.5f);
        state.setWordSpacing(value);
        assertEquals(value, state.getWordSpacing());
    }

    @Test
    public void leading() {
        assertEquals(new PDFReal(0), state.getLeading());
        PDFReal value = new PDFReal(.5f);
        state.setLeading(value);
        assertEquals(value, state.getLeading());
    }

    @Test
    void font() {
        PDFName name = new PDFName("F3");
        PDFReal size = new PDFReal(12);
        state.setTextFont(name);
        state.setTextFontSize(size);
        assertEquals(name, state.getTextFont());
        assertEquals(size, state.getTextFontSize());
    }
}