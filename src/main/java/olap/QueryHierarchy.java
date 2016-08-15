package olap;

import org.olap4j.metadata.Hierarchy;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;

import java.util.*;

public class QueryHierarchy {

    private Hierarchy hierarchy;

    private Map<String, QueryLevel> levels = new HashMap<>();

    public QueryHierarchy(Hierarchy hierarchy) {
        this.hierarchy = hierarchy;
    }

    public QueryLevel addLevel(Level level) {
        return getOrCreateLevel(level);
    }

    private QueryLevel getOrCreateLevel(Level level) {
        QueryLevel queryLevel = levels.get(level.getUniqueName());

        if (queryLevel != null) {
            return queryLevel;
        }

        queryLevel = new QueryLevel(level);

        levels.put(level.getUniqueName(), queryLevel);

        return queryLevel;
    }

    public Collection<QueryLevel> getLevels() {
        return levels.values();
    }

    public Hierarchy getHierarchy() {
        return hierarchy;
    }

    public boolean hasMembers() {
        return !getMembers().isEmpty();
    }

    public Collection<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();

        for (QueryLevel level : getLevels()) {
            members.addAll(level.getMembers());
        }

        return members;
    }

    public Collection<Member> getDeepestMembers() {
        QueryLevel level = findDeepestLevelWithMembers();

        return level != null ? level.getMembers() : Collections.<Member>emptyList();
    }

    public Level getDeepestEmptyLevel() {
        QueryLevel deepest = null;
        for (QueryLevel level : getLevels()) {
            if (!level.hasMembers() && (deepest == null || deepest.getLevel().getDepth() < level.getLevel().getDepth())) {
                deepest = level;
            }
        }
        return deepest != null ? deepest.getLevel() : null;
    }

    public Level getDeepestLevel() {
        return findDeepestLevel().getLevel();
    }

    private QueryLevel findDeepestLevelWithMembers() {
        QueryLevel deepest = null;
        for (QueryLevel level : getLevels()) {
            if (level.hasMembers()
                    && (deepest == null || deepest.getLevel().getDepth() < level.getLevel().getDepth())) {
               deepest = level;
            }
        }
        return deepest;
    }

    private QueryLevel findDeepestLevel() {
        QueryLevel deepest = null;
        for (QueryLevel level : getLevels()) {
            if (deepest == null || deepest.getLevel().getDepth() < level.getLevel().getDepth()) {
               deepest = level;
            }
        }
        return deepest;
    }

    public boolean isEmpty() {
        if (levels.isEmpty()) {
            return true;
        }

        boolean empty = true;

        for (QueryLevel level : levels.values()) {
            if (level.hasMembers()) {
                empty = false;
                break;
            }
        }

        return empty;
    }

    public void mergeQueryHierarchy(QueryHierarchy hierarchy) {
        for (QueryLevel level : hierarchy.getLevels()) {
            QueryLevel queryLevel = addLevel(level.getLevel());
            for (Member member : level.getMembers()) {
                queryLevel.addMember(member);
            }
        }
    }
}
