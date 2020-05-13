package com.justifiedsolutions.jspdfdriver;

import com.justifiedsolutions.jspdf.api.*;
import com.justifiedsolutions.jspdf.api.content.Paragraph;
import com.justifiedsolutions.jspdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SectionDocumentTest {

    @Test
    public void go() throws IOException, DocumentException {
        Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
        document.setMetadata(Metadata.TITLE, "SectionDocumentTest");
        document.setMetadata(Metadata.AUTHOR, "Jay Burgess");
        document.setMetadata(Metadata.CREATOR, "jsPDF");
        document.setMetadata(Metadata.SUBJECT, "Lorum Ipsum");
        PageNumberFooter footer = new PageNumberFooter(false, HorizontalAlignment.RIGHT,
                new PDFFont(PDFFont.FontName.HELVETICA, 10, Color.BLUE));
        document.setFooter(footer);

        document.setHeader(new Header() {
            @Override
            public boolean isValidForPageNumber(int pageNumber) {
                return true;
            }

            @Override
            public Paragraph getParagraph(int pageNumber) {
                Paragraph text = new Paragraph("Lorum Ipsum Paper");
                text.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 14, Color.BLUE));
                text.setAlignment(HorizontalAlignment.CENTER);
                return text;
            }
        });

        Paragraph ps1 = new Paragraph("Section One");
        ps1.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 16));
        ps1.setSpacingBefore(15);

        Paragraph ps2 = new Paragraph("Section Two");
        ps2.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 16));
        ps2.setSpacingBefore(15);

        Paragraph ps3 = new Paragraph("Section Three");
        ps3.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 16));
        ps3.setSpacingBefore(15);

        Section s1 = document.createSection(ps1);
        Section s2 = document.createSection(ps2);
        Section s3 = document.createSection(ps3);

        Paragraph p1 = new Paragraph();
        p1.setFont(new PDFFont(PDFFont.FontName.HELVETICA, 14));
        p1.setAlignment(HorizontalAlignment.JUSTIFIED);
        p1.setLineHeight(1.2f);
        p1.setSpacingBefore(4);
        p1.add("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Condimentum lacinia quis vel eros. Quis vel eros donec ac. Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi. Elementum tempus egestas sed sed risus pretium quam. Tortor at risus viverra adipiscing at in. In eu mi bibendum neque egestas congue quisque egestas diam. Tortor condimentum lacinia quis vel eros donec ac odio. Varius morbi enim nunc faucibus a pellentesque sit. Eu facilisis sed odio morbi quis commodo odio aenean. Convallis convallis tellus id interdum velit laoreet id. Dolor sit amet consectetur adipiscing elit pellentesque habitant. Sed libero enim sed faucibus turpis in eu mi bibendum. Quis lectus nulla at volutpat diam ut venenatis tellus.");
        s1.addContent(p1);

        Paragraph p2 = new Paragraph();
        p2.setFont(new PDFFont(PDFFont.FontName.HELVETICA, 14));
        p2.setAlignment(HorizontalAlignment.JUSTIFIED);
        p2.setLineHeight(1.2f);
        p2.setSpacingBefore(4);
        p2.add("Convallis a cras semper auctor. Consectetur lorem donec massa sapien faucibus. Viverra nam libero justo laoreet sit amet cursus. Vel pharetra vel turpis nunc eget lorem dolor sed. Pretium quam vulputate dignissim suspendisse in est ante. Ornare arcu odio ut sem nulla. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit. Tortor vitae purus faucibus ornare suspendisse. Blandit massa enim nec dui nunc mattis enim ut. Turpis massa sed elementum tempus egestas sed sed risus. Risus quis varius quam quisque id diam vel. Odio morbi quis commodo odio aenean. Mauris vitae ultricies leo integer malesuada nunc vel risus.");
        s2.addContent(p2);

        Paragraph p3 = new Paragraph();
        p3.setFont(new PDFFont(PDFFont.FontName.HELVETICA, 14));
        p3.setAlignment(HorizontalAlignment.JUSTIFIED);
        p3.setLineHeight(1.2f);
        p3.setSpacingBefore(4);
        p3.add("Purus non enim praesent elementum facilisis. Ornare arcu dui vivamus arcu felis bibendum ut tristique. Adipiscing vitae proin sagittis nisl. A iaculis at erat pellentesque adipiscing. Lorem donec massa sapien faucibus et. Eget est lorem ipsum dolor sit amet. Sed felis eget velit aliquet sagittis id consectetur. Adipiscing tristique risus nec feugiat in fermentum posuere. Fermentum odio eu feugiat pretium nibh ipsum consequat nisl vel. Turpis egestas sed tempus urna et. Mauris rhoncus aenean vel elit scelerisque mauris pellentesque. Lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Suscipit tellus mauris a diam maecenas. Erat velit scelerisque in dictum. Condimentum mattis pellentesque id nibh tortor. Nunc scelerisque viverra mauris in aliquam. Lorem ipsum dolor sit amet consectetur adipiscing elit.");
        s3.addContent(p3);

        Paragraph p4 = new Paragraph();
        p4.setFont(new PDFFont(PDFFont.FontName.HELVETICA, 14));
        p4.setAlignment(HorizontalAlignment.JUSTIFIED);
        p4.setLineHeight(1.2f);
        p4.setSpacingBefore(4);
        p4.add("At elementum eu facilisis sed. Bibendum enim facilisis gravida neque convallis a cras. Elementum sagittis vitae et leo duis ut diam. Amet purus gravida quis blandit turpis. Semper eget duis at tellus at. Aliquam sem et tortor consequat id porta nibh venenatis. Convallis a cras semper auctor neque vitae. Quam elementum pulvinar etiam non quam. Quis blandit turpis cursus in. Rhoncus dolor purus non enim praesent elementum. Quisque egestas diam in arcu cursus euismod quis viverra. Ac turpis egestas maecenas pharetra convallis posuere. Ultrices eros in cursus turpis massa tincidunt. Luctus venenatis lectus magna fringilla urna porttitor rhoncus dolor. Est ultricies integer quis auctor elit sed vulputate mi sit. Sit amet nulla facilisi morbi tempus iaculis urna id volutpat. Egestas pretium aenean pharetra magna ac placerat vestibulum lectus mauris. Sed arcu non odio euismod lacinia at. Posuere urna nec tincidunt praesent semper. Pellentesque nec nam aliquam sem et.");
        s3.addContent(p4);

        Paragraph ps11 = new Paragraph("Section One-One");
        ps11.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 16));
        ps11.setSpacingBefore(15);
        Section s11 = s1.addSection(ps11);
        s11.addContent(p3);
        s11.addContent(p4);

        Paragraph ps111 = new Paragraph("Section One-One-One");
        ps111.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 16));
        ps111.setSpacingBefore(15);
        Section s111 = s11.addSection(ps111);
        s111.addContent(p3);
        s111.addContent(p4);

        Paragraph ps31 = new Paragraph("Section Three-One");
        ps31.setFont(new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 16));
        ps31.setSpacingBefore(15);
        Section s31 = s3.addSection(ps31);
        s31.addContent(p1);
        s31.addContent(p2);


        String targetDirectoryName = (String) System.getProperties().get("TargetDirectory");
        File targetDirectory = new File(targetDirectoryName);
        File testOutputDirectory = new File(targetDirectory, "test-output");
        if (testOutputDirectory.isDirectory() || testOutputDirectory.mkdirs()) {
            File outputFile = new File(testOutputDirectory, "SectionDocumentTest.pdf");
            FileOutputStream pdf = new FileOutputStream(outputFile);
            document.write(pdf);
        }
    }
}
