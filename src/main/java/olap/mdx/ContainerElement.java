package olap.mdx;

public interface ContainerElement<T extends MdxElement> extends MdxElement {

    ContainerElement<T> add(T element);

    boolean hasElements();

}
