/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.filter;

import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;

/**
 * Represents an {@code filter} for a content stream in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.4"
 */
public interface PDFFilter {

    /**
     * Gets the {@link PDFName} that represents the filter required to decode content encoded by this filter.
     *
     * @return the name of decode filter
     */
    PDFName getDecodeFilterName();

    /**
     * Gets the {@link PDFDictionary} of parameters for the decode filter
     *
     * @return the dictionary of parameters or {@code null} if no parameters are required
     */
    PDFDictionary getDecodeFilterParams();

    /**
     * Applies the filter to the input.
     *
     * @param input the input for the filter
     * @return the output of the filter
     */
    byte[] filter(byte[] input);
}
