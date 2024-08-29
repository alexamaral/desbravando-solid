package cotuba.application;

import cotuba.domain.Ebook;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class Cotuba {

    private RenderizadorMDParaHTML renderizador;
    private GeradorPDF geradorPDF;
    private GeradorEPUB geradorEPUB;


    public Cotuba(RenderizadorMDParaHTML renderizador, GeradorPDF geradorPDF, GeradorEPUB geradorEPUB) {
        this.renderizador = renderizador;
        this.geradorPDF = geradorPDF;
        this.geradorEPUB = geradorEPUB;
    }

    public void executa(ParametrosCotuba parametros) {

        String formato = parametros.getFormato();
        Path diretorioDosMD = parametros.getDiretorioDosMD();
        Path arquivoDeSaida = parametros.getArquivoDeSaida();

        var capitulos =  renderizador.renderiza(diretorioDosMD);

        var ebook = new Ebook(formato, arquivoDeSaida, capitulos);

        if ("pdf".equals(formato)) {
            geradorPDF.gera(ebook);
        } else if ("epub".equals(formato)) {
            geradorEPUB.gera(ebook);
        } else {
            throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
        }
    }
}
