/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.jspdf.pdf.doc;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

class PDFHeader {

    void writeToPDF(OutputStream pdf) throws IOException {
        pdf.write("%PDF-1.7\n".getBytes(StandardCharsets.US_ASCII));
        byte[] bytes = {'%', (byte) 226, (byte) 227, (byte) 207, (byte) 211, '\n'};
        pdf.write(bytes);
    }
}
