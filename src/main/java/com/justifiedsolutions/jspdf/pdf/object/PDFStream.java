/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Represents a <code>stream object</code> in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.8"
 */
public class PDFStream implements PDFObject {

    private final PDFDictionary dictionary = new PDFDictionary();
    private final byte[] data;

    /**
     * Creates a new PDFStream object that encompasses the specified data
     *
     * @param data the data for the stream
     */
    public PDFStream(byte[] data) {
        this.data = data;
        dictionary.put(new PDFName("Length"), new PDFInteger(data.length));
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        dictionary.writeToPDF(pdf);
        pdf.write("\nstream\n".getBytes(StandardCharsets.US_ASCII));
        pdf.write(data);
        pdf.write("\nendstream\n".getBytes(StandardCharsets.US_ASCII));
    }
}
