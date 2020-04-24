/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PDFNameTest {

    @Test
    public void writeToPDFPound() throws IOException {
        testPDFName("#foo", "/#23foo");
    }

    @Test
    public void writeToPDF0() throws IOException {
        testPDFName("Name1", "/Name1");
    }

    @Test
    public void writeToPDF1() throws IOException {
        testPDFName("ASomewhatLongerName", "/ASomewhatLongerName");
    }

    @Test
    public void writeToPDF2() throws IOException {
        testPDFName("A;Name_With-Various***Characters?", "/A;Name_With-Various***Characters?");
    }

    @Test
    public void writeToPDF3() throws IOException {
        testPDFName("1.2", "/1.2");
    }

    @Test
    public void writeToPDF4() throws IOException {
        testPDFName("$$", "/$$");
    }

    @Test
    public void writeToPDF5() throws IOException {
        testPDFName("@pattern", "/@pattern");
    }

    @Test
    public void writeToPDF6() throws IOException {
        testPDFName(".notdef", "/.notdef");
    }

    @Test
    public void writeToPDF7() throws IOException {
        testPDFName("Lime Green", "/Lime#20Green");
    }

    @Test
    public void writeToPDF8() throws IOException {
        testPDFName("The_Key_of_F#_Minor", "/The_Key_of_F#23_Minor");
    }

    @Test
    public void writeToPDFLow() throws IOException {
        testPDFName("foo bar", "/foo#20bar");
    }

    @Test
    public void writeToPDFHigh() throws IOException {
        testPDFName("foo\u00BFbar", "/foo#bfbar");
    }

    private void testPDFName(String input, String pdfName) throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes(pdfName.getBytes(StandardCharsets.UTF_8));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFName name = new PDFName(input);
        name.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}