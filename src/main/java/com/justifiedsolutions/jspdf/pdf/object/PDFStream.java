/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a {@code stream object} in a PDF document.
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
        this.data = Objects.requireNonNull(data);
        dictionary.put(new PDFName("Length"), new PDFInteger(data.length));
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dictionary);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFStream pdfStream = (PDFStream) o;
        return dictionary.equals(pdfStream.dictionary) &&
                Arrays.equals(data, pdfStream.data);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        dictionary.writeToPDF(pdf);
        pdf.write("\nstream\n".getBytes(StandardCharsets.US_ASCII));
        pdf.write(data);
        pdf.write("\nendstream\n".getBytes(StandardCharsets.US_ASCII));
    }
}
