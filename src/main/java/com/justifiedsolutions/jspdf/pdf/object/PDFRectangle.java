/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.object;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class PDFRectangle implements PDFObject {
    private final int llx, lly, urx, ury;

    public PDFRectangle(int llx, int lly, int urx, int ury) {
        this.llx = llx;
        this.lly = lly;
        this.urx = urx;
        this.ury = ury;
    }

    @Override
    public int hashCode() {
        return Objects.hash(llx, lly, urx, ury);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PDFRectangle that = (PDFRectangle) o;
        return llx == that.llx &&
                lly == that.lly &&
                urx == that.urx &&
                ury == that.ury;
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        PDFArray array = new PDFArray();
        array.add(new PDFInteger(llx));
        array.add(new PDFInteger(lly));
        array.add(new PDFInteger(urx));
        array.add(new PDFInteger(ury));
        array.writeToPDF(pdf);
    }
}
