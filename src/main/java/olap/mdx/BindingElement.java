package olap.mdx;

import java.io.PrintWriter;

public class BindingElement implements MdxElement {

    private String name;
    private MdxExpressionElement expression;

    public BindingElement(String name, MdxExpressionElement expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public void writeTo(PrintWriter writer) {
        writer.print("\n\tSET [");
        writer.print(name);
        writer.print("] AS ");
        expression.writeTo(writer);
    }

}
