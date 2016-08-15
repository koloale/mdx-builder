package olap.mdx;

import olap.OlapIdentifier;

public class CurrentMemberElement extends MemberWithPostfixElement {

    public static final String POSTFIX = "currentmember";

    public CurrentMemberElement(String... path) {
        super(path, POSTFIX);
    }

    public CurrentMemberElement(OlapIdentifier member) {
        super(member, POSTFIX);
    }

}
