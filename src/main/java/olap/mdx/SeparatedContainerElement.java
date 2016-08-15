package olap.mdx;

import java.io.PrintWriter;

public class SeparatedContainerElement<T extends MdxElement> extends AbstractContainerElement<T> implements MdxExpressionElement {
    private String elementSeparator;

    public SeparatedContainerElement(String elementSeparator) {
        this.elementSeparator = elementSeparator;
    }

    @Override
    protected void writeAfterItem(boolean last, PrintWriter writer) {
        if (!last) {
            writer.print(" ");
            writer.print(elementSeparator);
            writer.print(" ");
        }
    }

}
