package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.content.TextContent;
import com.justifiedsolutions.justpdf.api.font.Font;
import com.justifiedsolutions.justpdf.api.font.PDFFont;

/**
 * A utility class for converting {@link TextContent} into a {@link Paragraph}.
 */
final class TextContentUtility {
    private TextContentUtility() {
    }

    /**
     * Transforms any type of {@link TextContent} into a {@link Paragraph}.
     *
     * @param content the content to transform
     * @return the {@link Paragraph}
     * @throws IllegalArgumentException if an unknown type of TextContent is passed
     */
    static Paragraph getParagraph(TextContent content) {
        if (content == null) {
            return null;
        }
        if (content instanceof Paragraph) {
            return (Paragraph) content;
        } else if (content instanceof Phrase) {
            return toParagraph((Phrase) content);
        } else if (content instanceof Chunk) {
            return toParagraph((Chunk) content);
        } else {
            throw new IllegalArgumentException("Unknown content type: " + content.getClass().getSimpleName());
        }
    }

    /**
     * Initializes the {@link Font} for the specified {@link Paragraph} and all of it's content. If a piece of content
     * does not have a specified font, it is initialzed to the font of the next higher content in the heirarchy.
     *
     * @param paragraph the paragraph to initialize
     */
    static void initializeFonts(Paragraph paragraph) {
        if (paragraph.getFont() == null) {
            paragraph.setFont(new PDFFont());
        }

        for (TextContent content : paragraph.getContent()) {
            if (content instanceof Chunk) {
                initializeFonts((Chunk) content, paragraph.getFont());
            } else if (content instanceof Phrase) {
                initializeFonts((Phrase) content, paragraph.getFont());
            }
        }
    }

    /**
     * Initializes the font for the specified {@link Phrase}, defaulting to specified default {@link Font}.
     *
     * @param phrase      the phrase to initialize
     * @param defaultFont the default font
     */
    static void initializeFonts(Phrase phrase, Font defaultFont) {
        if (phrase.getFont() == null) {
            phrase.setFont(defaultFont);
        }
        for (Chunk chunk : phrase.getChunks()) {
            initializeFonts(chunk, phrase.getFont());
        }
    }

    /**
     * Initializes the {@link Font} for the specified {@link Chunk} if it is not already initialized.
     *
     * @param chunk       the chunk to initialize
     * @param defaultFont the default font
     */
    static void initializeFonts(Chunk chunk, Font defaultFont) {
        if (chunk.getFont() == null) {
            chunk.setFont(defaultFont);
        }
    }

    private static Paragraph toParagraph(Phrase phrase) {
        Paragraph content = new Paragraph();
        content.setFont(phrase.getFont());
        content.setLeading(phrase.getLeading());
        for (Chunk chunk : phrase.getChunks()) {
            content.add(chunk);
        }
        return content;
    }

    private static Paragraph toParagraph(Chunk chunk) {
        Paragraph content = new Paragraph();
        content.add(chunk);
        return content;
    }
}
