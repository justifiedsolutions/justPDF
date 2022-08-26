/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.PDFString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PDFInfoDictionaryTest {

    @Test
    void putThrowsException() {
        PDFInfoDictionary info = new PDFInfoDictionary();
        PDFString value = new PDFString("");
        assertThrows(IllegalArgumentException.class, () -> info.put(PDFInfoDictionary.TITLE, value));
    }
}