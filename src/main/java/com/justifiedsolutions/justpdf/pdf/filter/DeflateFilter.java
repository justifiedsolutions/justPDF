package com.justifiedsolutions.justpdf.pdf.filter;

import com.justifiedsolutions.justpdf.pdf.object.PDFDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.zip.DeflaterOutputStream;

public class DeflateFilter implements PDFFilter {
    private static final PDFName DECODE_NAME = new PDFName("FlateDecode");

    @Override
    public PDFName getDecodeFilterName() {
        return DECODE_NAME;
    }

    @Override
    public PDFDictionary getDecodeFilterParams() {
        return null;
    }

    @Override
    public byte[] filter(byte[] input) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            DeflaterOutputStream deflater = new DeflaterOutputStream(output);
            deflater.write(input);
            deflater.finish();
            return output.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
