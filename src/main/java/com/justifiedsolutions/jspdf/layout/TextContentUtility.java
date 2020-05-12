package com.justifiedsolutions.jspdf.layout;

import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Paragraph;
import com.justifiedsolutions.jspdf.api.content.Phrase;
import com.justifiedsolutions.jspdf.api.content.TextContent;

import java.util.Objects;

class TextContentUtility {

    static Paragraph getParagraph(TextContent content) {
        Objects.requireNonNull(content);
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
