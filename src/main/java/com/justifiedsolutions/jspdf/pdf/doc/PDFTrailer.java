/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.jspdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.jspdf.pdf.object.PDFInteger;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Models a trailer in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.5.5"
 */
class PDFTrailer {

    private static final PDFName ROOT = new PDFName("Root");
    private static final PDFName INFO = new PDFName("Info");
    private static final PDFName SIZE = new PDFName("Size");

    private final PDFDictionary dictionary = new PDFDictionary();
    private PDFInteger totalBytes;

    /**
     * Sets the number of indirect objects in the file.
     *
     * @param size the number of indirect objects
     */
    void setSize(PDFInteger size) {
        dictionary.put(SIZE, size);
    }

    /**
     * Sets the reference to the PDF Catalog.
     *
     * @param root the reference to the catalog
     */
    void setRoot(PDFIndirectObject.Reference root) {
        dictionary.put(ROOT, root);
    }

    /**
     * Sets the reference to the Information Dictionary.
     *
     * @param info the reference to the info dictionary
     */
    void setInfo(PDFIndirectObject.Reference info) {
        dictionary.put(INFO, info);
    }

    /**
     * Specifies if the PDFTrailer has an {@code Info} entry.
     *
     * @return true if Info is in the dictionary
     */
    boolean hasInfo() {
        return dictionary.containsKey(INFO);
    }

    /**
     * Sets the total number of bytes in the PDF prior to the Cross Reference Table.
     *
     * @param totalBytes the total number of bytes
     */
    void setTotalBytes(PDFInteger totalBytes) {
        this.totalBytes = totalBytes;
    }

    /**
     * Writes the PDF syntax to the specified {@link OutputStream}
     *
     * @param pdf the OutputStream
     * @throws IOException if there was an issue writing to the stream
     */
    void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write("trailer\n".getBytes(StandardCharsets.US_ASCII));
        dictionary.writeToPDF(pdf);
        pdf.write("\nstartxref\n".getBytes(StandardCharsets.US_ASCII));
        totalBytes.writeToPDF(pdf);
        pdf.write("\n%%EOF".getBytes(StandardCharsets.US_ASCII));
    }
}
