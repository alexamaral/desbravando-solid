package cotuba.epub;

import cotuba.application.GeradorEbook;
import cotuba.domain.Ebook;
import cotuba.domain.FormatoEbook;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@Component
public class GeradorEPUB implements GeradorEbook {

    @Override
    public boolean accept(FormatoEbook formato) {
        return FormatoEbook.EPUB.equals(formato);
    }

    @Override
    public void gera(Ebook ebook) {

        var arquivoDeSaida = ebook.arquivoDeSaida();

        var epub = new Book();

        for (var capitulo : ebook.capitulos()) {
            String html = capitulo.conteudoHTML();
            String tituloDoCapitulo = capitulo.titulo();

            epub.addSection(tituloDoCapitulo, new Resource(html.getBytes(), MediatypeService.XHTML));
        }

        var epubWriter = new EpubWriter();

        try {
            epubWriter.write(epub, Files.newOutputStream(arquivoDeSaida));
        } catch (IOException ex) {
            throw new IllegalStateException("Erro ao criar arquivo EPUB: " + arquivoDeSaida.toAbsolutePath(), ex);
        }
    }
}
