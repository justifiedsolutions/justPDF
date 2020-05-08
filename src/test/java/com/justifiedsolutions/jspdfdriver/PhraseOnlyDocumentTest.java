package com.justifiedsolutions.jspdfdriver;

import com.justifiedsolutions.jspdf.api.*;
import com.justifiedsolutions.jspdf.api.content.Chunk;
import com.justifiedsolutions.jspdf.api.content.Phrase;
import com.justifiedsolutions.jspdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;

public class PhraseOnlyDocumentTest {

    @Test
    void go() throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
        Phrase content = new Phrase();
        content.setFont(new PDFFont(PDFFont.FontName.HELVETICA, 12));
        content.setLeading(16.5f);

        content.add(new Chunk("Lorum Ipsum", new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 20)));
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Condimentum lacinia quis vel eros. Quis vel eros donec ac. Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi. Elementum tempus egestas sed sed risus pretium quam. Tortor at risus viverra adipiscing at in. In eu mi bibendum neque egestas congue quisque egestas diam. Tortor condimentum lacinia quis vel eros donec ac odio. Varius morbi enim nunc faucibus a pellentesque sit. Eu facilisis sed odio morbi quis commodo odio aenean. Convallis convallis tellus id interdum velit laoreet id. Dolor sit amet consectetur adipiscing elit pellentesque habitant. Sed libero enim sed faucibus turpis in eu mi bibendum. Quis lectus nulla at volutpat diam ut venenatis tellus.");
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add("Convallis a cras semper auctor. Consectetur lorem donec massa sapien faucibus. Viverra nam libero justo laoreet sit amet cursus. Vel pharetra vel turpis nunc eget lorem dolor sed. Pretium quam vulputate dignissim suspendisse in est ante. Ornare arcu odio ut sem nulla. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit. Tortor vitae purus faucibus ornare suspendisse. Blandit massa enim nec dui nunc mattis enim ut. Turpis massa sed elementum tempus egestas sed sed risus. Risus quis varius quam quisque id diam vel. Odio morbi quis commodo odio aenean. Mauris vitae ultricies leo integer malesuada nunc vel risus.");
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add("Purus non enim praesent elementum facilisis. Ornare arcu dui vivamus arcu felis bibendum ut tristique. Adipiscing vitae proin sagittis nisl. A iaculis at erat pellentesque adipiscing. Lorem donec massa sapien faucibus et. Eget est lorem ipsum dolor sit amet. Sed felis eget velit aliquet sagittis id consectetur. Adipiscing tristique risus nec feugiat in fermentum posuere. Fermentum odio eu feugiat pretium nibh ipsum consequat nisl vel. Turpis egestas sed tempus urna et. Mauris rhoncus aenean vel elit scelerisque mauris pellentesque. Lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Suscipit tellus mauris a diam maecenas. Erat velit scelerisque in dictum. Condimentum mattis pellentesque id nibh tortor. Nunc scelerisque viverra mauris in aliquam. Lorem ipsum dolor sit amet consectetur adipiscing elit.");
        content.add(Chunk.LINE_BREAK);
        content.add(Chunk.LINE_BREAK);
        content.add("At elementum eu facilisis sed. Bibendum enim facilisis gravida neque convallis a cras. Elementum sagittis vitae et leo duis ut diam. Amet purus gravida quis blandit turpis. Semper eget duis at tellus at. Aliquam sem et tortor consequat id porta nibh venenatis. Convallis a cras semper auctor neque vitae. Quam elementum pulvinar etiam non quam. Quis blandit turpis cursus in. Rhoncus dolor purus non enim praesent elementum. Quisque egestas diam in arcu cursus euismod quis viverra. Ac turpis egestas maecenas pharetra convallis posuere. Ultrices eros in cursus turpis massa tincidunt. Luctus venenatis lectus magna fringilla urna porttitor rhoncus dolor. Est ultricies integer quis auctor elit sed vulputate mi sit. Sit amet nulla facilisi morbi tempus iaculis urna id volutpat. Egestas pretium aenean pharetra magna ac placerat vestibulum lectus mauris. Sed arcu non odio euismod lacinia at. Posuere urna nec tincidunt praesent semper. Pellentesque nec nam aliquam sem et.");
        document.add(content);

        document.setMetadata(Metadata.TITLE, "PhraseOnlyDocumentTest");
        document.setMetadata(Metadata.AUTHOR, "Jay Burgess");
        document.setMetadata(Metadata.CREATOR, "jsPDF");
        document.setMetadata(Metadata.SUBJECT, "Lorum Ipsum");

        FileOutputStream pdf = new FileOutputStream("/Users/jason/Downloads/PhraseOnlyDocumentTest.pdf");
        document.write(pdf);
    }
}
