/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf;

import java.io.IOException;
import java.io.OutputStream;

/**
 * An interface for writing an object to a PDF document.
 */
public interface PDFWritable {

    /**
     * Allows an object to write itself to a PDF document. This method will take into account any special formatting,
     * escaping, delimiting, or conversion to the object that must occur to represent it in the document.
     *
     * @param pdf an {@link OutputStream} that represents the document or part of the document
     * @throws IOException if there is an issue writing the object to the OutputStream
     */
    void writeToPDF(OutputStream pdf) throws IOException;
}
