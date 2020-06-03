package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Outline;
import com.justifiedsolutions.justpdf.api.Outlineable;
import com.justifiedsolutions.justpdf.pdf.doc.PDFDocument;
import com.justifiedsolutions.justpdf.pdf.doc.PDFOutlineDictionary;
import com.justifiedsolutions.justpdf.pdf.object.PDFIndirectObject;

import java.util.*;

/**
 * Translates the API outline to a {@link PDFOutlineDictionary}.
 */
final class OutlineLayout {

    private final Outline outlineModel;
    private final PDFDocument pdfDocument;
    private final Set<UUID> outlineIds = new HashSet<>();
    private final Map<UUID, ContentLocation> locationMap = new HashMap<>();

    /**
     * Creates a new OutlineLayout
     *
     * @param outlineModel the model of the document outline
     * @param pdfDocument  the PDF document
     */
    OutlineLayout(Outline outlineModel, PDFDocument pdfDocument) {
        this.outlineModel = outlineModel;
        this.pdfDocument = pdfDocument;
        buildOutlineContent();
    }

    /**
     * Gets the set of unique IDs for content linked to by the outline
     *
     * @return the outline IDs
     */
    Set<UUID> getOutlineIds() {
        return Collections.unmodifiableSet(outlineIds);
    }

    /**
     * This method should be called after adding all content to the document but before writing it out. It finializes
     * the {@link PDFOutlineDictionary}.
     */
    void populatePDFOutlineDictionary() {
        if (!outlineModel.getEntries().isEmpty()) {
            PDFOutlineDictionary outlineDictionary = pdfDocument.getOutline();
            for (Outline.Entry entry : outlineModel.getEntries()) {
                createTopLevelOutlineItems(outlineDictionary, entry);
            }
        }
    }

    /**
     * This method should be called as content is processed and added to the document.
     *
     * @param content the content being added
     * @param page    the reference to the page it's being added to
     * @param top     the vertical position on the page that it's being added to
     */
    void setContentLocation(Outlineable content, PDFIndirectObject.Reference page, float top) {
        if (outlineIds.contains(content.getOutlineId())) {
            ContentLocation location = new ContentLocation(page, top, content.getOutlineText());
            locationMap.put(content.getOutlineId(), location);
        } else {
            throw new IllegalArgumentException("Content is not an entry in outline.");
        }
    }

    private void buildOutlineContent() {
        for (Outline.Entry entry : outlineModel.getEntries()) {
            buildOutlineContent(entry);
        }
    }

    private void buildOutlineContent(Outline.Entry entry) {
        outlineIds.add(entry.getOutlineId());
        for (Outline.Entry child : entry.getEntries()) {
            buildOutlineContent(child);
        }
    }

    private void createTopLevelOutlineItems(PDFOutlineDictionary pdfOutline, Outline.Entry entry) {
        ContentLocation location = locationMap.get(entry.getOutlineId());
        if (location == null) {
            throw new IllegalStateException("Cannot complete outline. No location for content.");
        }
        PDFOutlineDictionary.Item item = pdfOutline.createItem(location.text, location.pageReference, location.top);
        createOutlineItem(item, entry);
    }

    private void createOutlineItem(PDFOutlineDictionary.Item pdfOutlineItem, Outline.Entry entry) {
        for (Outline.Entry child : entry.getEntries()) {
            ContentLocation location = locationMap.get(child.getOutlineId());
            if (location == null) {
                throw new IllegalStateException("Cannot complete outline. No location for content.");
            }
            PDFOutlineDictionary.Item item = pdfOutlineItem.createChild(location.text, location.pageReference, location.top);
            createOutlineItem(item, child);
        }
    }

    /**
     * Identifies the destination in the document that an outline item links to, as well as the outline text.
     */
    private static final class ContentLocation {
        private final PDFIndirectObject.Reference pageReference;
        private final float top;
        private final String text;

        private ContentLocation(PDFIndirectObject.Reference pageReference, float top, String text) {
            this.pageReference = pageReference;
            this.top = top;
            this.text = text;
        }
    }
}
