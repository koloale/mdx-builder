package olap.mdx;

import java.io.PrintWriter;

public class MemberElement implements MdxExpressionElement {

    private String[] path;

    public MemberElement(String... path) {
        this.path = path;
    }

    @Override
    public void writeTo(PrintWriter writer) {
        boolean first = true;

        for (String item : path) {
            if (!first) {
                writer.print(".");
            } else {
                first = false;
            }

            writer.print("[");
            writer.print(encodeMdxMember(item));
            writer.print("]");
        }
    }

    private static String encodeMdxMember(String member) {
        return member
                .replaceAll("\\]", "]]");
    }

}
