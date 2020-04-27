/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PDFDateTest {
    @Test
    public void createPDFDateStringEpochUTC() throws IOException {
        String expected = "D:19700101000000Z";
        ZonedDateTime input = ZonedDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC);

        testPDFDateCreation(expected, input);
    }

    @Test
    public void createPDFDateStringZMinus6() throws IOException {
        String expected = "D:19770124203800-06'00";
        ZonedDateTime input = ZonedDateTime.of(1977, 1, 24, 20, 38, 0, 0, ZoneOffset.of("-6"));

        testPDFDateCreation(expected, input);
    }

    @Test
    public void createPDFDateStringZPlus0630() throws IOException {
        String expected = "D:19770124203800+06'30";
        ZonedDateTime input = ZonedDateTime.of(1977, 1, 24, 20, 38, 0, 0, ZoneOffset.of("+0630"));

        testPDFDateCreation(expected, input);
    }

    private void testPDFDateCreation(String dateString, ZonedDateTime date) throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.write('(');
        expected.writeBytes(dateString.getBytes(StandardCharsets.US_ASCII));
        expected.write(')');

        PDFDate pdfDate = new PDFDate(date);
        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        pdfDate.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

}