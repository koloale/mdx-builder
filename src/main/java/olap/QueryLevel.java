package olap;

import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryLevel {

    private Level level;

    private Map<String, Member> members = new HashMap<>();

    public QueryLevel(Level level) {
        this.level = level;
    }

    public void addMember(Member member) {
        if (!member.getLevel().equals(level)) {
            throw new IllegalArgumentException("Unexpected level");
        }

        members.put(member.getUniqueName(), member);
    }
    
    public void addMembersRange(List<Member> range) {
        for (Member member : range) {
            addMember(member);
        }
    }

    public Collection<Member> getMembers() {
        return members.values();
    }

    public Level getLevel() {
        return level;
    }

    public boolean hasMembers() {
        return !members.isEmpty();
    }
    
}
