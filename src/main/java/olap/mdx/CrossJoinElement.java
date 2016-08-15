package olap.mdx;

public class CrossJoinElement extends SeparatedContainerElement<MdxExpressionElement> implements MdxExpressionElement {
    public CrossJoinElement(MdxExpressionElement...expression) {
        super("*");
        for (MdxExpressionElement element : expression) {
            add(element);
        }
    }

}
