package olap;

import org.olap4j.impl.IdentifierParser;
import org.olap4j.mdx.IdentifierNode;
import org.olap4j.mdx.IdentifierSegment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OlapIdentifier {

    private final List<String> segments;
    private final IdentifierNode identifierNode;

    public OlapIdentifier(IdentifierNode identifierNode) {
        this.identifierNode = identifierNode;
        this.segments = convertSegments(identifierNode);
    }

    public OlapIdentifier(List<IdentifierSegment> segments) {
        this(new IdentifierNode(segments));
    }

    public IdentifierNode toIdentifierNode() {
        return identifierNode;
    }

    public String getUniqueName() {
        return toIdentifierNode().toString();
    }

    private List<String> convertSegments(IdentifierNode node) {
        List<IdentifierSegment> segments = node.getSegmentList();
        List<String> result = new ArrayList<>(segments.size());

        for (IdentifierSegment segment : segments) {
            result.add(segment.getName());
        }

        return Collections.unmodifiableList(result);
    }

    public List<String> getSegments() {
        return segments;
    }

    public static OlapIdentifier parse(String member) {
        return new OlapIdentifier(IdentifierNode.parseIdentifier(member));
    }

    public static OlapIdentifier of(String...path) {
        return new OlapIdentifier(IdentifierNode.ofNames(path));
    }

    public OlapIdentifier append(String...path) {
        List<IdentifierSegment> segments = new ArrayList<>(identifierNode.getSegmentList());

        for (String item : path) {
            segments.addAll(IdentifierParser.parseIdentifier(item));
        }

        return new OlapIdentifier(new IdentifierNode(segments));
    }

    public String getLast() {
        return segments.get(segments.size()-1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OlapIdentifier that = (OlapIdentifier) o;

        if (!identifierNode.toString().equals(that.identifierNode.toString())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return identifierNode.toString().hashCode();
    }

    @Override
    public String toString() {
        return getUniqueName();
    }
}
