/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

/**
 * Identifies a {@link GraphicsOperator} that can be collapsed with the proceeding operator.
 */
public interface CollapsableOperator {
    /**
     * Specifies if this {@link GraphicsOperator} and the specified GraphicsOperator can be collapsed into a single
     * operator. In general, two operators can be collapsed if they are of the same type.
     *
     * @param operator the operator that would follow this one in execution
     * @return true if this operator and the specified operator can be collapsed.
     */
    boolean isCollapsable(GraphicsOperator operator);

    /**
     * Collapses this {@link GraphicsOperator} with the specified operator. The new operator is returned. This method
     * should only be called if {@link #isCollapsable(GraphicsOperator)} returns true.
     *
     * @param operator the operator that would follow this one in execution
     * @return the new GraphicsOperator that results from the collapsing of this operator and the specified operator
     */
    GraphicsOperator collapse(GraphicsOperator operator);
}
