package olap.mdx;

import java.io.PrintWriter;

public class BindingsElement extends AbstractContainerElement<BindingElement> {

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print("WITH ");
    }

    @Override
    protected void writeAfterItem(boolean last, PrintWriter writer) {
        writer.print(" ");
    }
}
