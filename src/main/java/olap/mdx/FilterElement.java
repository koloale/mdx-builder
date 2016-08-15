package olap.mdx;

import java.io.PrintWriter;

public class FilterElement extends SeparatedContainerElement<MdxExpressionElement> {

    public FilterElement(String elementSeparator) {
        super(elementSeparator);
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print(" WHERE ");
    }

}
