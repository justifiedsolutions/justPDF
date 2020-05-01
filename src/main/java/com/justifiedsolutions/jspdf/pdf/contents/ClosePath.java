/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command <code>h</code> to close an existing path in a content stream by connecting it to the
 * original point specified by {@link StartPath}.
 *
 * @see "ISO 32000-1:2008, 8.5.2.1"
 */
public class ClosePath implements PathConstructionGraphicsOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('h');
        pdf.write('\n');
    }
}
