/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.HorizontalAlignment;
import com.justifiedsolutions.justpdf.api.VerticalAlignment;

import java.util.*;

/**
 * A Cell represents a cell in a {@link Table}. A Cell is created by methods on the Table and must be instantiated with
 * the content embedded in the Cell. When adding a Paragraph to a Cell, if the Paragraph has a {@link
 * HorizontalAlignment} set, it will be overridden the HorizontalAlignment of the Cell.
 */
public final class Cell {

    private final List<Border> borders = new ArrayList<>();
    private TextContent content;
    private int rowSpan = 1;
    private int columnSpan = 1;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
    private VerticalAlignment verticalAlignment = VerticalAlignment.TOP;
    private float minimumHeight;
    private float paddingTop = 4;
    private float paddingBottom = 4;
    private float paddingLeft = 4;
    private float paddingRight = 4;
    private float grayFill = 1;
    private float borderWidth = 1;

    /**
     * Creates a cell devoid of content.
     */
    Cell() {
        borders.add(Border.ALL);
    }

    /**
     * Creates a new Cell with the specified content. The {@link TextContent} must be a {@link Phrase} or a {@link
     * Paragraph}.
     *
     * @param content the cell content
     * @throws IllegalArgumentException if the content isn't the correct type.
     */
    Cell(TextContent content) {
        this();
        setContent(content);
    }

    /**
     * Gets the content of the Cell.
     *
     * @return the content
     */
    public TextContent getContent() {
        return content;
    }

    /**
     * Sets the content of the Cell. The {@link TextContent} must be a {@link Phrase} or a {@link Paragraph}.
     *
     * @param content the cell content
     * @throws IllegalArgumentException if the content isn't the correct type.
     */
    public void setContent(TextContent content) {
        if ((content instanceof Phrase) || (content instanceof Paragraph)) {
            this.content = content;
        } else {
            throw new IllegalArgumentException("Invalid content type: " + content.getClass().getSimpleName());
        }
    }

    /**
     * Gets the number of rows high this cell is. The default is 1.
     *
     * @return the row span
     */
    public int getRowSpan() {
        return rowSpan;
    }

    /**
     * Sets the number of rows high the cell is.
     *
     * @param rowSpan the row span
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    /**
     * Gets the number of columns wide the cell is. The default is 1.
     *
     * @return the column span
     */
    public int getColumnSpan() {
        return columnSpan;
    }

    /**
     * Sets the number of columns wide the cell is.
     *
     * @param columnSpan the column span
     */
    public void setColumnSpan(int columnSpan) {
        this.columnSpan = columnSpan;
    }

    /**
     * Gets the {@link HorizontalAlignment} of the contents of the cell. The default is {@link
     * HorizontalAlignment#LEFT}.
     *
     * @return the horizontal alignment
     */
    public HorizontalAlignment getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * Sets the {@link HorizontalAlignment} of the contents of the cell.
     *
     * @param horizontalAlignment the horizontal alignment
     */
    public void setHorizontalAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * Gets the {@link VerticalAlignment} of the contents of the cell. The default is {@link VerticalAlignment#TOP}.
     *
     * @return the vertical alignment
     */
    public VerticalAlignment getVerticalAlignment() {
        return verticalAlignment;
    }

    /**
     * Sets the {@link VerticalAlignment} of the contents of the cell.
     *
     * @param verticalAlignment the vertical alignment
     */
    public void setVerticalAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    /**
     * Gets the minimum height of the cell. The default value is 0.
     *
     * @return the minimum height
     */
    public float getMinimumHeight() {
        return minimumHeight;
    }

    /**
     * Sets the minimum height of the cell.
     *
     * @param minimumHeight the minimum height
     */
    public void setMinimumHeight(float minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    /**
     * Sets the padding for all four sides of the cell. This is a convenience method for setting all four {@code
     * set*Padding} methods individually.
     *
     * @param padding the amount of padding
     */
    public void setPadding(float padding) {
        setPaddingTop(padding);
        setPaddingBottom(padding);
        setPaddingLeft(padding);
        setPaddingRight(padding);
    }

    /**
     * Gets the padding for the top of the cell. The default value is 4.
     *
     * @return the top padding
     */
    public float getPaddingTop() {
        return paddingTop;
    }

    /**
     * Sets the padding for the top of the cell.
     *
     * @param paddingTop top padding
     */
    public void setPaddingTop(float paddingTop) {
        this.paddingTop = paddingTop;
    }

    /**
     * Gets the padding for the bottom of the cell. The default value is 4.
     *
     * @return the bottom padding
     */
    public float getPaddingBottom() {
        return paddingBottom;
    }

    /**
     * Sets the padding for the bottom of the cell.
     *
     * @param paddingBottom bottom padding
     */
    public void setPaddingBottom(float paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    /**
     * Gets the padding for the left side of the cell. The default value is 4.
     *
     * @return the left padding
     */
    public float getPaddingLeft() {
        return paddingLeft;
    }

    /**
     * Sets the padding for the left side of the cell.
     *
     * @param paddingLeft left padding
     */
    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    /**
     * Gets the padding for the right side of the cell. The default value is 4.
     *
     * @return the right padding
     */
    public float getPaddingRight() {
        return paddingRight;
    }

    /**
     * Sets the padding for the right side of the cell.
     *
     * @param paddingRight right padding
     */
    public void setPaddingRight(float paddingRight) {
        this.paddingRight = paddingRight;
    }

    /**
     * Gets the {@linkplain Collections#unmodifiableList(List) unmodifiable list} of {@link Border}s configured for the
     * cell. The default is {@link Border#ALL}.
     *
     * @return the cell borders
     */
    public List<Border> getBorders() {
        return Collections.unmodifiableList(borders);
    }

    /**
     * Sets the {@link Border}s of the Cell. Call this method with no arguments to have no Borders on the Cell.
     *
     * @param borders the borders
     */
    public void setBorders(Border... borders) {
        this.borders.clear();
        List<Border> borderList = Arrays.asList(borders);
        if (borderList.contains(Border.TOP) && borderList.contains(Border.BOTTOM)
                && borderList.contains(Border.LEFT) && borderList.contains(Border.RIGHT)) {
            this.borders.add(Border.ALL);
        } else {
            this.borders.addAll(borderList);
        }
    }

    /**
     * Gets the grey fill for the cell. The values range from 0.0 (Black) to 1.0 (White). The default value is 1.0
     * (White).
     *
     * @return the grey fill
     */
    public float getGrayFill() {
        return grayFill;
    }

    /**
     * Sets the grey fill for the cell. The values range from 0.0 (Black) to 1.0 (White).
     *
     * @param grayFill the grey fill
     * @throws IllegalArgumentException if the value is less than 0.0 or greater than 1.0.
     */
    public void setGrayFill(float grayFill) {
        if (grayFill < 0.0f || grayFill > 1.0f) {
            throw new IllegalArgumentException("Invalid Gray Fill value. Must be between 0.0 and 1.0.");
        }
        this.grayFill = grayFill;
    }

    /**
     * Gets the width of the border line. The default value is 1.0.
     *
     * @return the border width
     */
    public float getBorderWidth() {
        return borderWidth;
    }

    /**
     * Sets the width of the border line.
     *
     * @param borderWidth the border width
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(borders, content, rowSpan, columnSpan, horizontalAlignment, verticalAlignment, minimumHeight, paddingTop, paddingBottom, paddingLeft, paddingRight, grayFill, borderWidth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cell cell = (Cell) o;
        return rowSpan == cell.rowSpan &&
                columnSpan == cell.columnSpan &&
                Float.compare(cell.minimumHeight, minimumHeight) == 0 &&
                Float.compare(cell.paddingTop, paddingTop) == 0 &&
                Float.compare(cell.paddingBottom, paddingBottom) == 0 &&
                Float.compare(cell.paddingLeft, paddingLeft) == 0 &&
                Float.compare(cell.paddingRight, paddingRight) == 0 &&
                Float.compare(cell.grayFill, grayFill) == 0 &&
                Float.compare(cell.borderWidth, borderWidth) == 0 &&
                borders.equals(cell.borders) &&
                Objects.equals(content, cell.content) &&
                horizontalAlignment == cell.horizontalAlignment &&
                verticalAlignment == cell.verticalAlignment;
    }

    /**
     * A Border represents the sides of the {@link Cell} that should have a border.
     */
    public enum Border {
        /**
         * The top border.
         */
        TOP,
        /**
         * The bottom border.
         */
        BOTTOM,
        /**
         * The left border.
         */
        LEFT,
        /**
         * The right border.
         */
        RIGHT,
        /**
         * All borders.
         */
        ALL
    }
}
