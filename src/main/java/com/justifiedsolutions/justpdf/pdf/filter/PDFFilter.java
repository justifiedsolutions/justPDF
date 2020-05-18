package com.justifiedsolutions.justpdf.pdf.filter;

import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;

public interface PDFFilter {

    PDFName getDecodeFilterName();

    PDFDictionary getDecodeFilterParams();

    byte[] filter(byte[] input);
}
