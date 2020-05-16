/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.pdf.contents;

import com.justifiedsolutions.justpdf.pdf.object.PDFStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * A helper used to build a PDF content stream. A content stream is built by adding a series of {@link
 * GraphicsOperator}s by calling the {@link #addOperator(GraphicsOperator)} method. When complete, calling the {@link
 * #getStream()} method will return the actual PDF content stream.
 *
 * @see "ISO 32000-1:2008, 7.8.2"
 */
public class PDFContentStreamBuilder {

    private final List<GraphicsOperator> operators = new ArrayList<>();
    private final Deque<GraphicsState> graphicsStateStack = new ArrayDeque<>();
    private GraphicsState graphicsState = new GraphicsState();
    private GraphicsObject graphicsObject = new PageDescriptionObject();

    /**
     * Checks to see if the stream is empty.
     *
     * @return true if the stream is empty
     */
    public boolean isEmpty() {
        return operators.isEmpty();
    }

    /**
     * Adds the specified {@link GraphicsOperator} to the content stream. It will ensure the operator is valid for the
     * current {@link GraphicsObject}, and possibly collapse the specified operator with the previously specified
     * operator if possible. It may also drop the specified operator if it is a {@link GraphicsStateOperator} and it
     * does not change the current state.
     *
     * @param operator the operator to add
     * @throws IllegalStateException if the specified operator is invalid for the current state
     */
    public void addOperator(GraphicsOperator operator) {
        if (!graphicsObject.isValidOperator(operator)) {
            throw new IllegalStateException(operator.getClass().getSimpleName() + " is not allowed in " + graphicsObject.getClass().getSimpleName());
        }

        if ((operator instanceof PopGraphicsState) && graphicsStateStack.isEmpty()) {
            throw new IllegalStateException("This operator is not allowed here.");
        }

        if (operator instanceof PushGraphicsState) {
            graphicsStateStack.push(graphicsState);
            graphicsState = new GraphicsState(graphicsState);
        }

        if (operator instanceof PopGraphicsState) {
            graphicsState = graphicsStateStack.pop();
        }

        if (!operators.isEmpty()) {
            GraphicsOperator lastOperator = operators.get(operators.size() - 1);
            if (lastOperator instanceof CollapsableOperator) {
                CollapsableOperator co = (CollapsableOperator) lastOperator;
                if (co.isCollapsable(operator)) {
                    operator = co.collapse(operator);
                    operators.remove(operators.size() - 1);
                }

                if (operator == null) {
                    return;
                }
            }
        }

        if (operator instanceof GraphicsStateOperator) {
            GraphicsStateOperator gso = (GraphicsStateOperator) operator;
            if (gso.changesState(graphicsState)) {
                gso.changeState(graphicsState);
            } else {
                return;
            }
        }

        if (graphicsObject.endsGraphicsObject(operator)) {
            graphicsObject = graphicsObject.getNewGraphicsObject(operator);
        }

        operators.add(operator);
    }

    /**
     * Gets the PDF Content Stream defined by the set of operators added to this builder.
     *
     * @return the {@link PDFStream} object that represents the contents
     * @throws IOException           if there was an issue creating the byte stream
     * @throws IllegalStateException if the content stream is not in a valid state.
     */
    public PDFStream getStream() throws IOException {
        if (graphicsStateStack.isEmpty() && (graphicsObject instanceof PageDescriptionObject) && !operators.isEmpty()) {
            return new PDFStream(getByteArray());
        }
        throw new IllegalStateException("Contents are not complete. Cannot create content stream.");
    }

    private byte[] getByteArray() throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        for (GraphicsOperator operator : operators) {
            operator.writeToPDF(bytes);
        }
        return bytes.toByteArray();
    }
}
