/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Specifies a text encoding.
 */
public abstract class Encoding {

    private static final List<Character> ESCAPED_CHARACTERS = new ArrayList<>();

    static {
        ESCAPED_CHARACTERS.add('\n');
        ESCAPED_CHARACTERS.add('\r');
        ESCAPED_CHARACTERS.add('\t');
        ESCAPED_CHARACTERS.add('\b');
        ESCAPED_CHARACTERS.add('\f');
        ESCAPED_CHARACTERS.add('(');
        ESCAPED_CHARACTERS.add(')');
        ESCAPED_CHARACTERS.add('\\');
    }

    /**
     * Encodes the specified text.
     *
     * @param text the text to encode
     * @return the encoded bytes
     */
    public byte[] encodeString(String text) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();

        for (char character : text.toCharArray()) {
            if (character < getMinCharacter() || (character > getMaxCharacter() && character < 256)) {
                result.writeBytes(escapeValue(character));
            } else {
                Integer charCode = getEncodingMap().get(character);
                if (charCode != null) {
                    result.write(charCode);
                }
            }
        }
        return result.toByteArray();
    }

    /**
     * Gets the minimum decimal character represented in the encoding map.
     *
     * @return the minimum decimal character
     */
    protected abstract int getMinCharacter();

    /**
     * Gets the maximum decimal character represented in the encoding map.
     *
     * @return the maximum decimal character
     */
    protected abstract int getMaxCharacter();

    /**
     * Gets the encoding map that maps characters to a decimal encoding.
     *
     * @return the encoding map
     */
    protected abstract Map<Character, Integer> getEncodingMap();

    private byte[] escapeValue(char c) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (ESCAPED_CHARACTERS.contains(c)) {
            bytes.write('\\');
        }
        bytes.write(c);
        return bytes.toByteArray();
    }

}
