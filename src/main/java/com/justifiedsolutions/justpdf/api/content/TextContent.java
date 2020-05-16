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

}
