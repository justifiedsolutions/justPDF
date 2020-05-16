/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

/**
 * Specifies a graphics object which represents a state in the state diagram displayed in Figure 9 of the PDF
 * specification.
 *
 * @see "ISO 32000-1:2008, 8.2"
 * @see "ISO 32000-1:2008, Figure 9"
 */
interface GraphicsObject {

    boolean isValidOperator(GraphicsOperator operator);

    boolean endsGraphicsObject(GraphicsOperator operator);

    GraphicsObject getNewGraphicsObject(GraphicsOperator operator);
}
