package olap.mdx;

import java.io.PrintWriter;

public class ConstantElement<T> implements MdxExpressionElement {

    private T value;

    public ConstantElement(T value) {
        this.value = value;
    }

    @Override
    public void writeTo(PrintWriter writer) {
        writeBefore(writer);
        writeValue(value, writer);
        writeAfter(writer);
    }

    protected void writeValue(T value, PrintWriter writer) {
        writer.print(value);
    }

    protected void writeAfter(PrintWriter writer) {
    }

    protected void writeBefore(PrintWriter writer) {
    }


}
