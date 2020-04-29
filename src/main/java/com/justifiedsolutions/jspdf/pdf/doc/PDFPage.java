/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.object.*;

/**
 * Models a page in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.7.3.3"
 */
public class PDFPage {
    public static final PDFName TYPE = new PDFName("Type");
    public static final PDFName PAGE = new PDFName("Page");
    public static final PDFName MEDIA_BOX = new PDFName("MediaBox");
    public static final PDFName RESOURCES = new PDFName("Resources");
    public static final PDFName CONTENTS = new PDFName("Contents");
    public static final PDFName PARENT = new PDFName("Parent");

    private final Resources resources = new Resources();

    private final PDFDictionary page = new PDFDictionary();
    private final PDFIndirectObject indirectPage;

    private final PDFDocument document;

    /**
     * Creates a new PDF Page. It sets the <code>Type</code> key in the dictionary.
     *
     * @param pageSize the size of the page
     */
    PDFPage(PDFDocument document, PDFRectangle pageSize) {
        this.document = document;
        this.indirectPage = this.document.createIndirectObject(page);
        page.put(TYPE, PAGE);
        page.put(MEDIA_BOX, pageSize);
        page.put(RESOURCES, resources);
    }

    /**
     * Sets the contents of the page. This also sets the <code>Contents</code> key in the dictionary.
     *
     * @param contents the contents of the page
     */
    public void setContents(PDFStream contents) {
        PDFIndirectObject indirectContents = document.createIndirectObject(contents);
        page.put(CONTENTS, indirectContents.getReference());
    }

    /**
     * Adds a reference to a font that is used on the page.
     *
     * @param reference the reference
     * @return the {@link PDFName} that identifies the reference on the page
     */
    public PDFName addFontReference(PDFIndirectObject.Reference reference) {
        return resources.addFontReference(reference);
    }

    /**
     * Sets the reference to the <code>Parent</code> Pages node.
     *
     * @param parent the reference
     */
    void setParent(PDFIndirectObject.Reference parent) {
        page.put(PARENT, parent);
    }

    /**
     * Gets a reference to this PDFPage.
     *
     * @return the reference to the PDFIndirectObject that wraps this page
     */
    PDFIndirectObject.Reference getReference() {
        return this.indirectPage.getReference();
    }

    /**
     * Models Page Resources in a PDF document.
     *
     * @see "ISO 32000-1:2008, 7.8.3"
     */
    private static class Resources extends PDFDictionary {
        private static final PDFName FONT = new PDFName("Font");
        private static final PDFName PROC_SET = new PDFName("ProcSet");

        private Resources() {
            PDFArray procSet = new PDFArray();
            procSet.add(new PDFName("PDF"));
            procSet.add(new PDFName("Text"));
            put(PROC_SET, procSet);
        }

        private PDFName addFontReference(PDFIndirectObject.Reference reference) {
            PDFDictionary fonts = (PDFDictionary) get(FONT);
            if (fonts == null) {
                fonts = new PDFDictionary();
                put(FONT, fonts);
            }
            int refIndex = fonts.size() + 1;
            PDFName refName = new PDFName("F" + refIndex);
            fonts.put(refName, reference);
            return refName;
        }
    }
}
