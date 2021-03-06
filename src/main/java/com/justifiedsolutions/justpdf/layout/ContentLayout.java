/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Content;

/**
 * A {@code ContentLayout} is responsible for taking a specific type of {@link Content} and transforming it into a
 * series of {@link ContentLine}s.
 */
interface ContentLayout {

    /**
     * Gets the minimum height of the {@link Content}. If the content specifies that it must be kept together, it will
     * be the total height of all lines of the content. If not, it will be the height of the first line of the content.
     *
     * @return the minimum height or -1 if the content is exhausted
     */
    float getMinimumHeight();

    /**
     * Gets the next {@link ContentLine} from the content.
     *
     * @param verticalPosition the upper left y coordinate on the page as the starting point of the content
     * @return the next line or {@code null} if the content is exhausted
     */
    ContentLine getNextLine(float verticalPosition);

    /**
     * Gets the remaining {@link Content}.
     *
     * @return the remaining content or {@code null} if the content is exhausted
     */
    Content getRemainingContent();

    /**
     * Gets the amount of vertical spacing before this content.
     *
     * @return the spacing before
     */
    float getSpacingBefore();

    /**
     * Gets the amount of vertical spacing after this content.
     *
     * @return the spacing after
     */
    float getSpacingAfter();
}
