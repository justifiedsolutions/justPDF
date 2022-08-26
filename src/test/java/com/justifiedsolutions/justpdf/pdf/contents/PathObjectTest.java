/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PathObjectTest {

    @Test
    void isValidOperator() {
        PathObject graphicsObject = new PathObject();
        assertFalse(graphicsObject.isValidOperator(new SetLineWidth(new PDFReal(0))));
        assertFalse(graphicsObject.isValidOperator(new PushGraphicsState()));
        assertFalse(graphicsObject.isValidOperator(new SetGrayStrokeColor(new DeviceGray(new PDFReal(0)))));
        assertFalse(graphicsObject.isValidOperator(new SetLeading(new PDFReal(0))));
        assertFalse(graphicsObject.isValidOperator(new BeginText()));
        assertTrue(graphicsObject.isValidOperator(new StartPath(new PDFReal(0), new PDFReal(0))));
        assertTrue(graphicsObject.isValidOperator(new CreateRectangularPath(new PDFRectangle(0, 0, 10, 10))));

        assertFalse(graphicsObject.isValidOperator(new EndText()));
        assertTrue(graphicsObject.isValidOperator(new ClosePath()));
    }

    @Test
    void endsGraphicsObject() {
        PathObject graphicsObject = new PathObject();
        assertTrue(graphicsObject.endsGraphicsObject(new FillPath()));
        assertTrue(graphicsObject.endsGraphicsObject(new StrokePath()));

        assertFalse(graphicsObject.endsGraphicsObject(new EndText()));
    }

    @Test
    void getNewGraphicsObject() {
        PathObject graphicsObject = new PathObject();
        assertEquals(new PageDescriptionObject(), graphicsObject.getNewGraphicsObject(new FillPath()));
    }
}