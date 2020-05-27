/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.object;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Date/Time in a PDF document in the form of a {@link PDFString}.
 *
 * @see "ISO 32000-1:2008, 7.9.4"
 */
public final class PDFDate extends PDFDocEncodedString {

    /**
     * Creates a new PDFDate representing the current date/time in the local time zone.
     */
    public PDFDate() {
        this(ZonedDateTime.now());
    }

    /**
     * Creates a new PDFDate representing the specified {@link ZonedDateTime}.
     *
     * @param dateTime the specified date/time
     */
    public PDFDate(ZonedDateTime dateTime) {
        super(createPdfDateString(dateTime));
    }

    /**
     * Creates a string in the format {@code D:YYYYMMDDHHmmSSOHH'mm}.
     *
     * @param dateTime the time to convert
     * @return the formatted string
     */
    private static String createPdfDateString(ZonedDateTime dateTime) {
        Objects.requireNonNull(dateTime);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'D:'yyyyMMddHHmmssXX");
        String result = dateTime.format(formatter);
        if (!result.endsWith("Z")) {
            int index = result.length() - 2;
            result = result.substring(0, index) + "'" + result.substring(index);
        }
        return result;
    }
}
