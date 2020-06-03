package com.justifiedsolutions.justpdf.layout;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.PageSize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentLayoutTest {

    @Test
    public void testLayoutEmptyDocument() {
        Margin margin = new Margin(72, 72, 72, 72);
        PageSize pageSize = PageSize.LETTER;
        Document document = new Document(pageSize, margin);
        assertThrows(IllegalStateException.class, () -> new DocumentLayout(document));
    }
}