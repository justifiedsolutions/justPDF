/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.operator.text;

import com.justifiedsolutions.jspdf.pdf.PDFWritable;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A TextObject encompasses a group of {@link TextOperator}s in a PDF stream.
 *
 * @see "ISO 32000-1:2008, 9.4.1"
 */
public class TextObject implements PDFWritable {
    private final List<TextOperator> operators = new ArrayList<>();

    /**
     * Adds an operator to the list to execute. Operators will be executed in the order that they are added to the
     * TextObject. Adding a new operator may not increase the size of the list of operators. If current final {@link
     * TextOperator} in the list {@link TextOperator#isCollapsable(TextOperator)} with the specified operator, they will
     * be collapsed and the result will replace the final operator in the list.
     *
     * @param operator the operator to add
     */
    public void addOperator(TextOperator operator) {
        if (operators.isEmpty()) {
            operators.add(operator);
        } else {
            TextOperator last = operators.get(operators.size() - 1);
            if (last.isCollapsable(operator)) {
                TextOperator newOperator = last.collapse(operator);
                operators.set(operators.size() - 1, newOperator);
            } else {
                operators.add(operator);
            }
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(operators);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextObject that = (TextObject) o;
        return operators.equals(that.operators);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        if (!operators.isEmpty()) {
            pdf.write("BT\n".getBytes(StandardCharsets.US_ASCII));
            for (TextOperator operator : operators) {
                operator.writeToPDF(pdf);
            }
            pdf.write("ET".getBytes(StandardCharsets.US_ASCII));
        }
    }
}
