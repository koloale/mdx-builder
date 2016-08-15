package olap.mdx;

import java.io.PrintWriter;

public class StringElement extends ConstantElement<String> {

    public StringElement(String value) {
        super(value);
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print("'");
    }

    @Override
    protected void writeAfter(PrintWriter writer) {
        writer.print("'");
    }

}
