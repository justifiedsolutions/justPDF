/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.doc.PDFPage;
import com.justifiedsolutions.jspdf.pdf.font.PDFFont;
import com.justifiedsolutions.jspdf.pdf.object.PDFName;
import com.justifiedsolutions.jspdf.pdf.object.PDFReal;

import java.util.Objects;

/**
 * Models the state of the graphics in a PDF content stream.
 *
 * @see "ISO 32000-1:2008, 8.4"
 */
class GraphicsState {

    // Graphics State
    private PDFReal lineWidth = new PDFReal(1);
    private LineCapStyle lineCap = LineCapStyle.BUTT_CAP;

    // Color Space State
    private ColorSpace fillColorSpace = new DeviceGray(new PDFReal(0));
    private ColorSpace strokeColorSpace = new DeviceGray(new PDFReal(0));

    // Text State
    private PDFReal characterSpacing = new PDFReal(0);
    private PDFReal wordSpacing = new PDFReal(0);
    private PDFReal leading = new PDFReal(0);
    private PDFName textFont; //no default
    private PDFReal textFontSize; //no default

    /**
     * Default constructor.
     */
    GraphicsState() {
    }

    /**
     * Copy constructor.
     *
     * @param state the state to copy
     */
    GraphicsState(GraphicsState state) {
        this.lineWidth = state.lineWidth;
        this.lineCap = state.lineCap;

        this.fillColorSpace = state.fillColorSpace;
        this.strokeColorSpace = state.strokeColorSpace;

        this.characterSpacing = state.characterSpacing;
        this.wordSpacing = state.wordSpacing;
        this.leading = state.leading;
        this.textFont = state.textFont;
        this.textFontSize = state.textFontSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineWidth, lineCap, fillColorSpace, strokeColorSpace, characterSpacing, wordSpacing, leading, textFont, textFontSize);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GraphicsState that = (GraphicsState) o;
        return lineWidth.equals(that.lineWidth) &&
                lineCap.equals(that.lineCap) &&
                fillColorSpace.equals(that.fillColorSpace) &&
                strokeColorSpace.equals(that.strokeColorSpace) &&
                characterSpacing.equals(that.characterSpacing) &&
                wordSpacing.equals(that.wordSpacing) &&
                leading.equals(that.leading) &&
                Objects.equals(textFont, that.textFont) &&
                Objects.equals(textFontSize, that.textFontSize);
    }

    /**
     * Gets the line width from the graphics state.
     *
     * @return the line width
     */
    PDFReal getLineWidth() {
        return lineWidth;
    }

    /**
     * Sets the line width for the graphics state.
     *
     * @param lineWidth the line width
     */
    void setLineWidth(PDFReal lineWidth) {
        this.lineWidth = Objects.requireNonNull(lineWidth);
    }

    /**
     * Gets the line cap style for the graphics state.
     *
     * @return the line cap style
     */
    LineCapStyle getLineCap() {
        return lineCap;
    }

    /**
     * Sets the line cap style for the graphics state.
     *
     * @param lineCap the line cap style
     */
    void setLineCap(LineCapStyle lineCap) {
        this.lineCap = Objects.requireNonNull(lineCap);
    }

    /**
     * Gets the {@link ColorSpace} for filling paths.
     *
     * @return the ColorSpace
     */
    ColorSpace getFillColorSpace() {
        return fillColorSpace;
    }

    /**
     * Sets the {@link ColorSpace} for filling paths.
     *
     * @param fillColorSpace the ColorSpace
     */
    void setFillColorSpace(ColorSpace fillColorSpace) {
        this.fillColorSpace = Objects.requireNonNull(fillColorSpace);
    }

    /**
     * Gets the {@link ColorSpace} for stroking paths.
     *
     * @return the ColorSpace
     */
    ColorSpace getStrokeColorSpace() {
        return strokeColorSpace;
    }

    /**
     * Sets the {@link ColorSpace} for stroking paths.
     *
     * @param strokeColorSpace the ColorSpace
     */
    void setStrokeColorSpace(ColorSpace strokeColorSpace) {
        this.strokeColorSpace = Objects.requireNonNull(strokeColorSpace);
    }

    /**
     * Gets the character spacing for the graphics state.
     *
     * @return the character spacing
     */
    PDFReal getCharacterSpacing() {
        return characterSpacing;
    }

    /**
     * Sets the character spacing for the graphics state.
     *
     * @param characterSpacing the character spacing
     */
    void setCharacterSpacing(PDFReal characterSpacing) {
        this.characterSpacing = Objects.requireNonNull(characterSpacing);
    }

    /**
     * Gets the word spacing for the graphics state.
     *
     * @return the word spacing
     */
    PDFReal getWordSpacing() {
        return wordSpacing;
    }

    /**
     * Sets the word spacing for the graphics state.
     *
     * @param wordSpacing the word spacing
     */
    void setWordSpacing(PDFReal wordSpacing) {
        this.wordSpacing = Objects.requireNonNull(wordSpacing);
    }

    /**
     * Gets the leading for the graphics state.
     *
     * @return the leading
     */
    PDFReal getLeading() {
        return leading;
    }

    /**
     * Sets the leading for the graphics state.
     *
     * @param leading the leading
     */
    void setLeading(PDFReal leading) {
        this.leading = Objects.requireNonNull(leading);
    }

    /**
     * Gets the {@link PDFName} that identifies a {@link PDFFont} on a {@link PDFPage}.
     *
     * @return the name for the font
     */
    PDFName getTextFont() {
        return textFont;
    }

    /**
     * Sets the {@link PDFName} that identifies a {@link PDFFont} on a {@link PDFPage}.
     *
     * @param textFont the name for the font
     */
    void setTextFont(PDFName textFont) {
        this.textFont = Objects.requireNonNull(textFont);
    }

    /**
     * Gets the size of the current font.
     *
     * @return the font size
     */
    PDFReal getTextFontSize() {
        return textFontSize;
    }

    /**
     * Sets the size of the current font.
     *
     * @param textFontSize the font size
     */
    void setTextFontSize(PDFReal textFontSize) {
        this.textFontSize = Objects.requireNonNull(textFontSize);
    }

}
