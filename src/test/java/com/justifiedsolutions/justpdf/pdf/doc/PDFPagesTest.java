/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.doc;

import com.justifiedsolutions.justpdf.pdf.object.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PDFPagesTest {

    @Test
    public void addPage() {
        PDFPages pages = new PDFPages();
        PDFObject type = pages.get(PDFPages.TYPE_NAME);
        PDFObject count = pages.get(PDFPages.COUNT_NAME);
        PDFObject kids = pages.get(PDFPages.KIDS_NAME);

        assertEquals(PDFPages.PAGES_NAME, type);
        assertEquals(new PDFInteger(0), count);
        assertEquals(new PDFArray(), kids);

        PDFIndirectObject kid = new PDFIndirectObject(PDFBoolean.TRUE);
        pages.addPage(kid.getReference());

        count = pages.get(PDFPages.COUNT_NAME);
        kids = pages.get(PDFPages.KIDS_NAME);
        assertEquals(new PDFInteger(1), count);
        PDFArray kidsArray = new PDFArray();
        kidsArray.add(kid.getReference());
        assertEquals(kidsArray, kids);
    }
}