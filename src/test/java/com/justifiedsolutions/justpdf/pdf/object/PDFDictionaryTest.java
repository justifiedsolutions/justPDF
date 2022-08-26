/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PDFDictionaryTest {

    @Test
    void sizeAndPut() {
        PDFDictionary dict = new PDFDictionary();
        assertEquals(0, dict.size());
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertEquals(1, dict.size());
    }

    @Test
    void isEmpty() {
        PDFDictionary dict = new PDFDictionary();
        assertTrue(dict.isEmpty());
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertFalse(dict.isEmpty());
    }

    @Test
    void containsKey() {
        PDFDictionary dict = new PDFDictionary();
        assertFalse(dict.containsKey(new PDFName("key")));
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertTrue(dict.containsKey(new PDFName("key")));
    }

    @Test
    void get() {
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        PDFObject actual = dict.get(new PDFName("key"));
        assertEquals(PDFBoolean.TRUE, actual);
    }

    @Test
    void remove() {
        PDFDictionary dict = new PDFDictionary();
        assertFalse(dict.containsKey(new PDFName("key")));
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertTrue(dict.containsKey(new PDFName("key")));
        dict.remove(new PDFName("key"));
        assertFalse(dict.containsKey(new PDFName("key")));
    }

    @Test
    void clear() {
        PDFDictionary dict = new PDFDictionary();
        assertFalse(dict.containsKey(new PDFName("key")));
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertTrue(dict.containsKey(new PDFName("key")));
        dict.clear();
        assertFalse(dict.containsKey(new PDFName("key")));
    }

    @Test
    void keySet() {
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        Set<PDFName> keys = dict.keySet();
        assertNotNull(keys);
        assertEquals(1, keys.size());
        PDFName[] keysArray = keys.toArray(new PDFName[0]);
        assertEquals(new PDFName("key"), keysArray[0]);
    }

    @Test
    void values() {
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        Collection<PDFObject> values = dict.values();
        assertNotNull(values);
        assertEquals(1, values.size());
        PDFObject[] valuesArray = values.toArray(new PDFObject[0]);
        assertEquals(PDFBoolean.TRUE, valuesArray[0]);
    }

    @Test
    void writeToPDF1KV() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("<</Name true>>".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("Name"), PDFBoolean.TRUE);
        dict.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    void writeToPDF2KV() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("<</Name true/Type 42>>".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("Name"), PDFBoolean.TRUE);
        dict.put(new PDFName("Type"), new PDFInteger(42));
        dict.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }
}