/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.pdf.contents.GraphicsOperator;

import java.util.List;

public class TableRow implements ContentLine {
    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public List<GraphicsOperator> getOperators() {
        return null;
    }
}
