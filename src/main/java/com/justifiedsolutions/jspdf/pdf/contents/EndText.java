/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command <code>ET</code> to end a text object in a content stream.
 *
 * @see "ISO 32000-1:2008, 9.4.1"
 */
public class EndText implements GraphicsOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('E');
        pdf.write('T');
        pdf.write('\n');
    }
}
