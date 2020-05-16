/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Implements the PDF command {@code f} to fill a path in a content stream.
 *
 * @see "ISO 32000-1:2008, 8.5.3.1"
 * @see "ISO 32000-1:2008, 8.5.3.3"
 */
public class FillPath implements PathPaintingGraphicsOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('f');
        pdf.write('\n');
    }
}
