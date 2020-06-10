/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.contents.GraphicsOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FontWrapperOperatorTest {

    @Test
    public void equals() {
        PDFFontWrapper foo = PDFFontWrapper.getInstance(new PDFFont());
        PDFFontWrapper bar = PDFFontWrapper.getInstance(new PDFFont(PDFFont.FontName.COURIER));
        GraphicsOperator operator = foo.getOperator();
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        GraphicsOperator op1 = new FontWrapperOperator(foo);
        GraphicsOperator op2 = new FontWrapperOperator(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
        assertTrue(operator instanceof FontWrapperOperator);
        FontWrapperOperator op = (FontWrapperOperator) operator;
        assertSame(foo, op.getFontWrapper());
    }
}