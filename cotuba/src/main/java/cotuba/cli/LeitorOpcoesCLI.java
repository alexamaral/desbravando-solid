package cotuba.cli;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

class LeitorOpcoesCLI {

    private Path diretorioDosMD;
    private String formato;
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
        String nomeDoFormatoDoEbook = cmd.getOptionValue("format");

        if (nomeDoFormatoDoEbook != null) {
            formato = nomeDoFormatoDoEbook.toLowerCase();
        } else {
            formato = "pdf";
        }
    }

    private void trataArquivoDeSaida(CommandLine cmd) {
        String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");
        if (nomeDoArquivoDeSaidaDoEbook != null) {
            arquivoDeSaida = Paths.get(nomeDoArquivoDeSaidaDoEbook);
        } else {
            arquivoDeSaida = Paths.get("book." + formato.toLowerCase());
        }

        try {
            if (Files.isDirectory(arquivoDeSaida)) {
                // deleta arquivos do diretório recursivamente
                Files.walk(arquivoDeSaida)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } else {
                Files.deleteIfExists(arquivoDeSaida);
            }
        } catch (IOException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private void trataModoVerboso(CommandLine cmd) {
        modoVerboso = cmd.hasOption("verbose");
    }

    public Path getDiretorioDosMD() {
        return diretorioDosMD;
    }

    public String getFormato() {
        return formato;
    }

    public Path getArquivoDeSaida() {
        return arquivoDeSaida;
    }

    public boolean isModoVerboso() {
        return modoVerboso;
    }
}
