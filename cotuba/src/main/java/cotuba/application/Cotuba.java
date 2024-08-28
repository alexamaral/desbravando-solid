package cotuba.application;

import cotuba.epub.GeradorEPUB;
import cotuba.pdf.GeradorPDF;
import cotuba.md.RenderizadorMDParaHTML;
import cotuba.domain.Ebook;

import java.nio.file.Path;

// user case
public class Cotuba {

    public void executa(String formato, Path diretorioDosMD, Path arquivoDeSaida) {

        var renderizador = new RenderizadorMDParaHTML();
        var capitulos =  renderizador.renderiza(diretorioDosMD);

        var ebook = new Ebook(formato, arquivoDeSaida, capitulos);

        if ("pdf".equals(formato)) {
            var geradorPDF = new GeradorPDF();
            geradorPDF.gera(ebook);
        } else if ("epub".equals(formato)) {
            var geradorEPUB = new GeradorEPUB();
            geradorEPUB.gera(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
        }
    }
}
