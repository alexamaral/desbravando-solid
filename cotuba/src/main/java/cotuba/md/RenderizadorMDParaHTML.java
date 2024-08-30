package cotuba.md;

import cotuba.domain.Capitulo;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;


@Component
public class RenderizadorMDParaHTML  {


    public List<Capitulo> renderiza(Path diretorioDosMD) {
        return obtemArquivosMD(diretorioDosMD).stream()
                .map(this::renderizaCapitulo)
                .toList();
    }

    private Capitulo renderizaCapitulo(Path arquivoMD) {
        Capitulo capitulo = new Capitulo();
        Node document = parseDoMD(arquivoMD, capitulo);
        renderizaParaHTML(arquivoMD, capitulo, document);
        return capitulo;
    }

    private List<Path> obtemArquivosMD(Path diretorioDosMD) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");

        try (Stream<Path> arquivosMD = Files.list(diretorioDosMD)) {
            return arquivosMD
                    .filter(matcher::matches)
                    .sorted()
                    .toList();
        } catch (IOException ex) {
            throw new IllegalStateException("Erro tentando encontrar arquivos .md em " + diretorioDosMD.toAbsolutePath(), ex);
        }

    }

    private Node parseDoMD(Path arquivoMD, Capitulo capitulo) {
        try {
            Parser parser = Parser.builder().build();
            var document = parser.parseReader(Files.newBufferedReader(arquivoMD));
            document.accept(LevelVisitor.from(capitulo));
            return document;
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao fazer parse do arquivo " + arquivoMD, ex);
        }
    }

    private void renderizaParaHTML(Path arquivoMD, Capitulo capitulo, Node document) {
        try {
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String html = renderer.render(document);
            capitulo.setConteudoHTML(html);
        } catch (Exception ex) {
            throw new IllegalStateException("Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
        }
    }
}
