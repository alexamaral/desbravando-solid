package cotuba.md;

import cotuba.builder.CapituloBuilder;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Text;

import java.util.Objects;

public class LevelVisitor extends AbstractVisitor {

    private CapituloBuilder capituloBuilder;

    private LevelVisitor(CapituloBuilder capituloBuilder) {
        this.capituloBuilder = Objects.requireNonNull(capituloBuilder);
    }

    public static LevelVisitor from(CapituloBuilder capitulo){
        return new LevelVisitor(capitulo);
    }

    @Override
    public void visit(Heading heading) {
        if (heading.getLevel() == 1) {
            // capítulo
            String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
            capituloBuilder.comTitulo(tituloDoCapitulo);
        } else if (heading.getLevel() == 2) {
            // seção
        } else if (heading.getLevel() == 3) {
            // título
        }
    }

}
