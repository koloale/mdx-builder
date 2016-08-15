package olap.builder;


import olap.mdx.RootElement;

public class QueryBuilder {

    private RootElement root;

    public QueryBuilder(String cube) {
        this.root = new RootElement(cube);
    }

    public BindingBuilder binding(String name) {
        return new BindingBuilder(this, root.getBindingsElement(), name);
    }

    public String toString() {
        return root.toMdx();
    }

    public AxisBuilder columns() {
        return new AxisBuilder(root.getAxisesElement().getColumns());
    }

    public AxisBuilder rows() {
        return new AxisBuilder(root.getAxisesElement().getRows());
    }

    public AxisBuilder filter() {
        return new AxisBuilder(root.getFilterElement());
    }
}
