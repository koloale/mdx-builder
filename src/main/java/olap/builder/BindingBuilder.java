package olap.builder;

import olap.OlapUtils;
import olap.QueryHierarchy;
import olap.RangeMember;
import olap.mdx.*;
import org.olap4j.metadata.Level;
import org.olap4j.metadata.Member;

import java.util.*;

public class BindingBuilder {

    private QueryBuilder queryBuilder;
    private BindingsElement bindingsElement;
    private String name;

    private DataSetElement dataSet = new DataSetElement();

    public BindingBuilder(QueryBuilder queryBuilder, BindingsElement bindingsElement, String name) {
        this.queryBuilder = queryBuilder;
        this.bindingsElement = bindingsElement;
        this.name = name;
    }

    public BindingBuilder expression(MdxExpressionElement expression) {
        dataSet.add(expression);
        return this;
    }

    public BindingBuilder dataSetsCrossJoin(Collection<String> names) {
        MdxExpressionElement dataSetCrossJoinElement = createDataSetCrossJoinElement(names);
        dataSet.add(dataSetCrossJoinElement);
        return this;
    }

    public BindingBuilder dataSetsNonEmptyCrossJoin(Collection<String> names) {
        MdxExpressionElement dataSetCrossJoinElement = createDataSetNonEmptyCrossJoinElement(names);
        dataSet.add(dataSetCrossJoinElement);
        return this;
    }

    private MdxExpressionElement createDataSetCrossJoinElement(Collection<String> names) {
        if (names.isEmpty()) {
            return new MemberElement();
        }

        if (names.size() == 1) {
            return new MemberElement(names.iterator().next());
        }

        CrossJoinElement crossJoin = new CrossJoinElement();
        for (String name : names) {
            crossJoin.add(new MemberElement(name));
        }
        return crossJoin;
    }

    private MdxExpressionElement createDataSetNonEmptyCrossJoinElement(Collection<String> names) {
        if (names.isEmpty()) {
            return new MemberElement();
        }

        if (names.size() == 1) {
            return new MemberElement(names.iterator().next());
        }

        Iterator<String> iterator = names.iterator();

        return createNonEmptyCrossJoinFunction(iterator.next(), iterator);
    }

    private FunctionElement createNonEmptyCrossJoinFunction(String dataSet, Iterator<String> namesIterator) {
        String nextDataSet = namesIterator.next();

        if (namesIterator.hasNext()) {
            FunctionElement crossJoinFunction = createNonEmptyCrossJoinFunction(nextDataSet, namesIterator);
            return new FunctionElement("NONEMPTYCROSSJOIN", new MemberElement(dataSet), crossJoinFunction);
        } else {
            return new FunctionElement("NONEMPTYCROSSJOIN", new MemberElement(dataSet), new MemberElement(nextDataSet));
        }
    }
    public BindingBuilder members(Member...members) {
        return members(Arrays.asList(members));
    }

    public BindingBuilder members(Collection<? extends Member> members) {
        for (Member member : members) {
            if (member instanceof RangeMember) {
                RangeMember range = (RangeMember) member;

                final MemberElement fromElement = new MemberElement(OlapUtils.identifierNodeToPath(range.getFrom()));
                final MemberElement toElement = new MemberElement(OlapUtils.identifierNodeToPath(range.getTo()));

                dataSet.add(new RangeElement(fromElement, toElement));
            } else {
                dataSet.add(new MemberElement(OlapUtils.identifierNodeToPath(member)));
            }
        }

        return this;
    }

    public BindingBuilder levels(Level... levels) {
        return levels(Arrays.asList(levels));
    }

    public BindingBuilder filter(Level level, Collection<Member> members) {
        dataSet.add(createFilterElement(level, members));
        return this;
    }

    private MdxExpressionElement createFilterElement(Level level, Collection<Member> members) {
        DataSetElement membersDataSet = new DataSetElement();

        String memberLevel = members.iterator().next().getLevel().getName();
        for (Member member : members) {
            membersDataSet.add(new MemberElement(OlapUtils.identifierNodeToPath(member)));
        }

        return new FunctionElement("FILTER",
                new LevelElement(OlapUtils.identifierNodeToPath(level)),
                new BinaryOperatorElement("IN",
                        new FunctionElement("ANCESTOR",
                                new CurrentMemberElement(OlapUtils.identifierNodeToPath(level.getHierarchy())),
                                new MemberElement(memberLevel)
                        ),
                        membersDataSet
                )
        );
    }

    public BindingBuilder levels(Collection<Level> levels) {

        for (Level level : levels) {
            if (level != null) {
                dataSet.add(new LevelElement(OlapUtils.identifierNodeToPath(level)));
            }
        }

        return this;
    }

    public BindingBuilder range(List<Member> members) {

        for (Member member : members) {
            dataSet.add(new MemberElement(OlapUtils.identifierNodeToPath(member)));
        }

        return this;
    }

    public BindingBuilder ranges(Map<String, List<Member>> ranges) {

        for (Map.Entry<String, List<Member>> range : ranges.entrySet()) {
            range(range.getValue());
        }

        return this;
    }

    public QueryBuilder end() {

        bindingsElement.add(new BindingElement(name, dataSet));

        return queryBuilder;
    }

    public BindingBuilder limit(String dataSet, Integer limit) {
        if (limit != null && limit > 0) {
            this.dataSet.add(new FunctionElement("HEAD", new MemberElement(dataSet), new ConstantElement<>(limit)));
        } else {
            this.dataSet.add(new MemberElement(dataSet));
        }

        return this;
    }

    public BindingBuilder generate(String allRowsMembersDataSet, Collection<QueryHierarchy> hierarchies) {

        TupleElement tupleElement = new TupleElement();

        for (QueryHierarchy hierarchy : hierarchies) {
            tupleElement.add(new CurrentMemberElement(hierarchy.getHierarchy().getName()));
        }

        this.dataSet.add(new FunctionElement("GENERATE", new MemberElement(allRowsMembersDataSet), new DataSetElement(tupleElement)));

        return this;
    }

    public BindingBuilder exists(String dataSet, Collection<Member> members) {
        DataSetElement membersDataSet = new DataSetElement();
        for (Member member : members) {
            membersDataSet.add(new MemberElement(OlapUtils.identifierNodeToPath(member)));
        }

        this.dataSet.add(new FunctionElement("EXISTS", membersDataSet, new MemberElement(dataSet)));
        return this;
    }

    public BindingBuilder exists(String dataSet, Level level) {
        this.dataSet.add(
                new FunctionElement(
                        "EXISTS",
                        new LevelElement(OlapUtils.identifierNodeToPath(level)),
                        new MemberElement(dataSet)
                )
        );
        return this;
    }

    public BindingBuilder hierarchize(Collection<String> names) {
        DataSetElement namesDataSet = new DataSetElement();
        for (String name : names) {
            namesDataSet.add(new MemberElement(name));
        }

        this.dataSet.add(new FunctionElement("HIERARCHIZE", namesDataSet));
        return this;
    }

    public BindingBuilder rowSets(Collection<String> names) {
        for (String name : names) {
            this.dataSet.add(new MemberElement(name));
        }
        return this;
    }

    public BindingBuilder descendants(Member member, Level level) {
        this.dataSet.add(
                new FunctionElement("DESCENDANTS",
                        new MemberElement(OlapUtils.identifierNodeToPath(member)),
                        new MemberElement(OlapUtils.identifierNodeToPath(level))
                )
        );

        return this;
    }
}
