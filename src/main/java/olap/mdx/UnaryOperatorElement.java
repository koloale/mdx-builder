package olap.mdx;

import java.io.PrintWriter;

public class UnaryOperatorElement implements MdxExpressionElement {

    private final String operator;
    private final MdxExpressionElement element;

    public UnaryOperatorElement(String operator, MdxExpressionElement element) {
        this.operator = operator;
        this.element = element;
    }

    @Override
    public void writeTo(PrintWriter writer) {
        writer.print(operator);
        writer.print(" ");
        element.writeTo(writer);
    }
}
