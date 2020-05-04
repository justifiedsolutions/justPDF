/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.api;

import com.justifiedsolutions.jspdf.api.content.Paragraph;

/**
 * Represents either a header or footer for a {@link com.justifiedsolutions.jspdf.api.Document}.
 *
 * @see com.justifiedsolutions.jspdf.api.Header
 * @see Footer
 */
public interface RunningMarginal {

    /**
     * Verifies that this RunningMarginal is valid for the specified page.
     *
     * @param pageNumber the page number to validate
     * @return true if this running marginal should be applied to the specified page
     */
    boolean isValidForPageNumber(int pageNumber);

    /**
     * Gets the {@link Paragraph} that should be applied to the {@link com.justifiedsolutions.jspdf.api.Document}.
     *
     * @param pageNumber the page number the {@link Paragraph} will be applied to
     * @return the Paragraph to apply
     */
    Paragraph getParagraph(int pageNumber);
}
