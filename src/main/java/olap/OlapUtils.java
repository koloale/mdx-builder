package olap;

import org.olap4j.mdx.IdentifierNode;
import org.olap4j.mdx.IdentifierSegment;
import org.olap4j.metadata.MetadataElement;

import java.util.List;

public abstract class OlapUtils {

    public static String[] identifierNodeToPath(IdentifierNode identifierNode) {
        List<IdentifierSegment> segments = identifierNode.getSegmentList();
        String[] result = new String[segments.size()];

        for (int i = 0; i < segments.size(); i++) {
            IdentifierSegment identifierSegment = segments.get(i);
            result[i] = identifierSegment.getName();
        }

        return result;
    }

    public static String[] identifierNodeToPath(String identifierNode) {
        return identifierNodeToPath(IdentifierNode.parseIdentifier(identifierNode));
    }

    public static String[] identifierNodeToPath(MetadataElement element) {
        return identifierNodeToPath(element.getUniqueName());
    }

}
