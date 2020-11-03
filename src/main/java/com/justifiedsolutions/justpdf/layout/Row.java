/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import java.util.Objects;

/**
 * Models a row in a table. Specifies the index and height of the row.
 */
class Row implements Comparable<Row> {
    private final int index;
    private float height;

    Row(int index) {
        this.index = index;
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
        Row row = (Row) o;
        return index == row.index;
    }

    @Override
    public int compareTo(Row o) {
        return Integer.compare(this.index, o.index);
    }

    float getHeight() {
        return height;
    }

    void setHeight(float height) {
        this.height = Math.max(this.height, height);
    }

    int getIndex() {
        return index;
    }
}
