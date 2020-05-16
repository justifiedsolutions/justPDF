/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command {@code Q} to pop the graphics state off of the stack in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.4.2"
 */
public class PopGraphicsState implements SpecialGraphicsOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('Q');
        pdf.write('\n');
    }
}
