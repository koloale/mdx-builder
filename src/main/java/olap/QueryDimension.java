package olap;

import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;

import java.util.*;

public class QueryDimension {

    private Dimension dimension;

    private Map<String, QueryHierarchy> hierarchies = new HashMap<>();

    public QueryDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public QueryHierarchy addHierarchy(Hierarchy hierarchy) {
        return getOrCreateHierarchy(hierarchy);
    }

    private QueryHierarchy getOrCreateHierarchy(Hierarchy hierarchy) {
        QueryHierarchy queryHierarchy = hierarchies.get(hierarchy.getUniqueName());

        if (queryHierarchy != null) {
            return queryHierarchy;
        }

        queryHierarchy = new QueryHierarchy(hierarchy);

        hierarchies.put(hierarchy.getUniqueName(), queryHierarchy);

        return queryHierarchy;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public Collection<QueryLevel> getLevels() {
        ArrayList<QueryLevel> levels = new ArrayList<>();

        for (QueryHierarchy hierarchy : getHierarchies()) {
            levels.addAll(hierarchy.getLevels());
        }

        return levels;

    }

    public Collection<QueryHierarchy> getHierarchies() {
        return hierarchies.values();
    }

    public QueryLevel addLevel(Level level) {
        return addHierarchy(level.getHierarchy()).addLevel(level);
    }

    public void addMember(Member member) {
        addLevel(member.getLevel()).addMember(member);
    }

    public void addMembersRange(List<Member> range) {
        Iterator<Member> iter = range.iterator();
        if (!iter.hasNext()) {
            return;
        }

        Member firstMember = iter.next();
        while (iter.hasNext()) {
            Member currMember = iter.next();
            if (!currMember.getLevel().equals(firstMember.getLevel())) {
                throw new IllegalStateException("Incorrect range levels");
            }
        }
        addLevel(firstMember.getLevel()).addMembersRange(range);
    }

    public Collection<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();

        for (QueryLevel level : getLevels()) {
            members.addAll(level.getMembers());
        }

        return members;
    }

    public Collection<Level> getEmptyLevels() {
        ArrayList<Level> levels = new ArrayList<>();

        for (QueryLevel queryLevel : getLevels()) {
            if (!queryLevel.hasMembers()) {
                levels.add(queryLevel.getLevel());
            }
        }

        return levels;
    }

    public boolean isEmpty() {
        if (hierarchies.isEmpty()) {
            return true;
        }

        boolean empty = true;

        for (QueryHierarchy hierarchy : hierarchies.values()) {
            if (!hierarchy.isEmpty()) {
                empty = false;
                break;
            }
        }

        return empty;
    }

    public void drainTo(QueryDimension dimension) {
        Iterator<Map.Entry<String, QueryHierarchy>> iterator = hierarchies.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, QueryHierarchy> entry = iterator.next();

            QueryHierarchy hierarchy = entry.getValue();

            if (dimension.hasHierarchy(hierarchy.getHierarchy())) {
                QueryHierarchy toHierarchy = dimension.getHierarchy(hierarchy.getHierarchy());
                toHierarchy.mergeQueryHierarchy(hierarchy);
                iterator.remove();
            }
        }
    }

    private QueryHierarchy getHierarchy(Hierarchy hierarchy) {
        return hierarchies.get(hierarchy.getUniqueName());
    }

    public boolean hasHierarchy(Hierarchy hierarchy) {
        return hierarchies.containsKey(hierarchy.getUniqueName());
    }
}
