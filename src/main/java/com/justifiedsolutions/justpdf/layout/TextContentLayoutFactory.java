/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.TextContent;


/**
 * A {@link ContentLayoutFactory} that supports {@link Paragraph}s.
 */
class TextContentLayoutFactory implements ContentLayoutFactory {
    private final float lineWidth;

    /**
     * Creates a new TextContentLayoutFactory for creating new TextContentLayouts.
     *
     * @param lineWidth the maximum width of a line of content on a page
     */
    TextContentLayoutFactory(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public boolean supportsContent(Content content) {
        return (content instanceof TextContent);
    }

    @Override
    public ContentLayout getContentLayout(Content content) {
        return new TextContentLayout(lineWidth, (TextContent) content);
    }
}
