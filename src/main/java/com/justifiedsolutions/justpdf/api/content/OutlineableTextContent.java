package com.justifiedsolutions.justpdf.api.content;

import com.justifiedsolutions.justpdf.api.Outlineable;
import com.justifiedsolutions.justpdf.api.font.Font;

import java.util.Objects;

/**
 * A parent class for {@link TextContent} that is {@link Outlineable}.
 */
public class OutlineableTextContent extends Outlineable implements TextContent {
    private Font font;
    private boolean hyphenate = true;

    /**
     * Default constructor.
     */
    protected OutlineableTextContent() {
        //default
    }

    /**
     * Copy constructor.
     *
     * @param content content to copy.
     */
    protected OutlineableTextContent(OutlineableTextContent content) {
        super(content);
        this.font = content.font;
        this.hyphenate = content.hyphenate;
    }

    @Override
    public Font getFont() {
        return font;
    }

    @Override
    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public boolean isHyphenate() {
        return hyphenate;
    }

    @Override
    public void setHyphenate(boolean hyphenate) {
        this.hyphenate = hyphenate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), font, hyphenate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        OutlineableTextContent that = (OutlineableTextContent) o;
        return hyphenate == that.hyphenate &&
                Objects.equals(font, that.font);
    }
}
