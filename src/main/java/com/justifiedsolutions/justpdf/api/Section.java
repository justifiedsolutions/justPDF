/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Content;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.TextContent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A Section displays it's content as follows:
 * <pre>
 *     [SectionNumber] [Title]
 *     [Content]
 *     [SectionNumber.SubsSectionNumber] [SubSection Title]
 *     [SubSection Content]
 *     ...
 * </pre>
 */
public final class Section {

    private final String sectionNumber;
    private final Paragraph title;
    private final List<Content> content = new ArrayList<>();
    private final List<Section> sections = new ArrayList<>();
    private final Outline.Entry entry;
    private boolean startsNewPage;
    private boolean displaySectionNumber = true;

    /**
     * Creates a new Section. Should not be called directly, instead call {@link #addSection(Paragraph)}.
     *
     * @param sectionNumber the section number
     * @param title         the title
     */
    Section(String sectionNumber, Paragraph title, Outline.Entry entry) {
        this.sectionNumber = Objects.requireNonNull(sectionNumber);
        this.title = Objects.requireNonNull(title);
        this.entry = entry;
        this.title.setOutlineText(getDisplayTitle().toString());
    }

    /**
     * Gets the Section number.
     *
     * @return the section number
     */
    public String getSectionNumber() {
        return sectionNumber;
    }

    /**
     * Returns the title of the section.
     *
     * @return the title
     */
    public Paragraph getTitle() {
        return title;
    }

    /**
     * Gets the title of the section as it should be displayed.
     *
     * @return the title
     */
    public Paragraph getDisplayTitle() {
        if (displaySectionNumber) {
            List<TextContent> headerContent = new ArrayList<>();
            headerContent.add(new Chunk(sectionNumber));
            headerContent.add(new Chunk(" "));
            headerContent.addAll(getTitle().getContent());
            return new Paragraph(getTitle(), headerContent);
        }
        return getTitle();
    }

    /**
     * Specifies if the Section should start a new page. The default value is {@code false}.
     *
     * @return true if the section should start a new page
     */
    public boolean isStartsNewPage() {
        return startsNewPage;
    }

    /**
     * Specifies if the Section should start a new page. The default value is {@code false}.
     *
     * @param startsNewPage true if the section should start a new page
     */
    public void setStartsNewPage(boolean startsNewPage) {
        this.startsNewPage = startsNewPage;
    }

    /**
     * Specifies if the section number should be displayed. The default is {@code true}.
     *
     * @return true if the section number should be displayed
     */
    public boolean isDisplaySectionNumber() {
        return displaySectionNumber;
    }

    /**
     * Specifies if the section number should be displayed. The default is {@code true}.
     *
     * @param displaySectionNumber true if the section number should be displayed
     */
    public void setDisplaySectionNumber(boolean displaySectionNumber) {
        this.displaySectionNumber = displaySectionNumber;
        this.title.setOutlineText(getDisplayTitle().toString());
    }

    /**
     * Add {@link Content} to the Section.
     *
     * @param content the content to add
     * @throws NullPointerException if content is null
     */
    public void addContent(Content content) {
        Objects.requireNonNull(content);
        this.content.add(content);
    }

    /**
     * Gets the {@linkplain Collections#unmodifiableList(List) unmodifiable list} of {@link Content} in the Section.
     *
     * @return the content in the section
     */
    public List<Content> getContent() {
        return Collections.unmodifiableList(content);
    }

    /**
     * Creates a Section and adds it as a subsection of this one.
     *
     * @param title the title of the subsection
     * @return the Section
     * @throws NullPointerException if title is {@code null}
     */
    public Section addSection(Paragraph title) {
        Objects.requireNonNull(title);
        int num = sections.size() + 1;
        String snText = sectionNumber + "." + num;
        Section section = new Section(snText, title, entry.createEntry(title));
        sections.add(section);
        return section;
    }

    /**
     * Gets a {@linkplain Collections#unmodifiableList(List) unmodifiable list} of all of the subsections of this
     * Section.
     *
     * @return the list of subsections
     */
    public List<Section> getSections() {
        return Collections.unmodifiableList(sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionNumber, title, content, sections, startsNewPage, displaySectionNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Section section = (Section) o;
        return startsNewPage == section.startsNewPage &&
                displaySectionNumber == section.displaySectionNumber &&
                sectionNumber.equals(section.sectionNumber) &&
                title.equals(section.title) &&
                content.equals(section.content) &&
                sections.equals(section.sections);
    }
}
