/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TextObjectTest {

    @Test
    public void isValidOperator() {
        TextObject graphicsObject = new TextObject();
        assertTrue(graphicsObject.isValidOperator(new SetLineWidth(new PDFReal(0))));
        assertFalse(graphicsObject.isValidOperator(new PushGraphicsState()));
        assertTrue(graphicsObject.isValidOperator(new SetGrayStrokeColor(new DeviceGray(new PDFReal(0)))));
        assertTrue(graphicsObject.isValidOperator(new SetLeading(new PDFReal(0))));
        assertFalse(graphicsObject.isValidOperator(new BeginText()));
        assertFalse(graphicsObject.isValidOperator(new StartPath(new PDFReal(0), new PDFReal(0))));
        assertFalse(graphicsObject.isValidOperator(new CreateRectangularPath(new PDFRectangle(0, 0, 10, 10))));

        assertTrue(graphicsObject.isValidOperator(new EndText()));
        assertFalse(graphicsObject.isValidOperator(new ClosePath()));
    }

    @Test
    public void endsGraphicsObject() {
        TextObject graphicsObject = new TextObject();
        assertFalse(graphicsObject.endsGraphicsObject(new BeginText()));
        assertFalse(graphicsObject.endsGraphicsObject(new StartPath(new PDFReal(0), new PDFReal(0))));
        assertFalse(graphicsObject.endsGraphicsObject(new CreateRectangularPath(new PDFRectangle(0, 0, 10, 10))));

        assertTrue(graphicsObject.endsGraphicsObject(new EndText()));
    }

    @Test
    public void getNewGraphicsObject() {
        TextObject graphicsObject = new TextObject();
        assertEquals(new PageDescriptionObject(), graphicsObject.getNewGraphicsObject(new EndText()));
    }
}