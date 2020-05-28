/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

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
public final class PDFStream implements PDFObject {

    private static final PDFName FILTER = new PDFName("Filter");
    private static final PDFName DECODE_PARMS = new PDFName("DecodeParms");
    private final PDFDictionary dictionary = new PDFDictionary();
    private final byte[] data;

    /**
     * Creates a new PDFStream object that encompasses the specified data
     *
     * @param data the data for the stream
     */
    public PDFStream(byte[] data) {
        Objects.requireNonNull(data);
        this.data = Arrays.copyOf(data, data.length);
        dictionary.put(new PDFName("Length"), new PDFInteger(data.length));
    }

    /**
     * Adds a {@code Filter} entry to the stream dictionary.
     *
     * @param filters the {@link PDFArray} of filters
     */
    public void addFilter(PDFArray filters) {
        if (filters != null && !filters.isEmpty()) {
            dictionary.put(FILTER, filters);
        }
    }

    /**
     * Adds the {@code DecodeParams} to the stream dictionary. The argument is a {@link PDFArray} of {@link
     * PDFDictionary}s. If the array contains a single element, just that element will be added.
     *
     * @param params the array of {@code DecodeParams} for the stream
     */
    public void addDecodeParams(PDFArray params) {
        if (params != null && !params.isEmpty()) {
            if (params.size() == 1) {
                dictionary.put(DECODE_PARMS, params.get(0));
            } else {
                dictionary.put(DECODE_PARMS, params);
            }
        }
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dictionary);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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

    /**
     * Gets the {@code Filter} entry from the stream dictionary.
     *
     * @return the filter entry or null if it isn't present
     */
    PDFArray getFilter() {
        return (PDFArray) dictionary.get(FILTER);
    }

    /**
     * Gets the {@code DecodeParams} entry from the stream dictionary.
     *
     * @return the entry or null if it isn't present
     */
    PDFArray getDecodeParams() {
        PDFObject object = dictionary.get(DECODE_PARMS);
        if (object instanceof PDFDictionary) {
            PDFArray result = new PDFArray();
            result.add(object);
            return result;
        }
        return (PDFArray) object;
    }
}
