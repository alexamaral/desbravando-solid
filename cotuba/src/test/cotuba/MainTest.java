package cotuba;

import cotuba.cli.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private static Path PDF_BOOK    = Path.of("book.pdf");
    private static Path EPUB_BOOK   = Path.of("book.epub");
    private static Path OUTPUT_PDF_BOOK   = Path.of("output.pdf");

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws IOException {

        Files.deleteIfExists(PDF_BOOK);
        Files.deleteIfExists(EPUB_BOOK);
        Files.deleteIfExists(OUTPUT_PDF_BOOK);
    }

    @Test
    void shoulConvertToPdf(){

        var parameters = new String[]{"-f", "pdf","-d", "livro-exemplo"};

        Main.main(parameters);

        assertTrue(Files.exists(PDF_BOOK));

    }

    @Test
    void shoulConvertToEpub(){

        var parameters = new String[]{"-f", "epub","-d", "livro-exemplo"};

        Main.main(parameters);

        assertTrue(Files.exists(EPUB_BOOK));

    }

    @Test
    void shoulConvertToPdfWithVerboseMode(){

        var parameters = new String[]{"-f", "pdf","-d", "livro-exemplo", "-v"};

        Main.main(parameters);

        assertTrue(Files.exists(PDF_BOOK));
    }


    @Test
    void shoulConvertToPdfWithOutpufile(){

        var parameters = new String[]{"-f", "epub","-d", "livro-exemplo", "-o", "output.pdf"};

        Main.main(parameters);

        assertTrue(Files.exists(OUTPUT_PDF_BOOK));
    }

    @Test
    void shoulConvertToPdfWithOutParameters(){

        var parameters = new String[]{"-d", "livro-exemplo", "-v"};

        Main.main(parameters);

        assertTrue(Files.exists(PDF_BOOK));
    }

}