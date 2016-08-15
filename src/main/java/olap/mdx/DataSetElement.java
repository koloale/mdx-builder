package olap.mdx;

import java.io.PrintWriter;

public class DataSetElement extends SeparatedContainerElement<MdxExpressionElement> implements MdxExpressionElement {

    public DataSetElement(MdxExpressionElement...expression) {
        super(",");
        for (MdxExpressionElement element : expression) {
            add(element);
        }
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print("{");
    }

    @Override
    protected void writeAfter(PrintWriter writer) {
        writer.print("}");
    }

}
