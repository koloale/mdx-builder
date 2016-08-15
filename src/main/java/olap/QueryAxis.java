package olap;

import org.olap4j.metadata.Dimension;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;

import java.util.*;

public class QueryAxis {

    private Map<String, QueryDimension> dimensions = new HashMap<>();

    public QueryLevel addLevel(Level level) {
        return getOrCreateDimension(level.getDimension()).addLevel(level);
    }

    public void addMember(Member member) {
        addLevel(member.getLevel()).addMember(member);
    }

    public void addMembers(List<Member> range) {
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

    private QueryDimension getOrCreateDimension(Dimension dimension) {
        QueryDimension queryDimension = dimensions.get(dimension.getUniqueName());

        if (queryDimension != null) {
            return queryDimension;
        }

        queryDimension = new QueryDimension(dimension);

        dimensions.put(dimension.getUniqueName(), queryDimension);

        return queryDimension;
    }

    public boolean hasDimension(Dimension dimension) {
        return dimensions.containsKey(dimension.getUniqueName());
    }

    public boolean hasHierarchy(QueryHierarchy hierarchy) {
        for (QueryDimension dimension : getDimensions()) {
            if (dimension.getHierarchies().contains(hierarchy.getHierarchy())) {
                return true;
            }
        }
        return false;
    }

    public Collection<QueryDimension> getDimensions() {
        return dimensions.values();
    }

    public Collection<QueryLevel> getLevels() {
        ArrayList<QueryLevel> levels = new ArrayList<>();

        for (QueryDimension dimension : getDimensions()) {
            levels.addAll(dimension.getLevels());
        }

        return levels;
    }

    public Collection<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();

        for (QueryLevel level : getLevels()) {
            members.addAll(level.getMembers());
        }

        return members;
    }

    public void drainTo(QueryAxis axis) {
        Iterator<Map.Entry<String, QueryDimension>> iterator = dimensions.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, QueryDimension> entry = iterator.next();

            QueryDimension dimension = entry.getValue();

            if (axis.hasDimension(dimension.getDimension())) {
                dimension.drainTo(axis.getDimension(dimension.getDimension()));
            }

            if (dimension.isEmpty()) {
                iterator.remove();
            }
        }
    }

    private QueryDimension getDimension(Dimension dimension) {
        return dimensions.get(dimension.getUniqueName());
    }

    public Collection<QueryHierarchy> getHierarchies() {
        ArrayList<QueryHierarchy> hierarchies = new ArrayList<>();

        for (QueryDimension dimension : getDimensions()) {
            hierarchies.addAll(dimension.getHierarchies());
        }

        return hierarchies;
    }
}
