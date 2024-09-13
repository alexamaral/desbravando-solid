package br.com.paradizo.tema;

import cotuba.domain.Ebook;
import cotuba.plugin.Plugin;
import org.jsoup.Jsoup;

public class TemaParadizo implements Plugin {

    @Override
    public String aposRenderizacao(String html) {
        System.out.println("Aplicando tema TemaParadizo");
        String htmlComTema = aplicaTema(html);
        return htmlComTema;
    }

    @Override
    public void aposGeracao(Ebook ebook) {

    }

    private String cssDoTema() {
        return FileUtils.getResourceContents("/tema.css");
    }

    private String aplicaTema(String html) {

        var document = Jsoup.parse(html);
        String css = cssDoTema();
        document.select("head")
                .append("<style> " + css + " </style>");

        return document.html();
    }

}
