/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFReal;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Implements the PDF command {@code TL} to set the leading in a Text Object in a stream.
 *
 * @see "ISO 32000-1:2008, 9.3.1"
 * @see "ISO 32000-1:2008, 9.3.5"
 */
public final class SetLeading implements TextStateOperator, CollapsableOperator {
    private final PDFReal leading;

    /**
     * Creates a new operator that sets the current leading for the {@link TextObject}.
     *
     * @param leading the new leading
     */
    public SetLeading(PDFReal leading) {
        this.leading = Objects.requireNonNull(leading);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leading);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SetLeading that = (SetLeading) o;
        return leading.equals(that.leading);
    }

    @Override
    public boolean isCollapsable(GraphicsOperator operator) {
        return (operator instanceof SetLeading);
    }

    @Override
    public GraphicsOperator collapse(GraphicsOperator operator) {
        return operator;
    }

    @Override
    public boolean changesState(GraphicsState state) {
        return !leading.equals(state.getLeading());
    }

    @Override
    public void changeState(GraphicsState state) {
        state.setLeading(leading);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        leading.writeToPDF(pdf);
        pdf.write(" TL\n".getBytes(StandardCharsets.US_ASCII));
    }
}
