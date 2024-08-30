package cotuba.cli;

import cotuba.application.ParametrosCotuba;
import cotuba.domain.FormatoEbook;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;

public class LeitorOpcoesCLI implements ParametrosCotuba {

    private Path diretorioDosMD;
    private FormatoEbook formato;
    private Path arquivoDeSaida;
    private boolean modoVerboso = false;


    public LeitorOpcoesCLI(String[] args) {
        var options = criaOpcoes();
        var cmd = parseDosArgumentos(args, options);
        trataDiretoriosDosMD(cmd);
        trataFormato(cmd);
        trataArquivoDeSaida(cmd);
        trataModoVerboso(cmd);
    }

    private Options criaOpcoes() {
        return new Options()
                .addOption("d", "dir", true, "Diretório que contém os arquivos md. Default: diretório atual.")
                .addOption("f", "format", true,"Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf")
                .addOption("o", "output", true,"Arquivo de saída do ebook. Default: book.{formato}.")
                .addOption("v", "verbose", false,"Habilita modo verboso.");
    }

    private CommandLine parseDosArgumentos(String[] args, Options options) {
        try {
            return new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            new HelpFormatter().printHelp("cotuba", options);
            throw new IllegalArgumentException("Opção inválida", e);
        }
    }

    private void trataDiretoriosDosMD(CommandLine cmd) {
        var nomeDoDiretorioDosMD = cmd.getOptionValue("dir");

        if (nomeDoDiretorioDosMD != null) {
            diretorioDosMD = Paths.get(nomeDoDiretorioDosMD);
            if (!Files.isDirectory(diretorioDosMD)) {
                throw new IllegalArgumentException(nomeDoDiretorioDosMD + " não é um diretório.");
            }
        } else {
            Path diretorioAtual = Paths.get("");
            diretorioDosMD = diretorioAtual;
        }
    }


    private void trataFormato(CommandLine cmd) {
        formato = Optional.ofNullable(cmd.getOptionValue("format"))
                .map(String::toUpperCase)
                .map(FormatoEbook::valueOf)
                .orElse(FormatoEbook.PDF);
    }

    private void trataArquivoDeSaida(CommandLine cmd) {
        String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");
        if (nomeDoArquivoDeSaidaDoEbook != null) {
            arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
        } else {
            var  extencao = "html".equalsIgnoreCase(formato.name()) ? "" : "." + formato.name().toLowerCase();
            arquivoDeSaida = Paths.get("book" + extencao);
        }

        try {
            if (Files.isDirectory(arquivoDeSaida)) {
                // deleta arquivos do diretório recursivamente
                Files.walk(arquivoDeSaida)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            }

            Files.deleteIfExists(arquivoDeSaida);
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void trataModoVerboso(CommandLine cmd) {
        modoVerboso = cmd.hasOption("verbose");
    }

    @Override
    public Path getDiretorioDosMD() {
        return diretorioDosMD;
    }

    @Override
    public FormatoEbook getFormato() {
        return formato;
    }

    @Override
    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    @Override
    public boolean isModoVerboso() {
        return modoVerboso;
    }
}
