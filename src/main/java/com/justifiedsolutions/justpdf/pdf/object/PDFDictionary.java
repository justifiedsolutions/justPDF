/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Represents a {@code dictionary object} in a PDF document.
 *
 * @see "ISO 32000-1:2008, 7.3.7"
 */
public class PDFDictionary implements PDFObject {

    private final Map<PDFName, PDFObject> dictionary = new HashMap<>();

    /**
     * Gets the number of entries in the dictionary.
     *
     * @return the number of entries
     */
    public int size() {
        return dictionary.size();
    }

    /**
     * Specifies if the dictionary is empty or not.
     *
     * @return true if size() == 0, false otherwise
     */
    public boolean isEmpty() {
        return dictionary.isEmpty();
    }

    /**
     * Specifies if the dictionary contains the specified key.
     *
     * @param key the key to check for
     * @return true if the key exists in the dictionary, false otherwise
     */
    public boolean containsKey(PDFName key) {
        return dictionary.containsKey(key);
    }

    /**
     * Gets the value associated with the specified key. A return value of {@code null} could mean either the key exists
     * and does not have a value, or the key did not exist in the dictionary.
     *
     * @param key the key to get the value for
     * @return the associated value or {@code null}
     */
    public PDFObject get(PDFName key) {
        return dictionary.get(key);
    }

    /**
     * Associates the specified value to the specified key. If the key already exists in the dictionary, the value will
     * replace the existing value.
     *
     * @param key   the key
     * @param value the new value
     */
    public void put(PDFName key, PDFObject value) {
        dictionary.put(key, value);
    }

    /**
     * Removes the key and any associated value from the dictionary.
     *
     * @param key the key to remove
     */
    public void remove(PDFName key) {
        dictionary.remove(key);
    }

    /**
     * Empties the dictionary.
     */
    public void clear() {
        dictionary.clear();
    }

    /**
     * Gets the set of keys for the dictionary.
     *
     * @return the key set
     */
    public Set<PDFName> keySet() {
        return dictionary.keySet();
    }

    /**
     * Gets the collection of values for the dictionary
     *
     * @return the values
     */
    public Collection<PDFObject> values() {
        return dictionary.values();
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFDictionary that = (PDFDictionary) o;
        return dictionary.equals(that.dictionary);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write('<');
        pdf.write('<');
        SortedSet<PDFName> keys = new TreeSet<>(dictionary.keySet());
        for (PDFName key : keys) {
            PDFObject value = dictionary.get(key);
            key.writeToPDF(pdf);
            pdf.write(' ');
            value.writeToPDF(pdf);
        }
        pdf.write('>');
        pdf.write('>');
    }
}
