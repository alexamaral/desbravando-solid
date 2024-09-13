package cotuba.application;

import cotuba.domain.Ebook;
import cotuba.domain.FormatoEbook;
import cotuba.md.RenderizadorMDParaHTML;
import cotuba.plugin.Plugin;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;

@Component
public class Cotuba {

    private final List<GeradorEbook> geradoresEbook;
    private RenderizadorMDParaHTML renderizador;

    public Cotuba(List<GeradorEbook> geradoresEbook, RenderizadorMDParaHTML renderizador) {
        this.renderizador = renderizador;
        this.geradoresEbook = geradoresEbook;
    }

    public void executa(ParametrosCotuba parametros) {

        FormatoEbook formato = parametros.getFormato();
        Path diretorioDosMD = parametros.getDiretorioDosMD();
        Path arquivoDeSaida = parametros.getArquivoDeSaida();

        var capitulos =  renderizador.renderiza(diretorioDosMD);

        var ebook = new Ebook(formato, arquivoDeSaida, capitulos);

        GeradorEbook geradorEbook = geradoresEbook.stream()
                .filter(gerador -> gerador.accept(formato))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException( "Formato do ebook inválido: " + formato));

        geradorEbook.gera(ebook);
        Plugin.gerou(ebook);
    }
}
