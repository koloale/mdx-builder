package olap.mdx;

import java.io.PrintWriter;

public class AxisesElement extends SeparatedContainerElement<AxisElement> implements MdxElement {

    private AxisElement columns = new AxisElement("COLUMNS", false);
    private AxisElement rows = new AxisElement("ROWS", true);
    private String cube;

    public AxisesElement(String cube) {
        super(",");
        this.cube = cube;
        add(columns);
        add(rows);
    }

    public AxisElement getColumns() {
        return columns;
    }

    public AxisElement getRows() {
        return rows;
    }

    @Override
    protected boolean filter(AxisElement element) {
        return element.hasElements();
    }

    @Override
    protected void writeBefore(PrintWriter writer) {
        writer.print("\nSELECT ");
    }

    @Override
    protected void writeAfter(PrintWriter writer) {
        writer.print("\nFROM ");
        new MemberElement(cube).writeTo(writer);
    }

}
