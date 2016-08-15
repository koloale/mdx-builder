package olap.mdx;

import java.io.PrintWriter;
import java.io.StringWriter;

public class RootElement implements MdxElement {

    private AxisesElement axisesElement;

    private BindingsElement bindingsElement = new BindingsElement();

    private FilterElement filterElement = new FilterElement("*");

    public RootElement(String cube) {
        this.axisesElement = new AxisesElement(cube);
    }

    public BindingsElement getBindingsElement() {
        return bindingsElement;
    }

    public AxisesElement getAxisesElement() {
        return axisesElement;
    }

    public FilterElement getFilterElement() {
        return filterElement;
    }

    public String toMdx() {
        StringWriter out = new StringWriter();

        writeTo(new PrintWriter(out));

        return out.toString();
    }

    @Override
    public void writeTo(PrintWriter writer) {
        if (bindingsElement.hasElements()) {
            bindingsElement.writeTo(writer);
        }

        axisesElement.writeTo(writer);

        if (filterElement.hasElements()) {
            filterElement.writeTo(writer);
        }
    }
}
