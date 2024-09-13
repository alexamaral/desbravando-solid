package cotuba.builder;

import cotuba.domain.Capitulo;

public class CapituloBuilder {

    private String titulo;
    private String conteudoHTML;

    public static CapituloBuilder builder(){
        return new CapituloBuilder();
    }

    public CapituloBuilder comTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public CapituloBuilder comConteudoHTML(String conteudoHTML) {
        this.conteudoHTML = conteudoHTML;
        return this;
    }

    public Capitulo constroi() {
        return new Capitulo(titulo, conteudoHTML);
    }
}
