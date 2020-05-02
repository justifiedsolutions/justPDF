/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command <code>BT</code> to begin a text object in a content stream.
 *
 * @see "ISO 32000-1:2008, 9.4.1"
 */
public class BeginText implements GraphicsOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('B');
        pdf.write('T');
        pdf.write('\n');
    }
}
