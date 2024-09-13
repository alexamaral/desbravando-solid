package br.com.cognitio.estatisticas;

import cotuba.domain.Capitulo;
import cotuba.domain.Ebook;
import cotuba.plugin.Plugin;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.text.Normalizer;
import java.util.Iterator;
import java.util.Map;

public class CalculadoraDeEstatisticas implements Plugin {

    record Contagem(String palavra, int ocorrencias) { }


    @Override
    public void aposGeracao(Ebook ebook) {

        var contagemDePalavra = new ContagemDePalavras();

        for (Capitulo capitulo : ebook.capitulos()) {
            String html = capitulo.conteudoHTML();
            Document doc = Jsoup.parse(html);
            String textoDoCapitulo = doc.body().text();

            String textoDoCapituloSemPontuacao = textoDoCapitulo.replaceAll("\\p{Punct}", " ");

            String textoDoCapituloSemAcentos = Normalizer.normalize(textoDoCapituloSemPontuacao, Normalizer.Form.NFD)
                    .replaceAll("[^\\p{ASCII}]", "");

            String[] palavras = textoDoCapituloSemAcentos.split("\\s+");

            for (String palavra : palavras) {
                String emMaiusculas = palavra.toUpperCase();
                contagemDePalavra.adicionaPalavra(emMaiusculas);
            }

            for (Map.Entry<String, Integer> contagem : contagemDePalavra.entrySet()) {
                String palavra = contagem.getKey();
                Integer ocorrencias = contagem.getValue();
                System.out.println(palavra + ": " + ocorrencias);
            }
        }
    }

    @Override
    public String aposRenderizacao(String html) {
        return html;
    }

    public Iterator<Contagem> iterator() {
        Iterator<Map.Entry<String, Integer>> iterator = this.map.entrySet().iterator();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }
            @Override
            public Contagem next() {
                Map.Entry<String, Integer> entry = iterator.next();
                String palavra = entry.getKey();
                int ocorrencias = entry.getValue();
                return new Contagem(palavra, ocorrencias);
            }
        };
    }

}
