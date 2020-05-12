/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.pdf.contents.GraphicsOperator;

import java.util.List;

/**
 * Models a line of content in a PDF document.
 */
interface ContentLine {

    /**
     * Gets the actual height of the line.
     *
     * @return the height
     */
    float getHeight();

    /**
     * Gets the list of {@link GraphicsOperator}s that represent this line.
     *
     * @return the list of operators
     */
    List<GraphicsOperator> getOperators();

}
