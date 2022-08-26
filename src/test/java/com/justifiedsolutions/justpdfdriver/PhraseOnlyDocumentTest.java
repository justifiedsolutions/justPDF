/*
 * Copyright 2020 Justified Solutions
 * SPDX-License-Identifier: Apache-2.0
 */

package com.justifiedsolutions.justpdfdriver;

import com.justifiedsolutions.justpdf.api.Document;
import com.justifiedsolutions.justpdf.api.DocumentException;
import com.justifiedsolutions.justpdf.api.Margin;
import com.justifiedsolutions.justpdf.api.Metadata;
import com.justifiedsolutions.justpdf.api.PageSize;
import com.justifiedsolutions.justpdf.api.content.Chunk;
import com.justifiedsolutions.justpdf.api.content.Phrase;
import com.justifiedsolutions.justpdf.api.font.PDFFont;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhraseOnlyDocumentTest {

        @Test
        void go() throws IOException, DocumentException {
                Document document = new Document(PageSize.LETTER, new Margin(72, 72, 72, 72));
                Phrase content = new Phrase();
                content.setFont(new PDFFont(PDFFont.FontName.HELVETICA, 12));
                content.setLeading(16.5f);

                content.add(new Chunk("Lorum Ipsum", new PDFFont(PDFFont.FontName.HELVETICA_BOLD, 20)));
                content.add(Chunk.LINE_BREAK);
                content.add(Chunk.LINE_BREAK);
                content.add(
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Condimentum lacinia quis vel eros. Quis vel eros donec ac. Aenean vel elit scelerisque mauris pellentesque pulvinar pellentesque habitant morbi. Elementum tempus egestas sed sed risus pretium quam. Tortor at risus viverra adipiscing at in. In eu mi bibendum neque egestas congue quisque egestas diam. Tortor condimentum lacinia quis vel eros donec ac odio. Varius morbi enim nunc faucibus a pellentesque sit. Eu facilisis sed odio morbi quis commodo odio aenean. Convallis convallis tellus id interdum velit laoreet id. Dolor sit amet consectetur adipiscing elit pellentesque habitant. Sed libero enim sed faucibus turpis in eu mi bibendum. Quis lectus nulla at volutpat diam ut venenatis tellus.");
                content.add(Chunk.LINE_BREAK);
                content.add(Chunk.LINE_BREAK);
                content.add(
                                "Convallis a cras semper auctor. Consectetur lorem donec massa sapien faucibus. Viverra nam libero justo laoreet sit amet cursus. Vel pharetra vel turpis nunc eget lorem dolor sed. Pretium quam vulputate dignissim suspendisse in est ante. Ornare arcu odio ut sem nulla. Posuere lorem ipsum dolor sit amet consectetur adipiscing elit. Tortor vitae purus faucibus ornare suspendisse. Blandit massa enim nec dui nunc mattis enim ut. Turpis massa sed elementum tempus egestas sed sed risus. Risus quis varius quam quisque id diam vel. Odio morbi quis commodo odio aenean. Mauris vitae ultricies leo integer malesuada nunc vel risus.");
                content.add(Chunk.LINE_BREAK);
                content.add(Chunk.LINE_BREAK);
                content.add(
                                "Purus non enim praesent elementum facilisis. Ornare arcu dui vivamus arcu felis bibendum ut tristique. Adipiscing vitae proin sagittis nisl. A iaculis at erat pellentesque adipiscing. Lorem donec massa sapien faucibus et. Eget est lorem ipsum dolor sit amet. Sed felis eget velit aliquet sagittis id consectetur. Adipiscing tristique risus nec feugiat in fermentum posuere. Fermentum odio eu feugiat pretium nibh ipsum consequat nisl vel. Turpis egestas sed tempus urna et. Mauris rhoncus aenean vel elit scelerisque mauris pellentesque. Lectus vestibulum mattis ullamcorper velit sed ullamcorper morbi tincidunt. Nisl nisi scelerisque eu ultrices vitae auctor eu augue. Suscipit tellus mauris a diam maecenas. Erat velit scelerisque in dictum. Condimentum mattis pellentesque id nibh tortor. Nunc scelerisque viverra mauris in aliquam. Lorem ipsum dolor sit amet consectetur adipiscing elit.");
                content.add(Chunk.LINE_BREAK);
                content.add(Chunk.LINE_BREAK);
                content.add(
                                "At elementum eu facilisis sed. Bibendum enim facilisis gravida neque convallis a cras. Elementum sagittis vitae et leo duis ut diam. Amet purus gravida quis blandit turpis. Semper eget duis at tellus at. Aliquam sem et tortor consequat id porta nibh venenatis. Convallis a cras semper auctor neque vitae. Quam elementum pulvinar etiam non quam. Quis blandit turpis cursus in. Rhoncus dolor purus non enim praesent elementum. Quisque egestas diam in arcu cursus euismod quis viverra. Ac turpis egestas maecenas pharetra convallis posuere. Ultrices eros in cursus turpis massa tincidunt. Luctus venenatis lectus magna fringilla urna porttitor rhoncus dolor. Est ultricies integer quis auctor elit sed vulputate mi sit. Sit amet nulla facilisi morbi tempus iaculis urna id volutpat. Egestas pretium aenean pharetra magna ac placerat vestibulum lectus mauris. Sed arcu non odio euismod lacinia at. Posuere urna nec tincidunt praesent semper. Pellentesque nec nam aliquam sem et.");
                content.add(Chunk.LINE_BREAK);
                content.add(Chunk.LINE_BREAK);
                content.add(
                                "all	Always hyphenate.	all-inclusive study century	Always hyphenate.	twentieth-century   technology "
                                                +
                                                "cross	Some hyphenated, some open, some closed. Check the dictionary.	cross-referenced section "
                                                +
                                                "cross-country skis But: crosscut saw fold	Closed unless formed with numbers of 100 or more.	"
                                                +
                                                "tenfold increase 100-fold increase full	Hyphenated before noun, open after noun.	full-scale drawings The drawings are full scale. half	Most hyphenated, some closed. Check the dictionary."
                                                +
                                                "	half-inch measurement half-baked plan halfway house halfhearted attempt high, low, upper, lower, middle, mid	Most hyphenated before noun, open after noun.	high-volume trading highbrow organization"
                                                +
                                                "   (check the dictionary!) middle-class voters midlife crisis mid-Atlantic region Mideast peace process like	Closed unless root word ends in l or ll or has three syllables or more.	catlike jumping"
                                                +
                                                " ability childlike demeanor cathedral-like façade number + odd	Always hyphenate compounds formed with numbers (words or numerals) plus the word odd.	"
                                                +
                                                "twenty-odd pages 360-odd days four-hundred-odd socks   . . . but, four hundred odd socks number	Always open when used to express a ratio (with the word percent).	"
                                                +
                                                "ten percent increase 100 percent change Number + Unit of Measure	Always hyphenate	three-mile limit two-week vacation 150-yard skid mark Number + Unit of Measure + Adjective	Always hyphenate"
                                                +
                                                "	two-year-old daughter sixty-five-year-old man two-and-a-half-year-old   child twenty-five-foot-high wall But: six year-old girls Number + Number + Unit of Measure + Adjective	Hyphenate the number "
                                                +
                                                "and then the adjective	twenty-four six-inch-long measurements Number + Unit of Measure + Adjective (coming after noun)	Hyphenate the number	a man sixty-five years old (note plural years) No hyphen"
                                                +
                                                "	a wall three meters high Hyphenate the number	twenty-four boys five years old fractional numbers	Hyphenate spelled-out fractions used as adjectives.	two-thirds majority In mixed fractions,"
                                                +
                                                " the whole number is not joined to the fraction by another hyphen.	twenty-one and one-quarter miles four and one-eighth inches numbers	Hyphenate only the numbers from twenty-one through ninety-nine."
                                                +
                                                " All others are open.	twenty-four bottles of beer on the wall two hundred rock stars over, under	Closed unless the word the appears in the compound.	overexposed film underrated basketball team "
                                                +
                                                "over-the-counter stock   market under-the-table deal self	Most hyphenated. Closed if prefix un- is added or suffix added to self.	self-confident applicant self-conscious speaker unselfconscious "
                                                +
                                                "speaker selfish act selfless character trait wide	Always closed unless cumbersome. Cumbersome compounds are hyphenated when they appear before the noun modified and open after the noun modified."
                                                +
                                                "	statewide referendum worldwide legal services university-wide crusade (comes before the noun "
                                                +
                                                "modified)");

                document.add(content);

                document.setMetadata(Metadata.TITLE, "PhraseOnlyDocumentTest");
                document.setMetadata(Metadata.AUTHOR, "Jay Burgess");
                document.setMetadata(Metadata.CREATOR, "jsPDF");
                document.setMetadata(Metadata.SUBJECT, "Lorum Ipsum");

                assertEquals(1, document.getContent().size());

                String targetDirectoryName = System.getProperty("TargetDirectory");
                File targetDirectory = new File(targetDirectoryName);
                File testOutputDirectory = new File(targetDirectory, "test-output");
                if (testOutputDirectory.isDirectory() || testOutputDirectory.mkdirs()) {
                        File outputFile = new File(testOutputDirectory, "PhraseOnlyDocumentTest.pdf");
                        try (OutputStream pdf = Files.newOutputStream(outputFile.toPath())) {
                                document.write(pdf);
                        }
                }
        }
}
