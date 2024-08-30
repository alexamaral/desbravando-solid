package cotuba.domain;

import java.nio.file.Path;
import java.util.List;

public class Ebook {

    private FormatoEbook formato;
    private Path arquivoDeSaida;
    private List<Capitulo> capitulos;


    public Ebook(FormatoEbook formato, Path arquivoDeSaida, List<Capitulo> capitulos) {
        this.formato = formato;
        this.arquivoDeSaida = arquivoDeSaida;
        this.capitulos = capitulos;
    }

    public boolean isUltimoCapitulo(Capitulo capitulo) {
        return this.capitulos.get(this.capitulos.size() - 1).equals(capitulo);
    }


    public FormatoEbook getFormato() {
        return formato;
    }

    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

}
