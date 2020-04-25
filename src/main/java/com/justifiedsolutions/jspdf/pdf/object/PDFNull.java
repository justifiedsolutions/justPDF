/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Represents a <code>null object</code> in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.9"
 */
public class PDFNull implements PDFObject {

    /**
     * The null object.
     */
    public static final PDFNull NULL = new PDFNull();

    private PDFNull() {
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write("null".getBytes(StandardCharsets.US_ASCII));
    }
}
