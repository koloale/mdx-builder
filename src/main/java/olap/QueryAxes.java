package olap;

public class QueryAxes {

    private final QueryAxis rowAxis = new QueryAxis();
    private final QueryAxis colAxis = new QueryAxis();
    private final QueryAxis filterAxis = new QueryAxis();

    public QueryAxis getRowAxis() {
        return rowAxis;
    }

    public QueryAxis getColAxis() {
        return colAxis;
    }

    public QueryAxis getFilterAxis() {
        return filterAxis;
    }

}
