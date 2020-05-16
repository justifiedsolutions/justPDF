/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Content;

/**
 * A factory for {@link ContentLayout}s.
 */
interface ContentLayoutFactory {
    /**
     * Identifies if this ContentLayout supports the specified {@link Content}.
     *
     * @param content the content to check
     * @return true if the specified content is supported
     */
    boolean supportsContent(Content content);

    /**
     * Factory method for a {@link ContentLayout}.
     *
     * @param content the content to layout
     * @return the new ContentLayout for the content
     */
    ContentLayout getContentLayout(Content content);

}
