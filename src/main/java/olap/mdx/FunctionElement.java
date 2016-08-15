package olap.mdx;

import java.io.PrintWriter;

public class FunctionElement extends SeparatedContainerElement<MdxExpressionElement> implements MdxExpressionElement {

    private final String name;

    public FunctionElement(String name, MdxExpressionElement... parameters) {
        super(",");
        this.name = name;
        for (MdxExpressionElement parameter : parameters) {
            add(parameter);
        }
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print(name);
        writer.print("(");
    }

    @Override
    protected void writeAfter(PrintWriter writer) {
        writer.print(")");
    }

}
