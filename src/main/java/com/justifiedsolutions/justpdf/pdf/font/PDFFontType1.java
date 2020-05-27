/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.font;

import com.justifiedsolutions.justpdf.pdf.object.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a Type 1 Font in a PDF document.
 *
 * @see "ISO 32000-1:2008, 9.6.2"
 */
public final class PDFFontType1 extends PDFFont {

    static final PDFName TYPE1 = new PDFName("Type1");
    static final PDFName FIRST_CHAR = new PDFName("FirstChar");
    static final PDFName LAST_CHAR = new PDFName("LastChar");
    static final PDFName WIDTHS = new PDFName("Widths");
    static final PDFName FONT_DESCRIPTOR = new PDFName("FontDescriptor");
    static final PDFName ENCODING = new PDFName("Encoding");
    static final PDFName WIN_ANSI_ENCODING = new PDFName("WinAnsiEncoding");
    private static final Map<FontName, PDFFontType1> CACHE = new EnumMap<>(FontName.class);
    private static final int FLAG_NON_SYMBOLIC = 32;

    private final PDFFontDescriptor descriptor;

    private final Map<Integer, Integer> characterWidths = new HashMap<>();
    private float minimumLeading;

    /**
     * Creates a new Type 1 font with the specified name. This supports the "standard 14" fonts in a PDF.
     *
     * @param fontName the font name
     */
    private PDFFontType1(FontName fontName) {
        put(SUBTYPE, TYPE1);
        put(BASE_FONT, new PDFName(fontName.baseFont));
        if ((fontName.flags & FLAG_NON_SYMBOLIC) == FLAG_NON_SYMBOLIC) {
            put(ENCODING, WIN_ANSI_ENCODING);
        }
        descriptor = new PDFFontDescriptor(fontName.flags);
        readFontMetrics(fontName.baseFont);
    }

    /**
     * Gets an instance of a {@link PDFFont} with the specified {@link FontName}.
     *
     * @param fontName the FontName for the font.
     * @return the font
     */
    public static PDFFont getInstance(FontName fontName) {
        return CACHE.computeIfAbsent(fontName, PDFFontType1::new);
    }

    @Override
    public int getCharacterWidth(int character) {
        Integer width = characterWidths.get(character);
        return (width != null) ? width : 0;
    }

    @Override
    public float getMinimumLeading() {
        return minimumLeading;
    }

    private void readFontMetrics(String fontName) {
        String location = String.format("/afm/%s.afm", fontName);
        InputStream is = PDFFontType1.class.getResourceAsStream(location);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.US_ASCII))) {
            String line = reader.readLine();
            while (!line.contains("StartCharMetrics")) {
                descriptor.parseAFMLine(line);
                line = reader.readLine();
            }

            determineMinimumLeading();

            int firstChar = 0;
            int lastChar = 0;
            PDFArray widths = new PDFArray();

            boolean firstLine = true;
            line = reader.readLine();
            while (!line.contains("EndCharMetrics")) {
                int character = parseCharacter(line);
                if (character < lastChar) {
                    break;
                }
                int width = parseWidth(line);
                if (firstLine) {
                    firstChar = character;
                    firstLine = false;
                }
                lastChar = character;
                widths.add(new PDFInteger(width));
                characterWidths.put(character, width);
                line = reader.readLine();
            }

            put(FIRST_CHAR, new PDFInteger(firstChar));
            put(LAST_CHAR, new PDFInteger(lastChar));
            put(WIDTHS, widths);
            put(FONT_DESCRIPTOR, descriptor);

        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read AFM file: " + location, e);
        }
    }

    private void determineMinimumLeading() {
        PDFObject ascentObject = descriptor.get(PDFFontDescriptor.ASCENT);
        if (ascentObject instanceof PDFReal) {
            PDFReal ascent = (PDFReal) ascentObject;
            minimumLeading = ascent.getValue();
        }

        PDFObject bboxObject = descriptor.get(PDFFontDescriptor.FONT_BBOX);
        if (minimumLeading == 0 && bboxObject instanceof PDFRectangle) {
            PDFRectangle bboxRect = (PDFRectangle) bboxObject;
            minimumLeading = bboxRect.getHeight().getValue();
        }
    }

    private int parseCharacter(String line) {
        return parseKeyValue(line, "C", -1);
    }

    private int parseWidth(String line) {
        return parseKeyValue(line, "WX", 0);
    }

    private int parseKeyValue(String line, String key, int defaultResult) {
        int result = defaultResult;
        String[] kvPairs = line.split(";");
        for (String charString : kvPairs) {
            String[] kv = charString.trim().split("[ \t]");
            if (kv.length == 2 && key.equals(kv[0])) {
                result = Integer.parseInt(kv[1]);
                break;
            }
        }
        return result;
    }

    /**
     * Enumeration of the Standard 14 PDF fonts.
     */
    public enum FontName {
        COURIER("Courier", 33),
        COURIER_BOLD("Courier-Bold", 33),
        COURIER_OBLIQUE("Courier-Oblique", 97),
        COURIER_BOLD_OBLIQUE("Courier-BoldOblique", 97),
        HELVETICA("Helvetica", 32),
        HELVETICA_BOLD("Helvetica-Bold", 32),
        HELVETICA_OBLIQUE("Helvetica-Oblique", 96),
        HELVETICA_BOLD_OBLIQUE("Helvetica-BoldOblique", 96),
        SYMBOL("Symbol", 4),
        TIMES_ROMAN("Times-Roman", 34),
        TIMES_BOLD("Times-Bold", 34),
        TIMES_ITALIC("Times-Italic", 98),
        TIMES_BOLD_ITALIC("Times-BoldItalic", 98),
        ZAPFDINGBATS("ZapfDingbats", 4);

        private final String baseFont;
        private final int flags;

        FontName(String baseFont, int flags) {
            this.baseFont = baseFont;
            this.flags = flags;
        }
    }
}
