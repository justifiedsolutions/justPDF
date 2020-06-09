package com.justifiedsolutions.justpdf.api;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents something that can be linked to from the document outline.
 */
public class Outlineable {
    private final UUID outlineId;
    private String outlineText;

    /**
     * Creates a new Outlineable generating a new unique ID.
     */
    protected Outlineable() {
        outlineId = UUID.randomUUID();
    }

    /**
     * Copies the specified Outlineable.
     *
     * @param outlineable the Outlineable to copy
     */
    protected Outlineable(Outlineable outlineable) {
        this.outlineId = outlineable.outlineId;
        this.outlineText = outlineable.outlineText;
    }

    /**
     * Gets the unique ID for this Outlineable.
     *
     * @return the unique ID
     */
    public UUID getOutlineId() {
        return outlineId;
    }

    /**
     * Gets the text that should be shown in the outline for this Outlineable.
     *
     * @return the outline text
     */
    public String getOutlineText() {
        return outlineText;
    }

    /**
     * Sets the text that should be shown in the outline for this Outlineable.
     *
     * @param outlineText the outline text
     */
    public void setOutlineText(String outlineText) {
        this.outlineText = outlineText;
    }

    @Override
    public int hashCode() {
        return Objects.hash(outlineId, outlineText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Outlineable that = (Outlineable) o;
        return outlineId.equals(that.outlineId) &&
                Objects.equals(outlineText, that.outlineText);
    }
}
