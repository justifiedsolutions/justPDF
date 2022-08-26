/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.Document;

/**
 * A PageBreak is a special type of {@link Content} that can only be added to a
 * {@link Document}.
 */
public final class PageBreak implements Content {

    /**
     * Creates a new {@code PageBreak}.
     */
    public PageBreak() {
        // empty
    }
}
