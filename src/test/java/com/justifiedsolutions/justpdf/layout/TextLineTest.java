package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.HorizontalAlignment;
import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import com.justifiedsolutions.justpdf.pdf.contents.GraphicsOperator;
import com.justifiedsolutions.justpdf.pdf.contents.SetWordSpacing;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TextLineTest {

    @Test
    public void append() {
        String text = "technology crossSome hyphenated, some open, some closed. Check the dictionary.";
        Chunk chunk = new Chunk(text);
        chunk.setFont(new PDFFont());

        TextLine line = new TextLine(468, 0, 0);
        line.setAlignment(HorizontalAlignment.JUSTIFIED);
        line.setLeading(0);
        line.setLineHeight(1.2f);
        line.setPreviousLineStart(0);
        Chunk remainder = line.append(chunk);
        assertNull(remainder);
        List<GraphicsOperator> operators = line.getOperators();
        Optional<GraphicsOperator> optional = operators.stream().filter(op -> op instanceof SetWordSpacing).findFirst();
        if (optional.isPresent()) {
            SetWordSpacing wordSpacing = (SetWordSpacing) optional.get();
            float ws = wordSpacing.getWordSpacing().getValue();
            assertEquals(2.42134f, ws, .00001f);
        }
    }

    @Test
    public void append2() {
        String text = "AdjectiveAlways hyphenatetwo-year-old daughter sixty-five-year-old man two-and-a-half-year-old child";
        Chunk chunk = new Chunk(text);
        chunk.setFont(new PDFFont());

        TextLine line = new TextLine(468, 0, 0);
        line.setAlignment(HorizontalAlignment.JUSTIFIED);
        line.setLeading(0);
        line.setLineHeight(1.2f);
        line.setPreviousLineStart(0);
        Chunk remainder = line.append(chunk);
        assertNotNull(remainder);
        List<GraphicsOperator> operators = line.getOperators();
        Optional<GraphicsOperator> optional = operators.stream().filter(op -> op instanceof SetWordSpacing).findFirst();
        if (optional.isPresent()) {
            SetWordSpacing wordSpacing = (SetWordSpacing) optional.get();
            float ws = wordSpacing.getWordSpacing().getValue();
            assertEquals(4.10401f, ws, .00001f);
        }
    }
}