/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An interface that represents all <code>object</code> types in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3"
 */
public interface PDFObject {
    /**
     * Allows a PDFObject to write itself to a PDF document. This method will take into account any special formatting,
     * escaping, delimiting, or conversion to the object that must occur to represent it in the document.
     *
     * @param pdf an {@link OutputStream} that represents the document or part of the document
     * @throws IOException if there is an issue writing the object to the OutputStream
     */
    void writeToPDF(OutputStream pdf) throws IOException;
}
