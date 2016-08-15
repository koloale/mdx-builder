package olap.builder;

import olap.mdx.ContainerElement;
import olap.mdx.MdxExpressionElement;
import olap.mdx.MemberElement;

import java.util.Arrays;
import java.util.Collection;

public class AxisBuilder {

    private ContainerElement<MdxExpressionElement> axisElement;

    public AxisBuilder(ContainerElement<MdxExpressionElement> axisElement) {
        this.axisElement = axisElement;
    }

    public AxisBuilder dataSets(String... names) {
        return dataSets(Arrays.asList(names));
    }

    public AxisBuilder dataSets(MdxExpressionElement... elements) {
        if (elements.length == 0) {
            throw new IllegalArgumentException();
        }

        for (MdxExpressionElement element : elements) {
            axisElement.add(element);
        }

        return this;
    }

    public AxisBuilder dataSets(Collection<String> names) {
        if (names.isEmpty()) {
            throw new IllegalArgumentException();
        }

        for (String name : names) {
            axisElement.add(new MemberElement(name));
        }

        return this;
    }

}
