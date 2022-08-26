/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SetCharacterSpacingTest {

    private final PDFReal spacing = new PDFReal(12);
    private SetCharacterSpacing operator;
    private GraphicsState graphicsState;

    @BeforeEach
    void setup() {
        operator = new SetCharacterSpacing(spacing);
        graphicsState = new GraphicsState();
    }

    @Test
    void isCollapsableFalse() {
        TextOperator other = new MoveToNextLine();
        assertFalse(operator.isCollapsable(other));
    }

    @Test
    void isCollapsableTrue() {
        SetCharacterSpacing other = new SetCharacterSpacing(new PDFReal(14));
        assertTrue(operator.isCollapsable(other));
    }

    @Test
    void collapse() {
        SetCharacterSpacing other = new SetCharacterSpacing(new PDFReal(14));
        GraphicsOperator actual = operator.collapse(other);
        assertEquals(other, actual);
    }

    @Test
    void changesStateFalse() {
        graphicsState.setCharacterSpacing(spacing);
        assertFalse(operator.changesState(graphicsState));
    }

    @Test
    void changesStateTrue() {
        assertTrue(operator.changesState(graphicsState));
    }

    @Test
    void changeState() {
        operator.changeState(graphicsState);
        assertEquals(spacing, graphicsState.getCharacterSpacing());
    }

    @Test
    void writeToPDF() throws IOException {
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);
        assertArrayEquals("12 Tc\n".getBytes(StandardCharsets.US_ASCII), actual.toByteArray());
    }

    @Test
    @SuppressWarnings({ "unlikely-arg-type", "PMD.SimplifiableTestAssertion" })
    void equals() {
        PDFReal foo = new PDFReal(1);
        PDFReal bar = new PDFReal(2);
        SetCharacterSpacing operator = new SetCharacterSpacing(foo);
        assertTrue(operator.equals(operator));
        assertFalse(operator.equals(null));
        assertFalse(operator.equals(Boolean.TRUE));
        SetCharacterSpacing op1 = new SetCharacterSpacing(foo);
        SetCharacterSpacing op2 = new SetCharacterSpacing(bar);
        assertTrue(operator.equals(op1));
        assertEquals(operator.hashCode(), op1.hashCode());
        assertFalse(operator.equals(op2));
    }
}