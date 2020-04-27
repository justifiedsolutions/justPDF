/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.font.PDFFont;
import com.justifiedsolutions.jspdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.jspdf.pdf.object.PDFInteger;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFString;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.7"
 */
public class PDFDocument {
    private final PDFHeader header = new PDFHeader();
    private final PDFXRefTable xrefTable = new PDFXRefTable();
    private final PDFTrailer trailer = new PDFTrailer();

    private final List<PDFPage> pageList = new ArrayList<>();
    private final PDFPages pages = new PDFPages();
    private final PDFIndirectObject indirectPages = new PDFIndirectObject(pages);

    private final PDFInfoDictionary info = new PDFInfoDictionary();
    private final PDFIndirectObject indirectInfo = new PDFIndirectObject(info);

    private final PDFCatalogDictionary catalog = new PDFCatalogDictionary();
    private final PDFIndirectObject indirectCatalog = new PDFIndirectObject(catalog);

    private final Map<PDFFont, PDFIndirectObject> fonts = new HashMap<>();

    /**
     * Adds a piece of metadata to the Information Dictionary
     *
     * @param key   the metadata key from {@link PDFInfoDictionary}
     * @param value a value to associate with the key
     */
    public void addInfo(PDFName key, PDFString value) {
        info.put(key, value);
    }

    /**
     * Adds a new {@link PDFPage} to the PDF document.
     *
     * @param page the page to add
     */
    public void addPage(PDFPage page) {
        pageList.add(page);
    }

    public void addFont(PDFFont font) {
        if (!fonts.containsKey(font)) {
            fonts.put(font, new PDFIndirectObject(font));
        }
    }

    public PDFIndirectObject.Reference getFontReference(PDFFont font) {
        PDFIndirectObject.Reference result = null;
        PDFIndirectObject indirectObject = fonts.get(font);
        if (indirectObject != null) {
            result = indirectObject.getReference();
        }
        return result;
    }

    /**
     * Writes the PDF document to the specified {@link OutputStream}.
     *
     * @param pdf the OutputStream to write to
     * @throws IOException if there is an issue writing the document
     */
    public void write(OutputStream pdf) throws IOException {
        CountingOutputStream cos = new CountingOutputStream(pdf);
        List<PDFIndirectObject> indirectObjects = getIndirectObjects();
        header.writeToPDF(cos);
        for (PDFIndirectObject indirectObject : indirectObjects) {
            if (catalog.equals(indirectObject.getObject())) {
                trailer.setRoot(indirectObject.getReference());
            } else if (info.equals(indirectObject.getObject())) {
                trailer.setInfo(indirectObject.getReference());
            }
            indirectObject.setByteOffset(new PDFInteger(cos.getCounter()));
            indirectObject.writeToPDF(cos);
            cos.flush();
        }
        trailer.setTotalBytes(new PDFInteger(cos.getCounter()));
        trailer.setSize(new PDFInteger(indirectObjects.size() + 1));
        xrefTable.setIndirectObjects(indirectObjects);
        xrefTable.writeToPDF(cos);
        trailer.writeToPDF(cos);
    }

    private List<PDFIndirectObject> getIndirectObjects() {
        List<PDFIndirectObject> result = new ArrayList<>();


        pageList.forEach(page -> {
            result.add(page.getIndirectContents());
            result.add(page.getIndirectPage());

            page.setParent(indirectPages.getReference());
            pages.addPage(page.getIndirectPage().getReference());
        });
        if (!pageList.isEmpty()) {
            catalog.put(PDFCatalogDictionary.PAGES, indirectPages.getReference());
            result.add(indirectPages);
        }

        result.add(indirectCatalog);

        if (!info.isEmpty()) {
            result.add(indirectInfo);
        }

        return result;
    }

    private static class CountingOutputStream extends OutputStream {

        private final OutputStream outputStream;
        private int counter;

        private CountingOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
            this.counter = 0;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            counter++;
        }

        @Override
        public void write(byte[] b) throws IOException {
            outputStream.write(b);
            counter += b.length;
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            outputStream.write(b, off, len);
            counter += len;
        }

        @Override
        public void flush() throws IOException {
            outputStream.flush();
        }

        @Override
        public void close() throws IOException {
            outputStream.close();
        }

        public int getCounter() {
            return counter;
        }
    }
}
