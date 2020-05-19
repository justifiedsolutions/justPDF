/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.Table;


/**
 * A {@link ContentLayoutFactory} that supports {@link Table}s.
 */
class TableLayoutFactory implements ContentLayoutFactory {
    private final Margin margin;
    private final float lineWidth;

    /**
     * Creates a new TableLayoutFactory.
     *
     * @param margin    the margin of the page
     * @param lineWidth the maximum width of content on the page
     */
    TableLayoutFactory(Margin margin, float lineWidth) {
        this.margin = margin;
        this.lineWidth = lineWidth;
    }

    @Override
    public boolean supportsContent(Content content) {
        return (content instanceof Table);
    }

    @Override
    public ContentLayout getContentLayout(Content content) {
        return new TableLayout(margin, lineWidth, (Table) content);
    }
}
