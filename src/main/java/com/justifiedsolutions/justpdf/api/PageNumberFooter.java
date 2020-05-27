/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.font.Font;

/**
 * A convenience class that places "Page N" at the bottom of a page (where N is the page number). The page number can be
 * aligned either to the left, center, or right of the page.
 */
public class PageNumberFooter implements Footer {

    private final boolean validForFirstPage;
    private final HorizontalAlignment alignment;
    private final Font font;

    /**
     * Creates a new PageNumberFooter.
     *
     * @param validForFirstPage true if the page number should be on the first page
     * @param alignment         where on the line the page number should appear
     * @param font              the font to use for the text
     */
    public PageNumberFooter(boolean validForFirstPage, HorizontalAlignment alignment, Font font) {
        this.validForFirstPage = validForFirstPage;
        this.alignment = alignment;
        this.font = font;
    }

    /**
     * Specifies if the page number should be on the first page.
     *
     * @return true if the page number will be on the first page
     */
    public boolean isValidForFirstPage() {
        return validForFirstPage;
    }

    /**
     * Specifies the {@link HorizontalAlignment} of the page number on the page
     *
     * @return the alignment
     */
    public HorizontalAlignment getAlignment() {
        return alignment;
    }

    /**
     * Gets the {@link Font} for the {@link Footer} text
     *
     * @return the font
     */
    public Font getFont() {
        return font;
    }

    @Override
    public boolean isValidForPageNumber(int pageNumber) {
        boolean result = true;
        if ((pageNumber == 1) && !isValidForFirstPage()) {
            result = false;
        }
        return result;
    }

    @Override
    public Paragraph getParagraph(int pageNumber) {
        Paragraph result = new Paragraph("Page " + pageNumber);
        result.setAlignment(getAlignment());
        result.setFont(getFont());
        return result;
    }
}
