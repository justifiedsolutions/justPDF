/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PDFContentStreamBuilderTest {

    private PDFContentStreamBuilder builder;

    @BeforeEach
    void setup() {
        builder = new PDFContentStreamBuilder();
    }

    @Test
    void addOperatorNotValid() {
        GraphicsOperator operator = new EndText();
        assertThrows(IllegalStateException.class, () -> builder.addOperator(operator));
    }

    @Test
    void addOperatorEmptyPop() {
        GraphicsOperator operator = new PopGraphicsState();
        assertThrows(IllegalStateException.class, () -> builder.addOperator(operator));
    }

    @Test
    void addOperatorPush() {
        builder.addOperator(new PushGraphicsState());
        assertThrows(IllegalStateException.class, () -> builder.getStream());
    }

    @Test
    void addOperatorPushPop() {
        builder.addOperator(new PushGraphicsState());
        builder.addOperator(new PopGraphicsState());
        assertThrows(IllegalStateException.class, () -> builder.getStream());
    }

    @Test
    void addOperatorChangeState() throws IOException {
        builder.addOperator(new SetLeading(new PDFReal(14)));
        builder.addOperator(new SetLineWidth(new PDFReal(.5f)));
        builder.addOperator(new SetLeading(new PDFReal(14)));
        assertNotNull(builder.getStream());
    }
}