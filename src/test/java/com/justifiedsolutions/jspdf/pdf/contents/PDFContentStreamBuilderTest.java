/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PDFContentStreamBuilderTest {

    private PDFContentStreamBuilder builder;

    @BeforeEach
    public void setup() {
        builder = new PDFContentStreamBuilder();
    }

    @Test
    public void addOperatorNotValid() {
        assertThrows(IllegalStateException.class, () -> builder.addOperator(new EndText()));
    }

    @Test
    public void addOperatorEmptyPop() {
        assertThrows(IllegalStateException.class, () -> builder.addOperator(new PopGraphicsState()));
    }

    @Test
    public void addOperatorPush() {
        builder.addOperator(new PushGraphicsState());
        assertThrows(IllegalStateException.class, () -> builder.getStream());
    }

    @Test
    public void addOperatorPushPop() {
        builder.addOperator(new PushGraphicsState());
        builder.addOperator(new PopGraphicsState());
        assertThrows(IllegalStateException.class, () -> builder.getStream());
    }

    @Test
    public void addOperatorChangeState() throws IOException {
        builder.addOperator(new SetLeading(new PDFReal(14)));
        builder.addOperator(new SetLineWidth(new PDFReal(.5f)));
        builder.addOperator(new SetLeading(new PDFReal(14)));
        assertNotNull(builder.getStream());
    }
}