package olap.mdx;

import java.io.PrintWriter;

public class AxisElement extends SeparatedContainerElement<MdxExpressionElement> {

    private boolean nonEmpty;
    private String axis;

    public AxisElement(String axis, boolean nonEmpty) {
        super(",");
        this.axis = axis;
        this.nonEmpty = nonEmpty;
    }

    @Override
    protected void writeAfter(PrintWriter writer) {
        writer.print(" ON ");
        writer.print(axis);
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print("\n\t");
        if (nonEmpty) {
            writer.print("NON EMPTY ");
        }
    }

}
