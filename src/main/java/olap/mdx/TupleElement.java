package olap.mdx;

import java.io.PrintWriter;

public class TupleElement extends SeparatedContainerElement<MdxExpressionElement> implements MdxExpressionElement {

    public TupleElement(MdxExpressionElement... expression) {
        super(",");
        for (MdxExpressionElement element : expression) {
            add(element);
        }
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print("(");
    }

    @Override
    protected void writeAfter(PrintWriter writer) {
        writer.print(")");
    }

}
