/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.Paragraph;


/**
 * A {@link ContentLayoutFactory} that supports {@link Paragraph}s.
 */
class ParagraphLayoutFactory implements ContentLayoutFactory {
    private final float lineWidth;

    ParagraphLayoutFactory(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public boolean supportsContent(Content content) {
        return (content instanceof Paragraph);
    }

    @Override
    public ContentLayout getContentLayout(Content content) {
        return new ParagraphLayout(lineWidth, (Paragraph) content);
    }
}
