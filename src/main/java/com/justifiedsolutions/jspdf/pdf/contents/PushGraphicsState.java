/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command {@code q} to push the graphics state on to the stack in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.4.2"
 */
public class PushGraphicsState implements SpecialGraphicsOperator, CollapsableOperator {
    @Override
    public boolean isCollapsable(GraphicsOperator operator) {
        // this means it's a push followed immediately by a pop
        return (operator instanceof PopGraphicsState);
    }

    @Override
    public GraphicsOperator collapse(GraphicsOperator operator) {
        // this will remove the push from the stack and not add the pop
        return null;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('q');
        pdf.write('\n');
    }
}
