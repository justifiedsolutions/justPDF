/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.PDFWritable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Creates the file header for a PDF document. Specifies the document is compliant with version 1.7 of the PDF
 * specification.
 *
 * @see "ISO 32000-1:2008, 7.5.2"
 */
final class PDFHeader implements PDFWritable {

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write("%PDF-1.7\n".getBytes(StandardCharsets.US_ASCII));
        byte[] bytes = {'%', (byte) 226, (byte) 227, (byte) 207, (byte) 211, '\n'};
        pdf.write(bytes);
    }
}
