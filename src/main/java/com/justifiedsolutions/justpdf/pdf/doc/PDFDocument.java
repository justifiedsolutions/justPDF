/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.object.*;

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

    private final List<PDFIndirectObject> indirectObjects = new ArrayList<>();

    private final PDFPages pages = new PDFPages();
    private final PDFInfoDictionary info = new PDFInfoDictionary();
    private final PDFCatalogDictionary catalog = new PDFCatalogDictionary();
    private final Map<PDFFont, PDFIndirectObject> fonts = new HashMap<>();

    public PDFDocument() {
        PDFIndirectObject.resetObjectNumber();
        PDFIndirectObject indirectCatalog = createIndirectObject(catalog);
        trailer.setRoot(indirectCatalog.getReference());
    }

    /**
     * Adds a piece of metadata to the Information Dictionary
     *
     * @param key   the metadata key from {@link PDFInfoDictionary}
     * @param value a value to associate with the key
     */
    public void addInfo(PDFName key, PDFString value) {
        if (!trailer.hasInfo()) {
            PDFIndirectObject indirectInfo = createIndirectObject(info);
            trailer.setInfo(indirectInfo.getReference());
        }
        info.put(key, value);
    }

    /**
     * Factory method for new {@link PDFPage}s. Adds the new PDFPage to the PDF document.
     *
     * @param pageSize the size of the new page
     * @return the new PDFPage
     */
    public PDFPage createPage(PDFRectangle pageSize) {
        PDFIndirectObject.Reference pagesReference = (PDFIndirectObject.Reference) catalog.get(PDFCatalogDictionary.PAGES);
        if (pagesReference == null) {
            PDFIndirectObject indirectObject = createIndirectObject(pages);
            pagesReference = indirectObject.getReference();
            catalog.put(PDFCatalogDictionary.PAGES, pagesReference);
        }
        PDFPage page = new PDFPage(this, pageSize);
        page.setParent(pagesReference);
        pages.addPage(page.getReference());

        return page;
    }

    public PDFIndirectObject.Reference addFont(PDFFont font) {
        PDFIndirectObject indirectFont = fonts.get(font);
        if (indirectFont == null) {
            indirectFont = createIndirectObject(font);
            fonts.put(font, indirectFont);
        }
        return indirectFont.getReference();
    }

    /**
     * Writes the PDF document to the specified {@link OutputStream}.
     *
     * @param pdf the OutputStream to write to
     * @throws IOException if there is an issue writing the document
     */
    public void write(OutputStream pdf) throws IOException {
        CountingOutputStream cos = new CountingOutputStream(pdf);
        header.writeToPDF(cos);
        for (PDFIndirectObject indirectObject : indirectObjects) {
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

    /**
     * All {@link PDFIndirectObject}s in a document must be created through this method.
     *
     * @param object the object to wrap
     * @return the PDFIndirectObject
     */
    PDFIndirectObject createIndirectObject(PDFObject object) {
        PDFIndirectObject result = new PDFIndirectObject(object);
        this.indirectObjects.add(result);
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