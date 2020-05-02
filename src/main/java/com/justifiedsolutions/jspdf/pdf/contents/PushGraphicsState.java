/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command <code>q</code> to push the graphics state on to the stack in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.4.2"
 */
public class PushGraphicsState implements SpecialGraphicsOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('q');
        pdf.write('\n');
    }
}
