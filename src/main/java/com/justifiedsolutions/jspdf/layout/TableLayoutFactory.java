/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.Margin;
import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.Table;


/**
 * A {@link ContentLayoutFactory} that supports {@link Table}s.
 */
class TableLayoutFactory implements ContentLayoutFactory {
    private final Margin margin;
    private final float lineWidth;

    public TableLayoutFactory(Margin margin, float lineWidth) {
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
