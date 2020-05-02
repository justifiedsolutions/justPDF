/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

/**
 * Interface that marks operators for PDF text objects.
 */
public interface TextOperator extends GraphicsOperator {

    /**
     * Specifies if this TextOperator and the specified TextOperator can be collapsed into a single TextOperator. In
     * general, two operators can be collapsed if they are of the same type.
     *
     * @param operator the operator that would follow this one in execution
     * @return true if this operator and the specified operator can be collapsed.
     */
    boolean isCollapsable(TextOperator operator);

    /**
     * Collapses this operator with the specified operator. The new operator is returned. This method should only be
     * called if {@link #isCollapsable(TextOperator)} returns true.
     *
     * @param operator the operator that would follow this one in execution
     * @return the new TextOperator that results from the collapsing of this operator and the specified operator
     */
    TextOperator collapse(TextOperator operator);
}
