/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.layout.DocumentLayout;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Represents a PDF document. A document can contain {@link Metadata}, {@link Section}s, and {@link Content}. It
 * initialized with both a {@link PageSize} and a {@link Margin}. A Document can contain either a list of {@link
 * Section}s or {@link Content}, such as {@link Paragraph}s, but not both.
 */
public final class Document {

    private final PageSize pageSize;
    private final Margin margin;
    private final Map<Metadata, String> metadata = new EnumMap<>(Metadata.class);
    private final List<Section> sections = new ArrayList<>();
    private final List<Content> content = new ArrayList<>();
    private final Outline outline = new Outline();
    private Header header;
    private Footer footer;

    /**
     * Creates a new instance of a Document with the specified {@link PageSize} and {@link Margin}. These values are
     * fixed throughout the Document.
     *
     * @param pageSize the size of the pages
     * @param margin   the page margins
     * @throws NullPointerException if either argument is {@code null}
     */
    public Document(PageSize pageSize, Margin margin) {
        this.pageSize = Objects.requireNonNull(pageSize);
        this.margin = Objects.requireNonNull(margin);
    }

    /**
     * Returns the {@link PageSize} of the document.
     *
     * @return the PageSize.
     */
    public PageSize getPageSize() {
        return pageSize;
    }

    /**
     * Returns the document {@link Margin}
     *
     * @return the margin
     */
    public Margin getMargin() {
        return margin;
    }

    /**
     * Sets the specific piece of metadata. A {@code null} value will remove the metadata from the document.
     *
     * @param metadata the metadata to set
     * @param value    the value of the metadata. {@code null} removes the metadata
     * @throws NullPointerException if metadata is {@code null}
     */
    public void setMetadata(Metadata metadata, String value) {
        Objects.requireNonNull(metadata);
        if (value != null) {
            this.metadata.put(metadata, value);
        } else {
            this.metadata.remove(metadata);
        }
    }

    /**
     * Gets the metadata value.
     *
     * @param metadata the metadata to get
     * @return the value of the metadata. {@code null} means there is no value for that @{@link Metadata}
     * @throws NullPointerException if metadata is {@code null}
     */
    public String getMetadata(Metadata metadata) {
        Objects.requireNonNull(metadata);
        return this.metadata.get(metadata);
    }

    /**
     * Gets a {@linkplain Collections#unmodifiableMap(Map) unmodifiable map} of the metadata.
     *
     * @return the metadata
     */
    public Map<Metadata, String> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    /**
     * Gets the {@link Header} for the document.
     *
     * @return the document header
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the {@link Header} for the document.
     *
     * @param header the header, {@code null} will remove the header
     */
    public void setHeader(Header header) {
        this.header = header;
    }

    /**
     * Gets the {@link Footer} for the document.
     *
     * @return the document footer
     */
    public Footer getFooter() {
        return footer;
    }

    /**
     * Sets the {@link Footer} for the document.
     *
     * @param footer the footer, {@code null} will remove the footer
     */
    public void setFooter(Footer footer) {
        this.footer = footer;
    }

    /**
     * Gets the document outline.
     *
     * @return the outline
     */
    public Outline getOutline() {
        return outline;
    }

    /**
     * Specifies if the Document has any {@link Section}s.
     *
     * @return true if there is a Section in the Document
     */
    public boolean hasSections() {
        return !sections.isEmpty();
    }

    /**
     * Gets a {@linkplain Collections#unmodifiableList(List) unmodifiable list} of the sections.
     *
     * @return the sections
     */
    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    /**
     * Creates a {@link Section} and adds it to the Document.
     *
     * @param title the title for the Section
     * @return the new chapter
     * @throws DocumentException    if other {@link Content} has already been added to the Document
     * @throws NullPointerException if title is {@code null}
     */
    public Section createSection(Paragraph title) throws DocumentException {
        if (hasContent()) {
            throw new DocumentException(
                    "Unable to create Section with Content already added to Document.");
        }
        Objects.requireNonNull(title);
        int num = sections.size() + 1;
        Section section = new Section(String.valueOf(num), title, outline.createEntry(title));
        sections.add(section);
        return section;
    }

    /**
     * Specifies if the Document has any {@link Content}.
     *
     * @return true if there is Content in the Document
     */
    public boolean hasContent() {
        return !content.isEmpty();
    }

    /**
     * Gets a {@linkplain Collections#unmodifiableList(List) unmodifiable list} of the content.
     *
     * @return the content.
     */
    public List<Content> getContent() {
        return Collections.unmodifiableList(content);
    }

    /**
     * Adds {@link Content} to the document.
     *
     * @param content the content to add
     * @throws DocumentException    if {@link Section}s have already been added to the Document
     * @throws NullPointerException if content is {@code null}
     */
    public void add(Content content) throws DocumentException {
        if (hasSections()) {
            throw new DocumentException(
                    "Unable to add Content with Chapters already added to Document.");
        }
        Objects.requireNonNull(content);
        this.content.add(content);
    }

    /**
     * Writes the contents of the Document to the specified {@link OutputStream}.
     *
     * @param out the OutputStream to write the PDF to
     * @throws NullPointerException  if the OutputStream is {@code null}
     * @throws IOException           if there was a problem writing to the output stream
     * @throws DocumentException     if there was problem laying out the document
     * @throws IllegalStateException if there was no data in the document
     */
    public void write(OutputStream out) throws IOException, DocumentException {
        Objects.requireNonNull(out);
        DocumentLayout layout = new DocumentLayout(this);
        layout.write(out);
    }
}
