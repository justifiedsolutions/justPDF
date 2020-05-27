/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A Table is a type of {@link Content} that can be added to a PDF {@link Document}.
 */
public final class Table implements Content {

    private final float[] relativeColumnWidths;
    private final List<Cell> cells = new ArrayList<>();
    private boolean keepTogether;
    private float widthPercentage = 80;
    private float spacingBefore;
    private float spacingAfter;
    private float borderWidth = 1;

    /**
     * Creates a Table with {@code relativeColumnWidths.length} number of columns. The widths should be specified as a
     * percentage of the whole, with the values ranging between 0 and 1.
     *
     * @param relativeColumnWidths the relative widths of the columns
     * @throws IllegalArgumentException if an invalid width is passed
     */
    public Table(float[] relativeColumnWidths) {
        this.relativeColumnWidths = Arrays.copyOf(relativeColumnWidths, relativeColumnWidths.length);
        for (float width : this.relativeColumnWidths) {
            if (width < 0 || width > 1) {
                throw new IllegalArgumentException("Widths should be between 0 and 1");
            }
        }
    }

    /**
     * Creates a Table with the specified number of columns.
     *
     * @param numberOfColumns the number of columns
     */
    public Table(int numberOfColumns) {
        relativeColumnWidths = new float[numberOfColumns];
        float widthPercent = (1f / (float) numberOfColumns) * 100f;
        Arrays.fill(relativeColumnWidths, widthPercent);
    }

    /**
     * A copy constructor that copies all attributes of the specified table except for the {@link Cell}s. Those are
     * copied from the specified list.
     *
     * @param table the table to copy
     * @param cells the cells to copy
     */
    public Table(Table table, List<Cell> cells) {
        this.relativeColumnWidths = table.relativeColumnWidths;
        this.keepTogether = table.keepTogether;
        this.widthPercentage = table.widthPercentage;
        this.spacingBefore = table.spacingBefore;
        this.spacingAfter = table.spacingAfter;
        this.cells.addAll(cells);
    }

    /**
     * Gets the number of columns in the Table.
     *
     * @return the number of columns
     */
    public int getNumberOfColumns() {
        return this.relativeColumnWidths.length;
    }

    /**
     * Gets the array of relative column widths.
     *
     * @return column widths
     */
    public float[] getRelativeColumnWidths() {
        return Arrays.copyOf(this.relativeColumnWidths, this.relativeColumnWidths.length);
    }

    /**
     * Specifies whether the entire Table should be kept together on the same page. The default value is {@code false}.
     *
     * @return true if the table should be kept together on the same page
     */
    public boolean isKeepTogether() {
        return keepTogether;
    }

    /**
     * Specifies whether the entire Table should be kept together on the same page.
     *
     * @param keepTogether true if the table should be kept together
     */
    public void setKeepTogether(boolean keepTogether) {
        this.keepTogether = keepTogether;
    }

    /**
     * Gets the percentage of the page width that the table should occupy. A value of 100 would go from left margin to
     * right margin. The default value is {@code 80}.
     *
     * @return the width percentage
     */
    public float getWidthPercentage() {
        return widthPercentage;
    }

    /**
     * Sets the percentage of the page width that the table should occupy. A value of 100 would go from left margin to
     * right margin.
     *
     * @param widthPercentage the width percentage
     */
    public void setWidthPercentage(float widthPercentage) {
        this.widthPercentage = widthPercentage;
    }

    /**
     * Gets the amount of empty space above the table. The default value is {@code 0}.
     *
     * @return the spacing before the table
     */
    public float getSpacingBefore() {
        return spacingBefore;
    }

    /**
     * Sets the amount of empty space above the table.
     *
     * @param spacingBefore the spacing before the table
     */
    public void setSpacingBefore(float spacingBefore) {
        this.spacingBefore = spacingBefore;
    }

    /**
     * Gets the amount of empty space below the table. The default value is {@code 0}.
     *
     * @return the spacing after the table
     */
    public float getSpacingAfter() {
        return spacingAfter;
    }

    /**
     * Sets the amount of empty space below the table.
     *
     * @param spacingAfter the spacing after the table
     */
    public void setSpacingAfter(float spacingAfter) {
        this.spacingAfter = spacingAfter;
    }

    /**
     * Gets the default borderWidth for the cells in the table. This value is assigned to the {@link Cell}s as they are
     * created. Changes to this do not change Cells that have already been created. The default value is {@code 1}.
     *
     * @return the default border width
     */
    public float getBorderWidth() {
        return borderWidth;
    }

    /**
     * Sets the default borderWidth for the cells in this table. This only affects {@code Cells} created after this
     * value is set.
     *
     * @param borderWidth the default border width
     */
    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    /**
     * Creates a new empty {@link Cell} and adds it to the Table.
     *
     * @return a new cell
     */
    public Cell createCell() {
        Cell cell = new Cell();
        cell.setBorderWidth(this.borderWidth);
        cells.add(cell);
        return cell;
    }

    /**
     * Creates a new {@link Cell} with the specified {@link TextContent} and adds it to the Table. The TextContent must
     * be either a {@link Phrase} or a {@link Paragraph}.
     *
     * @param content the content for the cell
     * @return a new cell
     * @throws IllegalArgumentException if content is not the correct type
     */
    public Cell createCell(TextContent content) {
        Cell cell = new Cell(content);
        cell.setBorderWidth(this.borderWidth);
        cells.add(cell);
        return cell;
    }

    /**
     * Gets a {@linkplain Collections#unmodifiableList(List) unmodifiable list} of the {@link Cell}s in this Table.
     *
     * @return the cells
     */
    public List<Cell> getCells() {
        return Collections.unmodifiableList(cells);
    }
}
