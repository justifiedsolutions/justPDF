/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an {@code indirect object} in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.10"
 */
public class PDFIndirectObject implements PDFObject, Comparable<PDFIndirectObject> {

    private static final AtomicInteger LAST_USED_OBJECT_NUMBER = new AtomicInteger(0);

    private final PDFInteger objectNumber;
    private final PDFInteger generationNumber;
    private final PDFObject object;
    private final Reference reference;
    private PDFInteger byteOffset;

    /**
     * Creates the PDFIndirectObject that points at the specified {@link PDFObject}.
     *
     * @param object the referenced object
     */
    public PDFIndirectObject(PDFObject object) {
        this.objectNumber = new PDFInteger(LAST_USED_OBJECT_NUMBER.addAndGet(1));
        this.generationNumber = new PDFInteger(0);
        this.object = Objects.requireNonNull(object);
        this.reference = new Reference();
    }

    /**
     * Resets the counter used to generate the object number.
     */
    public static void resetObjectNumber() {
        LAST_USED_OBJECT_NUMBER.set(0);
    }

    /**
     * Gets the unique object number.
     *
     * @return the object number
     */
    public PDFInteger getObjectNumber() {
        return objectNumber;
    }

    /**
     * Gets the generation number. This is {@code 0} unless the PDF was updated.
     *
     * @return the generation number
     */
    public PDFInteger getGenerationNumber() {
        return generationNumber;
    }

    /**
     * Gets the referenced object.
     *
     * @return the referenced object
     */
    public PDFObject getObject() {
        return object;
    }

    public PDFInteger getByteOffset() {
        return byteOffset;
    }

    public void setByteOffset(PDFInteger byteOffset) {
        this.byteOffset = byteOffset;
    }

    /**
     * Gets the reference to the {@link PDFIndirectObject}. This should be used anywhere in the PDF document that
     * references the original object.
     *
     * @return the indirect object reference
     */
    public Reference getReference() {
        return reference;
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectNumber, generationNumber, object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFIndirectObject that = (PDFIndirectObject) o;
        return objectNumber.equals(that.objectNumber) &&
                generationNumber.equals(that.generationNumber) &&
                object.equals(that.object);
    }

    @Override
    public int compareTo(PDFIndirectObject o) {
        if (o == null) {
            return -1;
        }
        return this.objectNumber.compareTo(o.objectNumber);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectNumber.writeToPDF(out);
        out.write(' ');
        generationNumber.writeToPDF(out);
        out.write(" obj\n".getBytes(StandardCharsets.US_ASCII));
        object.writeToPDF(out);
        if (out.toByteArray()[out.size() - 1] != 10) {
            out.write('\n');
        }
        out.write("endobj\n\n".getBytes(StandardCharsets.US_ASCII));
        out.writeTo(pdf);
    }

    /**
     * Represents a reference to the associated {@link PDFIndirectObject}.
     *
     * @see "ISO 32000-1:2008, 7.3.10"
     */
    public class Reference implements PDFObject {

        private Reference() {
        }

        @Override
        public int hashCode() {
            return Objects.hash(objectNumber, generationNumber);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Reference reference = (Reference) o;
            return objectNumber.equals(reference.getObjectNumber()) &&
                    generationNumber.equals(reference.getGenerationNumber());
        }

        @Override
        public void writeToPDF(OutputStream pdf) throws IOException {
            objectNumber.writeToPDF(pdf);
            pdf.write(' ');
            generationNumber.writeToPDF(pdf);
            pdf.write(' ');
            pdf.write('R');
        }

        private PDFInteger getObjectNumber() {
            return objectNumber;
        }

        private PDFInteger getGenerationNumber() {
            return generationNumber;
        }
    }
}
