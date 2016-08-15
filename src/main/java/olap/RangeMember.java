package olap;

import org.olap4j.OlapException;
import org.olap4j.impl.Olap4jUtil;
import org.olap4j.mdx.ParseTreeNode;
import org.olap4j.metadata.*;

import java.util.List;

public class RangeMember implements Member {
    private Member from;
    private Member to;

    public RangeMember(Member from, Member to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Members mustn't be null");
        }

        this.from = from;
        this.to = to;
    }

    public Member getFrom() {
        return from;
    }

    public Member getTo() {
        return to;
    }

    @Override
    public String getName() {
        return from.getName() + ":" + to.getName();
    }

    @Override
    public String getUniqueName() {
        return from.getUniqueName() + ":" + to.getUniqueName();
    }

    @Override
    public NamedList<? extends Member> getChildMembers() throws OlapException {
        return null;
    }

    @Override
    public int getChildMemberCount() throws OlapException {
        return 0;
    }

    @Override
    public Member getParentMember() {
        return null;
    }

    @Override
    public Level getLevel() {
        return from.getLevel();
    }

    @Override
    public Hierarchy getHierarchy() {
        return getLevel().getHierarchy();
    }

    @Override
    public Dimension getDimension() {
        return getHierarchy().getDimension();
    }

    @Override
    public Type getMemberType() {
        return Type.FORMULA;
    }

    @Override
    public boolean isAll() {
        return false;
    }

    @Override
    public boolean isChildOrEqualTo(Member member) {
        return member.getUniqueName().equalsIgnoreCase(this.getUniqueName());
    }

    @Override
    public boolean isCalculated() {
        return false;
    }

    @Override
    public int getSolveOrder() {
        return 0;
    }

    @Override
    public ParseTreeNode getExpression() {
        return null;
    }

    @Override
    public List<Member> getAncestorMembers() {
        return null;
    }

    @Override
    public boolean isCalculatedInQuery() {
        return false;
    }

    @Override
    public Object getPropertyValue(Property property) throws OlapException {
        return null;
    }

    @Override
    public String getPropertyFormattedValue(Property property) throws OlapException {
        return null;
    }

    @Override
    public void setProperty(Property property, Object value) throws OlapException {
    }

    @Override
    public NamedList<Property> getProperties() {
        return Olap4jUtil.emptyNamedList();
    }

    @Override
    public int getOrdinal() {
        return 0;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public int getDepth() {
        return 0;
    }

    @Override
    public Member getDataMember() {
        return null;
    }

    @Override
    public String getCaption() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean isVisible() {
        return false;
    }

}
