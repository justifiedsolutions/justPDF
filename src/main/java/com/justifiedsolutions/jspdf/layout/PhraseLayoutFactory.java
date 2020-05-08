/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Content;
import com.justifiedsolutions.jspdf.api.content.Phrase;


/**
 * A {@link ContentLayoutFactory} that supports {@link Phrase}s.
 */
class PhraseLayoutFactory implements ContentLayoutFactory {
    private final float lineWidth;

    PhraseLayoutFactory(float lineWidth) {
        this.lineWidth = lineWidth;
    }

    @Override
    public boolean supportsContent(Content content) {
        return (content instanceof Phrase);
    }

    @Override
    public ContentLayout getContentLayout(Content content) {
        return new PhraseLayout(lineWidth, (Phrase) content);
    }
}
