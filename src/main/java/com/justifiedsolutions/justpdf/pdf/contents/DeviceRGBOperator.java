package com.justifiedsolutions.justpdf.pdf.contents;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Superclass for operators that work on {@link DeviceRGB} colorspace.
 */
abstract class DeviceRGBOperator implements ColorGraphicsOperator {
    private final DeviceRGB colorSpace;

    /**
     * Creates a new operator that sets the stroke color in the RGB color space in a content stream.
     *
     * @param colorSpace the color space
     */
    protected DeviceRGBOperator(DeviceRGB colorSpace) {
        this.colorSpace = Objects.requireNonNull(colorSpace);
    }

    @Override
    public int hashCode() {
        return Objects.hash(colorSpace);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeviceRGBOperator that = (DeviceRGBOperator) o;
        return colorSpace.equals(that.colorSpace);
    }

    @Override
    public void writeToPDF(OutputStream pdf) throws IOException {
        colorSpace.getRed().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getGreen().writeToPDF(pdf);
        pdf.write(' ');
        colorSpace.getBlue().writeToPDF(pdf);
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
        return colorSpace;
    }

    /**
     * Gets the operator code for writing to a PDF.
     *
     * @return the code
     */
    protected abstract String getOperatorCode();

}
