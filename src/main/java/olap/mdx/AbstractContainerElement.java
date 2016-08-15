package olap.mdx;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractContainerElement<T extends MdxElement> implements ContainerElement<T> {

    private List<T> elements = new ArrayList<>();

    public ContainerElement<T> add(T element) {
        elements.add(element);
        return this;
    }

    public boolean hasElements() {
        return !elements.isEmpty();
    }

    @Override
    public final void writeTo(PrintWriter writer) {
        writeBefore(writer);

        for (Iterator<T> iterator = elements.iterator(); iterator.hasNext(); ) {

            T element = iterator.next();

            if (filter(element)) {
                writBeforeItem(!iterator.hasNext(), writer);

                writeElement(writer, element);

                writeAfterItem(!iterator.hasNext(), writer);
            }
        }

        writeAfter(writer);
    }

    protected void writeElement(PrintWriter writer, T element) {
        element.writeTo(writer);
    }

    protected boolean filter(T element) {
        return true;
    }

    protected void writeAfter(PrintWriter writer) {
    }

    protected void writeBefore(PrintWriter writer) {
    }

    protected void writBeforeItem(boolean last, PrintWriter writer) {
    }

    protected void writeAfterItem(boolean last, PrintWriter writer) {
    }

}
