package cotuba.plugin;

import cotuba.domain.Ebook;

import java.util.ServiceLoader;

public interface Plugin {

    String aposRenderizacao(String html);
    void aposGeracao(Ebook ebook);

    static String renderizou(String html) {
        String htmlModificado = html;
        for (Plugin plugin : ServiceLoader.load(Plugin.class)) {
            htmlModificado = plugin.aposRenderizacao(htmlModificado);
        }
        return htmlModificado;
    }

    static void gerou(Ebook ebook) {
        ServiceLoader.load(Plugin.class)
                .forEach(plugin -> plugin.aposGeracao(ebook));
    }
}
