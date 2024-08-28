package cotuba.md;

import cotuba.domain.Capitulo;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Text;

import java.util.Objects;

public class LevelVisitor extends AbstractVisitor {

    private Capitulo capitulo;

    private LevelVisitor(Capitulo capitulo) {
        this.capitulo = Objects.requireNonNull(capitulo);
    }

    public static LevelVisitor from(Capitulo capitulo){
        return new LevelVisitor(capitulo);
    }

    @Override
    public void visit(Heading heading) {
        if (heading.getLevel() == 1) {
            // capítulo
            String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
            capitulo.setTitulo(tituloDoCapitulo);
        } else if (heading.getLevel() == 2) {
            // seção
        } else if (heading.getLevel() == 3) {
            // título
        }
    }

}
