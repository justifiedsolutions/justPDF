package com.justifiedsolutions.justpdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Superclass for operators that work on {@link DeviceRGB} colorspace.
 */
abstract class DeviceRGBOperator extends DeviceColorSpaceOperator {

    /**
     * Creates a new operator that sets the stroke color in the RGB color space in a content stream.
     *
     * @param colorSpace the color space
     */
    protected DeviceRGBOperator(DeviceRGB colorSpace) {
        super(colorSpace);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        getColorSpace().getRed().writeToPDF(pdf);
        pdf.write(' ');
        getColorSpace().getGreen().writeToPDF(pdf);
        pdf.write(' ');
        getColorSpace().getBlue().writeToPDF(pdf);
        pdf.write(' ');
        pdf.write(getOperatorCode().getBytes(StandardCharsets.US_ASCII));
        pdf.write('\n');
    }

    /**
     * Gets the {@link DeviceRGB} {@link ColorSpace}.
     *
     * @return the colorspace
     */
    protected DeviceRGB getColorSpace() {
        return (DeviceRGB) getDeviceColorSpace();
    }
}
