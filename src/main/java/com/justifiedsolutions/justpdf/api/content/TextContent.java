/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.font.Font;

/**
 * TextContent is an interface for Content that has a {@link Font} and can be added to a {@link Document}.
 */
public interface TextContent extends Content {

    /**
     * Get the {@link Font} for the Content.
     *
     * @return the font
     */
    Font getFont();

    /**
     * Set the {@link Font} for the Content.
     *
     * @param font the font
     */
    void setFont(Font font);

    /**
     * Specifies if the content should be hyphenated automatically. Defaults to {@code true}. When a {@code TextContent}
     * item added to a larger {@code TextContent} item, this item will take on the hyphenation state of the wrapping
     * item.
     *
     * @return true if the content should be auto-hyphenated.
     */
    boolean isHyphenate();

    /**
     * Specifies if the content should be hyphenated automatically. Defaults to {@code true}.
     *
     * @param hyphenate true if the content should be auto-hyphenated.
     */
    void setHyphenate(boolean hyphenate);
}
