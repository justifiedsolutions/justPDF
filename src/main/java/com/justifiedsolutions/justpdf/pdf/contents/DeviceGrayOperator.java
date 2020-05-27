package com.justifiedsolutions.justpdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Superclass for operators that work on {@link DeviceGray} colorspace.
 */
abstract class DeviceGrayOperator extends DeviceColorSpaceOperator {

    /**
     * Creates a new operator that sets the stroke color in the Gray color space in a content stream.
     *
     * @param colorSpace the color space
     */
    protected DeviceGrayOperator(DeviceGray colorSpace) {
        super(colorSpace);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        getColorSpace().getGray().writeToPDF(pdf);
        pdf.write(' ');
        pdf.write(getOperatorCode().getBytes(StandardCharsets.US_ASCII));
        pdf.write('\n');
    }

    /**
     * Gets the {@link DeviceRGB} {@link ColorSpace}.
     *
     * @return the colorspace
     */
    protected DeviceGray getColorSpace() {
        return (DeviceGray) getDeviceColorSpace();
    }
}
