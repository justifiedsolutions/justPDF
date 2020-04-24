/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an <code>array object</code> in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.6"
 */
public class PDFArray implements PDFObject {

    private final List<PDFObject> objects = new ArrayList<>();

    /**
     * Gets the number of entries in the array.
     *
     * @return the number of entries
     */
    public int size() {
        return objects.size();
    }

    /**
     * Specifies if the array is empty or not.
     *
     * @return true if size() == 0, false otherwise
     */
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    /**
     * Specifies if the array contains the specified {@link PDFObject}.
     *
     * @param o the object to check for
     * @return true if the object exists in the array, false otherwise
     */
    public boolean contains(PDFObject o) {
        return objects.contains(o);
    }

    /**
     * Adds the {@link PDFObject} to the array.
     *
     * @param pdfObject the object to add
     */
    public void add(PDFObject pdfObject) {
        objects.add(pdfObject);
    }

    /**
     * Removes the {@link PDFObject} from the array.
     *
     * @param o the object to remove
     */
    public void remove(PDFObject o) {
        objects.remove(o);
    }

    /**
     * Empties the array.
     */
    public void clear() {
        objects.clear();
    }

    /**
     * Gets the {@link PDFObject} at the specified index.
     *
     * @param index the index to get
     * @return the object at the specified index
     */
    public PDFObject get(int index) {
        return objects.get(index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objects);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFArray pdfArray = (PDFArray) o;
        return objects.equals(pdfArray.objects);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('[');
        pdf.write(' ');
        for (PDFObject obj : objects) {
            obj.writeToPDF(pdf);
            pdf.write(' ');
        }
        pdf.write(']');
    }
}
