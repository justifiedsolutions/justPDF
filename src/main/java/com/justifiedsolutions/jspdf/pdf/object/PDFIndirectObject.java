/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents an <code>indirect object</code> in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.10"
 */
public class PDFIndirectObject implements PDFObject {

    private static final AtomicInteger LAST_USED_OBJECT_NUMBER = new AtomicInteger(0);

    private final int objectNumber;
    private final int generationNumber;
    private final PDFObject object;
    private final Reference reference;

    /**
     * Creates the PDFIndirectObject that points at the specified {@link PDFObject}.
     *
     * @param object the referenced object
     */
    public PDFIndirectObject(PDFObject object) {
        this.objectNumber = LAST_USED_OBJECT_NUMBER.addAndGet(1);
        this.generationNumber = 0;
        this.object = Objects.requireNonNull(object);
        this.reference = new Reference();
    }

    /**
     * Gets the unique object number.
     *
     * @return the object number
     */
    public int getObjectNumber() {
        return objectNumber;
    }

    /**
     * Gets the generation number. This is <code>0</code> unless the PDF was updated.
     *
     * @return the generation number
     */
    public int getGenerationNumber() {
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
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write(String.valueOf(objectNumber).getBytes(StandardCharsets.UTF_8));
        pdf.write(' ');
        pdf.write(String.valueOf(generationNumber).getBytes(StandardCharsets.UTF_8));
        pdf.write(" obj\n".getBytes(StandardCharsets.UTF_8));
        object.writeToPDF(pdf);
        pdf.write("\nendobj\n".getBytes(StandardCharsets.UTF_8));
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
        public void writeToPDF(OutputStream pdf) throws IOException {
            pdf.write(String.valueOf(objectNumber).getBytes(StandardCharsets.UTF_8));
            pdf.write(' ');
            pdf.write(String.valueOf(generationNumber).getBytes(StandardCharsets.UTF_8));
            pdf.write(' ');
            pdf.write('R');
        }
    }
}
