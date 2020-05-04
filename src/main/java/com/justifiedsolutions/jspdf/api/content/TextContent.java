/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api.content;

import com.justifiedsolutions.jspdf.api.Document;
import com.justifiedsolutions.jspdf.api.font.Font;

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
