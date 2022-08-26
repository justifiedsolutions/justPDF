/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFArray;
import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFDocEncodedString;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;
import com.justifiedsolutions.justpdf.pdf.object.PDFInteger;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;
import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Represents the {@code Outline Dictionary} in a PDF document.
 *
 * @see "ISO 32000-1:2008, 12.3.3"
 */
public class PDFOutlineDictionary extends PDFDictionary {
    private static final PDFName TYPE = new PDFName("Type");
    private static final PDFName OUTLINES = new PDFName("Outlines");

    private static final PDFName FIRST = new PDFName("First");
    private static final PDFName LAST = new PDFName("Last");
    private static final PDFName COUNT = new PDFName("Count");

    private static final PDFName PARENT = new PDFName("Parent");
    private static final PDFName PREV = new PDFName("Prev");
    private static final PDFName NEXT = new PDFName("Next");

    private static final PDFName TITLE = new PDFName("Title");
    private static final PDFName DEST = new PDFName("Dest");

    private static final PDFName FIT = new PDFName("XYZ");
    private static final float BREATHING_ROOM = 3f;

    private final PDFDocument document;
    private final Deque<Item> items = new LinkedList<>();
    private final PDFIndirectObject.Reference reference;

    /**
     * Creates a new PDFOutlineDictionary.
     *
     * @param document the document this outline belongs to
     */
    PDFOutlineDictionary(PDFDocument document) {
        this.document = document;
        this.reference = this.document.createIndirectObject(this).getReference();
        put(TYPE, OUTLINES);
    }

    /**
     * Creates a new top-level item in the outline dictionary.
     *
     * @param title the text shown in the outline
     * @param page  the reference to the page the content resides on
     * @param top   the vertical position on the page of the beginning of the
     *              content
     * @return the new item
     */
    public Item createItem(String title, PDFIndirectObject.Reference page, float top) {
        Item result = new Item(title, page, top);
        result.put(PARENT, this.reference);

        if (items.isEmpty()) {
            put(FIRST, result.reference);
        } else {
            Item last = items.getLast();
            last.put(NEXT, result.reference);
            result.put(PREV, last.reference);
        }
        items.add(result);
        put(LAST, result.reference);
        put(COUNT, new PDFInteger(items.size()));
        return result;
    }

    /**
     * Gets a reference to this dictionary.
     *
     * @return the indirect object reference
     */
    PDFIndirectObject.Reference getReference() {
        return reference;
    }

    /**
     * Represents an item in the outline.
     *
     * @see "ISO 32000-1:2008, 12.3.3"
     */
    public class Item extends PDFDictionary {

        private final PDFIndirectObject.Reference reference;

        private final Deque<Item> children = new LinkedList<>();

        private Item(String title, PDFIndirectObject.Reference page, float top) {
            reference = document.createIndirectObject(this).getReference();
            put(TITLE, new PDFDocEncodedString(title));
            PDFArray dest = new PDFArray();
            dest.add(page);
            dest.add(FIT);
            dest.add(new PDFInteger(0));
            dest.add(new PDFReal(top + BREATHING_ROOM));
            dest.add(new PDFInteger(0));
            put(DEST, dest);
        }

        /**
         * Creates a child item to this item in the outline.
         *
         * @param title the text shown in the outline
         * @param page  the reference to the page the content resides on
         * @param top   the vertical position on the page of the beginning of the
         *              content
         * @return the new item
         */
        public Item createChild(String title, PDFIndirectObject.Reference page, float top) {
            Item child = new Item(title, page, top);
            child.put(PARENT, reference);

            if (children.isEmpty()) {
                put(FIRST, child);
            } else {
                Item last = children.getLast();
                last.put(NEXT, child.reference);
                child.put(PREV, last.reference);
            }
            children.add(child);
            put(LAST, child);
            put(COUNT, new PDFInteger(-1 * children.size()));
            return child;
        }
    }
}
