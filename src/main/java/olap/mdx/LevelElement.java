package olap.mdx;

public class LevelElement extends MemberWithPostfixElement {

    public static final String POSTFIX = "members";

    public LevelElement(String... path) {
        super(path, POSTFIX);
    }

    public LevelElement(MemberElement member) {
        super(member, POSTFIX);
    }

}
