/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdf.api;

import java.util.*;

/**
 * Models the outline of a PDF document. To create a viable outline, call {@link Outlineable#setOutlineText(String)} on
 * all items you want represented in the outline. Then, create the outline heirarchy utilizing the {@link
 * #createEntry(Outlineable)} and {@link Entry#createEntry(Outlineable)} methods. You can get the instance of the
 * Outline by calling {@link Document#getOutline()}. These calls do not need to be made in a specific order. Calls to
 * setting the text and creating entries can be interleved in any order.
 */
public class Outline {

    private final List<Entry> entries = new ArrayList<>();

    /**
     * Created automatically by the Document.
     */
    Outline() {
        //do nothing
    }

    /**
     * Gets the list of top level {@link Entry}s in the {@code Outline}.
     *
     * @return the top level entries
     */
    public List<Entry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Creates a new top level entry to the outline. Entries are ordered in the order of creation.
     *
     * @param outlineable the {@link Outlineable} to add
     * @return the new {@link Entry}
     */
    public Entry createEntry(Outlineable outlineable) {
        Entry entry = new Entry(outlineable.getOutlineId());
        entries.add(entry);
        return entry;
    }

    /**
     * An entry in the document outline.
     */
    public static class Entry {
        private final UUID outlineId;
        private final List<Entry> entries = new ArrayList<>();

        private Entry(UUID outlineId) {
            this.outlineId = Objects.requireNonNull(outlineId);
        }

        /**
         * The unique ID that represents the content linked to this entry.
         *
         * @return the unique id
         */
        public UUID getOutlineId() {
            return outlineId;
        }

        /**
         * Gets the list of {@code Entry}s that are children to this entry.
         *
         * @return the child entries
         */
        public List<Entry> getEntries() {
            return Collections.unmodifiableList(entries);
        }

        /**
         * Creates a new child entry for this entry.
         *
         * @param outlineable the {@link Outlineable} to link to this outline entry
         * @return the new child entry
         */
        public Entry createEntry(Outlineable outlineable) {
            Entry entry = new Entry(outlineable.getOutlineId());
            entries.add(entry);
            return entry;
        }

        @Override
        public int hashCode() {
            return Objects.hash(outlineId, entries);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Entry entry = (Entry) o;
            return outlineId.equals(entry.outlineId) &&
                    entries.equals(entry.entries);
        }
    }
}
