/**
 * This module allows you to model a PDF document and then write it out. Start by looking at creating a {@link
 * com.justifiedsolutions.justpdf.api.Document}. Then proceed by adding {@link com.justifiedsolutions.justpdf.api.Metadata}
 * and {@link com.justifiedsolutions.justpdf.api.content.Content} to the Document. Finally, the Document can be written
 * to an {@link java.io.OutputStream}.
 *
 * <H2>Debugging Support</H2>
 * You can set any of the following system properties to help with debugging. The values of the system properties do not
 * matter.
 * <UL>
 * <LI>{@code DrawMargin} - Draws a box on every page at the margin</LI>
 * <LI>{@code DrawCenterLine} - Draws a vertical line thru the center of the page from top to bottom margin</LI>
 * <LI>{@code DrawCellPadding} - Draws a box around the content area of a cell</LI>
 * <LI>{@code DisableContentFilters} - Disables all Filters in Content Streams</LI>
 * </UL>
 */
module com.justifiedsolutions.justpdf {
    requires java.desktop;

    exports com.justifiedsolutions.justpdf.api;
    exports com.justifiedsolutions.justpdf.api.content;
    exports com.justifiedsolutions.justpdf.api.font;
}