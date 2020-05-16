/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import java.util.Objects;

/**
 * A Margin represents how much space between the edge of the page and where content is allowed to be displayed. The
 * distance is measured in pixels.
 */
public final class Margin {

    private final float top;
    private final float bottom;
    private final float left;
    private final float right;

    /**
     * Creates a new Margin.
     *
     * @param top    distance between the top of the page and the content
     * @param bottom distance between the bottom of the page and the content
     * @param left   distance between the left side of the page and the content
     * @param right  distance between the right side of the page and the content
     */
    public Margin(float top, float bottom, float left, float right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the distance between the top of the page and the content.
     *
     * @return the top margin
     */
    public float getTop() {
        return top;
    }

    /**
     * Gets the distance between the bottom of the page and the content.
     *
     * @return the bottom margin
     */
    public float getBottom() {
        return bottom;
    }

    /**
     * Gets the distance between the left side of the page and the content.
     *
     * @return the left margin
     */
    public float getLeft() {
        return left;
    }

    /**
     * Gets the distance between the right side of the page and the content.
     *
     * @return the right margin
     */
    public float getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return Objects.hash(top, bottom, left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        com.justifiedsolutions.justpdf.api.Margin margin = (com.justifiedsolutions.justpdf.api.Margin) o;
        return Float.compare(margin.top, top) == 0 &&
                Float.compare(margin.bottom, bottom) == 0 &&
                Float.compare(margin.left, left) == 0 &&
                Float.compare(margin.right, right) == 0;
    }
}
