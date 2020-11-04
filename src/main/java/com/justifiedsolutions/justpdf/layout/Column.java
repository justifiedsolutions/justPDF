/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import java.util.Objects;

/**
 * Models a column in a table. Specifies the index and width of the column.
 */
class Column {
    private final int index;
    private final float preferredWidth;
    private float minWidth;
    private float actualWidth;

    Column(int index, float preferredWidth) {
        this.index = index;
        this.preferredWidth = preferredWidth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Column column = (Column) o;
        return Float.compare(column.index, index) == 0;
    }

    float getPreferredWidth() {
        return preferredWidth;
    }

    float getMinWidth() {
        return minWidth;
    }

    void setMinWidth(float width) {
        this.minWidth = Math.max(this.minWidth, width);
    }

    float getActualWidth() {
        return actualWidth;
    }

    void setActualWidth(float actualWidth) {
        this.actualWidth = actualWidth;
    }
}
