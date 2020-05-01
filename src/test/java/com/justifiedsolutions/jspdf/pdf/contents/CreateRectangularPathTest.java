/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFReal;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CreateRectangularPathTest {
    private final PDFReal llx = new PDFReal(1);
    private final PDFReal lly = new PDFReal(1);
    private final PDFReal urx = new PDFReal(10);
    private final PDFReal ury = new PDFReal(20);
    private final PDFRectangle rectangle = new PDFRectangle(llx, lly, urx, ury);
    private final CreateRectangularPath operator = new CreateRectangularPath(rectangle);

    @Test
    public void writeToPDF() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("1 1 9 19 re\n".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        operator.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}