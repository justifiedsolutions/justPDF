/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Implements the PDF command {@code T*} to move to the start of the next line including leading.
 *
 * @see "ISO 32000-1:2008, 9.4.2"
 */
public final class MoveToNextLine implements TextPositioningOperator {
    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write("T*\n".getBytes(StandardCharsets.US_ASCII));
    }
}
