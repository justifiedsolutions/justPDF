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

import static org.junit.jupiter.api.Assertions.*;

public class PDFDictionaryTest {

    @Test
    public void sizeAndPut() {
        PDFDictionary dict = new PDFDictionary();
        assertEquals(0, dict.size());
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertEquals(1, dict.size());
    }

    @Test
    public void isEmpty() {
        PDFDictionary dict = new PDFDictionary();
        assertTrue(dict.isEmpty());
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertFalse(dict.isEmpty());
    }

    @Test
    public void containsKey() {
        PDFDictionary dict = new PDFDictionary();
        assertFalse(dict.containsKey(new PDFName("key")));
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertTrue(dict.containsKey(new PDFName("key")));
    }

    @Test
    public void get() {
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        PDFObject actual = dict.get(new PDFName("key"));
        assertEquals(PDFBoolean.TRUE, actual);
    }

    @Test
    public void remove() {
        PDFDictionary dict = new PDFDictionary();
        assertFalse(dict.containsKey(new PDFName("key")));
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertTrue(dict.containsKey(new PDFName("key")));
        dict.remove(new PDFName("key"));
        assertFalse(dict.containsKey(new PDFName("key")));
    }

    @Test
    public void clear() {
        PDFDictionary dict = new PDFDictionary();
        assertFalse(dict.containsKey(new PDFName("key")));
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        assertTrue(dict.containsKey(new PDFName("key")));
        dict.clear();
        assertFalse(dict.containsKey(new PDFName("key")));
    }

    @Test
    public void keySet() {
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        Set<PDFName> keys = dict.keySet();
        assertNotNull(keys);
        assertEquals(1, keys.size());
        PDFName[] keysArray = keys.toArray(new PDFName[1]);
        assertEquals(new PDFName("key"), keysArray[0]);
    }

    @Test
    public void values() {
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("key"), PDFBoolean.TRUE);
        Collection<PDFObject> values = dict.values();
        assertNotNull(values);
        assertEquals(1, values.size());
        PDFObject[] valuesArray = values.toArray(new PDFObject[1]);
        assertEquals(PDFBoolean.TRUE, valuesArray[0]);
    }

    @Test
    public void writeToPDF1KV() throws IOException {
        ByteArrayOutputStream expected = new ByteArrayOutputStream();
        expected.writeBytes("<</Name true>>".getBytes(StandardCharsets.US_ASCII));

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        PDFDictionary dict = new PDFDictionary();
        dict.put(new PDFName("Name"), PDFBoolean.TRUE);
        dict.writeToPDF(actual);

        assertArrayEquals(expected.toByteArray(), actual.toByteArray());
    }

    @Test
    public void writeToPDF2KV() throws IOException {
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