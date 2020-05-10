/**
 * This module allows you to model a PDF document and then write it out. Start by looking at
 * creating a {@link com.justifiedsolutions.jspdf.api.Document}. Then proceed by adding {@link
 * com.justifiedsolutions.jspdf.api.Metadata} and {@link com.justifiedsolutions.jspdf.api.content.Content}
 * to the Document. Finally, the Document can be written to an {@link java.io.OutputStream}.
 *
 * <H2>Debugging Support</H2>
 * You can set any of the following system properties to help with debugging. The values of the system properties do not matter.
 * <UL>
 * <LI><code>DrawMargin</code> - Draws a box on every page at the margin</LI>
 * <LI><code>DrawCenterLine</code> - Draws a vertical line thru the center of the page from top to bottom margin</LI>
 * </UL>
 */
module com.justifiedsolutions.jspdf {
    requires java.desktop;

    exports com.justifiedsolutions.jspdf.api;
    exports com.justifiedsolutions.jspdf.api.content;
    exports com.justifiedsolutions.jspdf.api.font;
}