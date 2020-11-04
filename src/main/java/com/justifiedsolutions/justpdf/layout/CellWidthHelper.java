/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Cell;
import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.TextContent;

import java.text.BreakIterator;
import java.util.List;

/**
 * Calculates the minimum width required to display a {@link Cell}.
 */
final class CellWidthHelper {

    /**
     * Prevent instantiation.
     */
    private CellWidthHelper() {
    }

    /**
     * Calculates the minimum width required to display the text and padding for the specified {@link Cell}.
     *
     * @param cell the {@code Cell} to calculate for
     * @return the minimum width
     */
    static float getMinimumWidth(Cell cell) {
        return getMinimumWidth(cell.getContent()) + cell.getPaddingLeft() + cell.getPaddingRight();
    }

    /**
     * Calculates the width of each column based on the minimum widths and the preferred widths. If enough width is
     * available to steal from some columns to compensate for other columns going over, it will re-adjust the widths
     * appropriately. If this is not enough width available in the table for the run-over, then an {@code
     * IllegalStateException} will be thrown.
     *
     * @param columns the columns for the table
     * @throws IllegalStateException if there isn't enough room to present all of the content
     */
    static void calculateColumnWidths(List<Column> columns) {
        float widthRequired = getTotalRequiredExtraWidth(columns);
        if (widthRequired == 0) {
            for (Column column : columns) {
                column.setActualWidth(column.getPreferredWidth());
            }
            return;
        }

        float widthAvailable = getTotalAvailableWidth(columns);
        if (widthRequired > widthAvailable) {
            throw new IllegalStateException("Unable to fit all contents into the width of the table.");
        }

        setWidthsWhereAvailLessThanAvg(columns);
        setWidthsWhereAvailGreaterThanAvg(columns);
        setWidthsWhereMinGreaterThanPreferred(columns);
    }

    private static float getMinimumWidth(TextContent content) {
        float maxWidth = 0f;

        if (content != null) {
            Paragraph paragraph = TextContentUtility.getParagraph(content);
            TextContentUtility.initializeFonts(paragraph);
            for (TextContent textContent : paragraph.getContent()) {
                float width;
                if (textContent instanceof Chunk) {
                    width = getChunkMinWidth((Chunk) textContent);
                } else {
                    width = getMinimumWidth(textContent);
                }
                maxWidth = Math.max(maxWidth, width);
            }
        }

        return maxWidth;
    }

    private static float getChunkMinWidth(Chunk chunk) {
        PDFFontWrapper fontWrapper = PDFFontWrapper.getInstance(chunk.getFont());
        String text = chunk.getText();
        boolean hyphenate = chunk.isHyphenate();

        BreakIterator breaker = BreakIterator.getLineInstance();
        breaker.setText(text);
        float maxWidth = 0f;
        int prevBoundary = 0;
        int boundary = breaker.next();
        while (boundary != BreakIterator.DONE) {
            String substring = text.substring(prevBoundary, boundary).stripTrailing();
            float width;
            if (hyphenate) {
                width = hyphenate(substring, fontWrapper);
            } else {
                width = fontWrapper.getStringWidth(substring);
            }
            maxWidth = Math.max(maxWidth, width);
            prevBoundary = boundary;
            boundary = breaker.next();
        }

        return maxWidth;
    }

    private static float hyphenate(String text, PDFFontWrapper fontWrapper) {
        float result = 0;

        Hyphenator hyphenator = new Hyphenator();
        hyphenator.setText(text);

        int prevBoundary = 0;
        int boundary = hyphenator.first();
        while (boundary != Hyphenator.DONE) {
            String chunk = text.substring(prevBoundary, boundary) + "-";
            float width = fontWrapper.getStringWidth(chunk);
            result = Math.max(result, width);
            prevBoundary = boundary;
            boundary = hyphenator.next();
        }

        if (prevBoundary > 0) {
            String chunk = text.substring(prevBoundary);
            float width = fontWrapper.getStringWidth(chunk);
            result = Math.max(result, width);
        }

        if (result == 0) {
            result = fontWrapper.getStringWidth(text);
        }

        return result;
    }


    /**
     * Counts how many columns have a {@code minWidth} less than their {@code preferredWidth}.
     *
     * @param columns the columns of the table
     * @return the number of columns with available width
     */
    private static int getNumColumnsWithAvailableWidth(List<Column> columns) {
        int result = 0;
        for (Column column : columns) {
            if (columnHasAvailableWidth(column)) {
                result++;
            }
        }
        return result;
    }

    /**
     * Counts the total amount of width across all columns that's available for donating to columns in need.
     *
     * @param columns the columns of the table
     * @return the total width available
     */
    private static float getTotalAvailableWidth(List<Column> columns) {
        float result = 0f;
        for (Column column : columns) {
            if (columnHasAvailableWidth(column)) {
                result += (column.getPreferredWidth() - column.getMinWidth());
            }
        }
        return result;
    }

    /**
     * Counts the total amount of width required above and beyond the preferred widths for columns that have minimum
     * widths that exceed their preferred.
     *
     * @param columns the columns of the table
     * @return the total extra width required
     */
    private static float getTotalRequiredExtraWidth(List<Column> columns) {
        float result = 0f;
        for (Column column : columns) {
            if ((column.getMinWidth() > column.getPreferredWidth()) && (column.getActualWidth() == 0)) {
                result += (column.getMinWidth() - column.getPreferredWidth());
            } else if ((column.getPreferredWidth() > column.getActualWidth()) && (column.getActualWidth() > 0)) {
                float widthTaken = column.getPreferredWidth() - column.getActualWidth();
                result -= widthTaken;
            }
        }
        return result;
    }

    /**
     * Goes through the columns repeatedly and sets the {@code actualWidth} of columns to the {@code minWidth} if their
     * available width is less than the average required.
     *
     * @param columns the columns of the table
     */
    private static void setWidthsWhereAvailLessThanAvg(List<Column> columns) {
        boolean changedColumn;
        do {
            changedColumn = false;
            int numColumnsWithAvailWidth = getNumColumnsWithAvailableWidth(columns);
            if (numColumnsWithAvailWidth > 1) {
                float avgWidth = getTotalRequiredExtraWidth(columns) / (float) numColumnsWithAvailWidth;
                for (Column column : columns) {
                    if (columnHasAvailableWidth(column)) {
                        float availableWidth = column.getPreferredWidth() - column.getMinWidth();
                        if (availableWidth < avgWidth) {
                            column.setActualWidth(column.getMinWidth());
                            changedColumn = true;
                        }
                    }
                }
            }
        } while (changedColumn);
    }

    /**
     * Sets the {@code actualWidth} of columns that have more than the average width to donate.
     *
     * @param columns the columns of the table
     */
    private static void setWidthsWhereAvailGreaterThanAvg(List<Column> columns) {
        int numColumnsWithAvailWidth = getNumColumnsWithAvailableWidth(columns);
        if (numColumnsWithAvailWidth > 0) {
            float avgWidth = getTotalRequiredExtraWidth(columns) / (float) numColumnsWithAvailWidth;
            for (Column column : columns) {
                if (columnHasAvailableWidth(column)) {
                    column.setActualWidth(column.getPreferredWidth() - avgWidth);
                }
            }
        }
    }

    /**
     * Sets the widths of the columns that have a {@code minWidth} that exceeds their {@code preferredWidth}
     *
     * @param columns the columns of the table
     */
    private static void setWidthsWhereMinGreaterThanPreferred(List<Column> columns) {
        for (Column column : columns) {
            if ((column.getPreferredWidth() <= column.getMinWidth()) && (column.getActualWidth() == 0)) {
                column.setActualWidth(column.getMinWidth());
            }
        }
    }

    private static boolean columnHasAvailableWidth(Column column) {
        return (column.getMinWidth() < column.getPreferredWidth()) && (column.getActualWidth() == 0);
    }
}
