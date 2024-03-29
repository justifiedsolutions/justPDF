/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PDFCatalogDictionaryTest {

    @Test
    void testCreate() {
        PDFCatalogDictionary dict = new PDFCatalogDictionary();
        PDFObject type = dict.get(PDFCatalogDictionary.TYPE);
        PDFObject version = dict.get(PDFCatalogDictionary.VERSION);

        assertEquals(PDFCatalogDictionary.CATALOG, type);
        assertEquals(PDFCatalogDictionary.VERSION_17, version);
    }

}