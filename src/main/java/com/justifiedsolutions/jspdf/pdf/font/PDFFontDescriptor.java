/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.font;

import com.justifiedsolutions.jspdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.jspdf.pdf.object.PDFInteger;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFRectangle;

import java.util.Objects;

/**
 * Models a Font Descriptor in a PDF document.
 *
 * @see "ISO 32000-1:2008, 9.8"
 */
public class PDFFontDescriptor extends PDFDictionary {
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

    PDFFontDescriptor(int flags) {
        put(TYPE, FONT_DESCRIPTOR);
        put(FLAGS, new PDFInteger(flags));
    }

    void parseAFMLine(String line) {
        Objects.requireNonNull(line);
        if (line.contains("FontName")) {
            parseStringValue(line, FONT_NAME);
        } else if (line.contains("FontBBox")) {
            parseRectangleValue(line, FONT_BBOX);
        } else if (line.contains("CapHeight")) {
            parseIntegerValue(line, CAP_HEIGHT);
        } else if (line.contains("Ascender")) {
            parseIntegerValue(line, ASCENT);
        } else if (line.contains("Descender")) {
            parseIntegerValue(line, DESCENT);
        } else if (line.contains("StdHW")) {
            parseIntegerValue(line, STEM_H);
        } else if (line.contains("StdVW")) {
            parseIntegerValue(line, STEM_V);
        } else if (line.contains("ItalicAngle")) {
            parseIntegerValue(line, ITALIC_ANGLE);
        }
    }

    private void parseStringValue(String line, PDFName key) {
        String value = parseSingleValue(line);
        if (value != null) {
            put(key, new PDFName(value));
        }
    }

    private void parseIntegerValue(String line, PDFName key) {
        String value = parseSingleValue(line);
        if (value != null) {
            int result = Integer.parseInt(value);
            put(key, new PDFInteger(result));
        }
    }

    private void parseRectangleValue(String line, PDFName key) {
        String[] kv = line.trim().split("[ \t]");
        if (kv.length == 5) {
            PDFRectangle rect = new PDFRectangle(Integer.parseInt(kv[1]), Integer.parseInt(kv[2]),
                    Integer.parseInt(kv[3]), Integer.parseInt(kv[4]));
            put(key, rect);
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
