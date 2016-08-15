package olap.mdx;

import java.io.PrintWriter;

public class RangeElement implements MdxExpressionElement {

    private final MemberElement from;
    private final MemberElement to;

    public RangeElement(MemberElement from, MemberElement to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public void writeTo(PrintWriter writer) {
        from.writeTo(writer);
        writer.write(":");
        to.writeTo(writer);
    }
}
