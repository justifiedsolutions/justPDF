/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.contents;

import com.justifiedsolutions.jspdf.pdf.object.PDFStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class PDFContentStreamBuilder {

    private final List<GraphicsOperator> operators = new ArrayList<>();
    private final Deque<GraphicsState> graphicsStateStack = new ArrayDeque<>();
    private GraphicsState graphicsState = new GraphicsState();
    private GraphicsObject graphicsObject = new PageDescriptionObject();

    public void addOperator(GraphicsOperator operator) {
        if (!graphicsObject.isValidOperator(operator)) {
            throw new IllegalStateException("This operator is not allowed here.");
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

    public PDFStream getStream() throws IOException {
        if (graphicsStateStack.isEmpty() && (graphicsObject instanceof PageDescriptionObject)) {
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
