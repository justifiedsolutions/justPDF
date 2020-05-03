/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageDescriptionObjectTest {

    @Test
    void isValidOperator() {
        PageDescriptionObject graphicsObject = new PageDescriptionObject();
        assertTrue(graphicsObject.isValidOperator(new SetLineWidth(new PDFReal(0))));
        assertTrue(graphicsObject.isValidOperator(new PushGraphicsState()));
        assertTrue(graphicsObject.isValidOperator(new SetGrayStrokeColor(new PDFReal(0))));
        assertTrue(graphicsObject.isValidOperator(new SetLeading(new PDFReal(0))));
        assertTrue(graphicsObject.isValidOperator(new BeginText()));
        assertTrue(graphicsObject.isValidOperator(new StartPath(new PDFReal(0), new PDFReal(0))));
        assertTrue(graphicsObject.isValidOperator(new CreateRectangularPath(new PDFRectangle(0, 0, 10, 10))));

        assertFalse(graphicsObject.isValidOperator(new EndText()));
        assertFalse(graphicsObject.isValidOperator(new ClosePath()));
    }

    @Test
    void endsGraphicsObject() {
        PageDescriptionObject graphicsObject = new PageDescriptionObject();
        assertTrue(graphicsObject.endsGraphicsObject(new BeginText()));
        assertTrue(graphicsObject.endsGraphicsObject(new StartPath(new PDFReal(0), new PDFReal(0))));
        assertTrue(graphicsObject.endsGraphicsObject(new CreateRectangularPath(new PDFRectangle(0, 0, 10, 10))));

        assertFalse(graphicsObject.endsGraphicsObject(new EndText()));
    }

    @Test
    void getNewGraphicsObject() {
        PageDescriptionObject graphicsObject = new PageDescriptionObject();
        assertEquals(new TextObject(), graphicsObject.getNewGraphicsObject(new BeginText()));
        assertEquals(new PathObject(), graphicsObject.getNewGraphicsObject(new StartPath(new PDFReal(0), new PDFReal(0))));
        assertNull(graphicsObject.getNewGraphicsObject(new ClosePath()));
    }
}