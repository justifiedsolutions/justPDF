package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.util.Objects;

/**
 * An abstract implementation of an operator that works on a point.
 */
class LocationOperator {
    private final PDFReal x;
    private final PDFReal y;

    /**
     * Creates a new location operator using the specified point.
     *
     * @param x the x value of the point
     * @param y the y value of the point
     */
    protected LocationOperator(PDFReal x, PDFReal y) {
        this.x = Objects.requireNonNull(x);
        this.y = Objects.requireNonNull(y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationOperator that = (LocationOperator) o;
        return x.equals(that.x) &&
                y.equals(that.y);
    }

    /**
     * Gets the x coordinate
     *
     * @return x
     */
    protected PDFReal getX() {
        return x;
    }

    /**
     * Gets the y coordinate
     *
     * @return y
     */
    protected PDFReal getY() {
        return y;
    }
}
