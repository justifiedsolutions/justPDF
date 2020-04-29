/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import com.justifiedsolutions.jspdf.pdf.object.PDFObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PDFCatalogDictionaryTest {

    @Test
    public void testCreate() {
        PDFCatalogDictionary dict = new PDFCatalogDictionary();
        PDFObject type = dict.get(PDFCatalogDictionary.TYPE);
        PDFObject version = dict.get(PDFCatalogDictionary.VERSION);

        assertEquals(PDFCatalogDictionary.CATALOG, type);
        assertEquals(PDFCatalogDictionary.VERSION_17, version);
    }

}