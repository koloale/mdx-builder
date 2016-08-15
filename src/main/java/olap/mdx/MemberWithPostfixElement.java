package olap.mdx;


import olap.OlapIdentifier;
import olap.OlapUtils;

import java.io.PrintWriter;

public class MemberWithPostfixElement implements MdxExpressionElement {

    private String postfix;
    private MdxExpressionElement member;

    public MemberWithPostfixElement(MdxExpressionElement member, String postfix) {
        this.postfix = postfix;
        this.member = member;
    }

    public MemberWithPostfixElement(OlapIdentifier identifier, String postfix) {
        this(OlapUtils.identifierNodeToPath(identifier.toIdentifierNode()), postfix);
    }

    public MemberWithPostfixElement(String[] path, String postfix) {
        this(new MemberElement(path), postfix);
    }

    @Override
    public void writeTo(PrintWriter writer) {
        member.writeTo(writer);
        writer.print(".");
        writer.print(postfix);
    }

}
