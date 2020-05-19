package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Paragraph;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.content.TextContent;

class TextContentUtility {
    private TextContentUtility() {
    }

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
        content.setFont(chunk.getFont());
        content.add(chunk);
        return content;
    }
}
