/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.PDFWritable;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Models the Cross Reference Table in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.5.4"
 */
final class PDFXRefTable implements PDFWritable {

    private final List<PDFIndirectObject> indirectObjects = new ArrayList<>();

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        String line = String.format("xref\n0 %d\n", indirectObjects.size() + 1);
        pdf.write(line.getBytes(StandardCharsets.US_ASCII));
        new PDFXrefTableEntry().writeToPDF(pdf);
        for (PDFIndirectObject indirectObject : indirectObjects) {
            new PDFXrefTableEntry(indirectObject).writeToPDF(pdf);
        }
    }

    /**
     * Sets the list of {@link PDFIndirectObject}s used in the document.
     *
     * @param indirectObjects the list of indirect objects
     */
    void setIndirectObjects(Collection<PDFIndirectObject> indirectObjects) {
        this.indirectObjects.clear();
        this.indirectObjects.addAll(indirectObjects);
        Collections.sort(this.indirectObjects);
    }

    /**
     * Represents an entry in the Xref table.
     */
    private static class PDFXrefTableEntry implements PDFWritable {
        private final int byteOffset;
        private final int generationNumber;
        private final char state;

        private PDFXrefTableEntry(PDFIndirectObject indirectObject) {
            byteOffset = indirectObject.getByteOffset().getValue();
            generationNumber = indirectObject.getGenerationNumber().getValue();
            state = 'n';
        }

        private PDFXrefTableEntry() {
            this.byteOffset = 0;
            this.generationNumber = 65_535;
            this.state = 'f';
        }

        @Override
        public void writeToPDF(OutputStream pdf) throws IOException {
            String entry = String.format("%010d %05d %c \n", byteOffset, generationNumber, state);
            pdf.write(entry.getBytes(StandardCharsets.US_ASCII));
        }
    }
}
