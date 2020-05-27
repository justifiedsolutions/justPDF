/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void font() {
        PDFName name = new PDFName("F3");
        PDFReal size = new PDFReal(12);
        state.setTextFont(name);
        state.setTextFontSize(size);
        assertEquals(name, state.getTextFont());
        assertEquals(size, state.getTextFontSize());
    }

    @Test
    public void equals() {
        PDFReal one = new PDFReal(1);
        GraphicsState gs = new GraphicsState();
        assertTrue(gs.equals(gs));
        assertFalse(gs.equals(null));
        assertFalse(gs.equals(Boolean.TRUE));

        GraphicsState gs1 = new GraphicsState();
        assertTrue(gs.equals(gs1));
        assertEquals(gs.hashCode(), gs1.hashCode());

        GraphicsState gs2 = new GraphicsState();
        gs2.setCharacterSpacing(one);
        assertFalse(gs.equals(gs2));

        GraphicsState gs3 = new GraphicsState();
        gs3.setWordSpacing(one);
        assertFalse(gs.equals(gs3));

        GraphicsState gs4 = new GraphicsState();
        gs4.setLeading(one);
        assertFalse(gs.equals(gs4));

        GraphicsState gs5 = new GraphicsState();
        gs5.setLineWidth(new PDFReal(.5f));
        assertFalse(gs.equals(gs5));

        GraphicsState gs6 = new GraphicsState();
        gs6.setFillColorSpace(new DeviceGray(one));
        assertFalse(gs.equals(gs6));

        GraphicsState gs7 = new GraphicsState();
        gs7.setStrokeColorSpace(new DeviceGray(one));
        assertFalse(gs.equals(gs7));

        GraphicsState gs8 = new GraphicsState();
        gs8.setLineCap(LineCapStyle.ROUND_CAP);
        assertFalse(gs.equals(gs8));

        GraphicsState gs9 = new GraphicsState();
        gs9.setTextFont(new PDFName("foo"));
        assertFalse(gs.equals(gs9));

        GraphicsState gs0 = new GraphicsState();
        gs0.setTextFontSize(one);
        assertFalse(gs.equals(gs0));
    }
}