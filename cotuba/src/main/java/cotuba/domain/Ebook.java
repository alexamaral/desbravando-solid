package cotuba.domain;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

public record Ebook (
        FormatoEbook formato,
        Path arquivoDeSaida,
        List<Capitulo> capitulos) {


    public Ebook {
        capitulos = Collections.unmodifiableList(capitulos);
    }

    public boolean ultimoCapitulo(Capitulo capitulo) {
        return capitulos.get(capitulos.size() - 1).equals(capitulo);
    }

}
