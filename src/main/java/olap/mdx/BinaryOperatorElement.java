package olap.mdx;

import java.io.PrintWriter;

public class BinaryOperatorElement implements MdxExpressionElement {

    private final String operator;
    private final MdxExpressionElement element1;
    private final MdxExpressionElement element2;

    public BinaryOperatorElement(String operator, MdxExpressionElement element1, MdxExpressionElement element2) {
        this.operator = operator;
        this.element1 = element1;
        this.element2 = element2;
    }

    @Override
    public void writeTo(PrintWriter writer) {
        element1.writeTo(writer);
        writer.print(" ");
        writer.print(operator);
        writer.print(" ");
        element2.writeTo(writer);
    }
}
