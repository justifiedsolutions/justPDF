/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.font;

import com.justifiedsolutions.justpdf.pdf.object.*;

import java.util.Objects;

/**
 * Models a Font Descriptor in a PDF document.
 *
 * @see "ISO 32000-1:2008, 9.8"
 */
public final class PDFFontDescriptor extends PDFDictionary {
    static final PDFName TYPE = new PDFName("Type");
    static final PDFName FONT_DESCRIPTOR = new PDFName("FontDescriptor");
    static final PDFName FONT_NAME = new PDFName("FontName");
    static final PDFName FLAGS = new PDFName("Flags");
    static final PDFName FONT_BBOX = new PDFName("FontBBox");
    static final PDFName ITALIC_ANGLE = new PDFName("ItalicAngle");
    static final PDFName ASCENT = new PDFName("Ascent");
    static final PDFName DESCENT = new PDFName("Descent");
    static final PDFName CAP_HEIGHT = new PDFName("CapHeight");
    static final PDFName STEM_V = new PDFName("StemV");
    static final PDFName STEM_H = new PDFName("StemH");

    /**
     * Creates a new Font Descriptor.
     *
     * @param flags the flags value for the specific font
     */
    PDFFontDescriptor(int flags) {
        put(TYPE, FONT_DESCRIPTOR);
        put(FLAGS, new PDFInteger(flags));
    }

    /**
     * Parses a line of an AFM file and sets any values required from that line
     *
     * @param line the AFM file line
     */
    void parseAFMLine(String line) {
        Objects.requireNonNull(line);
        if (line.contains("FontName")) {
            parseStringValue(line);
        } else if (line.contains("FontBBox")) {
            parseRectangleValue(line);
        } else if (line.contains("CapHeight")) {
            parseFloatValue(line, CAP_HEIGHT);
        } else if (line.contains("Ascender")) {
            parseFloatValue(line, ASCENT);
        } else if (line.contains("Descender")) {
            parseFloatValue(line, DESCENT);
        } else if (line.contains("StdHW")) {
            parseFloatValue(line, STEM_H);
        } else if (line.contains("StdVW")) {
            parseFloatValue(line, STEM_V);
        } else if (line.contains("ItalicAngle")) {
            parseFloatValue(line, ITALIC_ANGLE);
        }
    }

    private void parseStringValue(String line) {
        String value = parseSingleValue(line);
        if (value != null) {
            put(PDFFontDescriptor.FONT_NAME, new PDFName(value));
        }
    }

    private void parseFloatValue(String line, PDFName key) {
        String value = parseSingleValue(line);
        if (value != null) {
            float result = Float.parseFloat(value);
            put(key, new PDFReal(result));
        }
    }

    private void parseRectangleValue(String line) {
        String[] kv = line.trim().split("[ \t]");
        if (kv.length == 5) {
            PDFRectangle rect = new PDFRectangle(Float.parseFloat(kv[1]), Float.parseFloat(kv[2]),
                    Float.parseFloat(kv[3]), Float.parseFloat(kv[4]));
            put(PDFFontDescriptor.FONT_BBOX, rect);
        }
    }

    private String parseSingleValue(String line) {
        String[] kv = line.trim().split("[ \t]");
        if (kv.length == 2) {
            return kv[1];
        }
        return null;
    }
}
