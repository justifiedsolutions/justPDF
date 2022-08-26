/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFArray;
import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFRectangle;
import com.justifiedsolutions.justpdf.pdf.object.PDFStream;

/**
 * Models a page in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.7.3.3"
 */
public final class PDFPage {
    public static final PDFName TYPE_NAME = new PDFName("Type");
    public static final PDFName PAGE_NAME = new PDFName("Page");
    public static final PDFName MEDIA_BOX_NAME = new PDFName("MediaBox");
    public static final PDFName RESOURCES_NAME = new PDFName("Resources");
    public static final PDFName CONTENTS_NAME = new PDFName("Contents");
    public static final PDFName PARENT_NAME = new PDFName("Parent");

    private final Resources resources = new Resources();

    private final PDFDictionary page = new PDFDictionary();
    private final PDFIndirectObject indirectPage;

    private final PDFDocument document;

    /**
     * Creates a new PDF Page. It sets the {@code Type} key in the dictionary.
     *
     * @param pageSize the size of the page
     */
    PDFPage(PDFDocument document, PDFRectangle pageSize) {
        this.document = document;
        this.indirectPage = this.document.createIndirectObject(page);
        page.put(TYPE_NAME, PAGE_NAME);
        page.put(MEDIA_BOX_NAME, pageSize);
        page.put(RESOURCES_NAME, resources);
    }

    /**
     * Sets the contents of the page. This also sets the {@code Contents} key in the
     * dictionary.
     *
     * @param contents the contents of the page
     */
    public void setContents(PDFStream contents) {
        PDFIndirectObject indirectContents = document.createIndirectObject(contents);
        page.put(CONTENTS_NAME, indirectContents.getReference());
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
     * Gets a reference to this PDFPage.
     *
     * @return the reference to the PDFIndirectObject that wraps this page
     */
    public PDFIndirectObject.Reference getReference() {
        return this.indirectPage.getReference();
    }

    /**
     * Sets the reference to the {@code Parent} Pages node.
     *
     * @param parent the reference
     */
    void setParent(PDFIndirectObject.Reference parent) {
        page.put(PARENT_NAME, parent);
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
            procSet.add(new PDFName("ImageB"));
            procSet.add(new PDFName("ImageC"));
            procSet.add(new PDFName("ImageI"));
            put(PROC_SET, procSet);
        }

        private PDFName addFontReference(PDFIndirectObject.Reference reference) {
            PDFDictionary fonts = (PDFDictionary) get(FONT);
            if (fonts == null) {
                fonts = new PDFDictionary();
                put(FONT, fonts);
            }
            PDFName refName;
            if (!fonts.values().contains(reference)) {
                int refIndex = fonts.size() + 1;
                refName = new PDFName("F" + refIndex);
                fonts.put(refName, reference);
            } else {
                refName = null;
                for (PDFName key : fonts.keySet()) {
                    PDFObject value = fonts.get(key);
                    if (reference.equals(value)) {
                        refName = key;
                        break;
                    }
                }
                if (refName == null) {
                    throw new IllegalStateException("Font dictionary contains reference but cannot find it!");
                }
            }
            return refName;
        }
    }
}
