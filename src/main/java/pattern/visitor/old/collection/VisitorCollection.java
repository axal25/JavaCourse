package pattern.visitor.old.collection;

import pattern.visitor.old.element.*;
import utils.Pair;
import pattern.visitor.old.exporter.Exporter;
import pattern.visitor.old.visitor.A11Visitor;
import pattern.visitor.old.visitor.B111Visitor;
import pattern.visitor.old.visitor.B112Visitor;
import pattern.visitor.old.visitor.I1Visitor;
import pattern.visitor.old.visitor.utils.I1VisitorTypeErased;

import java.util.stream.IntStream;

public class VisitorCollection {
    public final I1 i1 = new I1() {
    };
    public final A11 a11 = new A11() {
    };
    public final B111 b111 = new B111();
    public final B112 b112 = new B112();

    public final Pair<Class<? extends I1>, I1>[] i1s = new Pair[]{
            new Pair(I1TypeErased.I1.class, i1),
            new Pair(I1TypeErased.A11.class, a11),
            new Pair(I1TypeErased.B111.class, b111),
            new Pair(I1TypeErased.B112.class, b112)
    };
    public final Pair<Class<? extends A11>, A11>[] a11s =
            IntStream.range(1, i1s.length).mapToObj(i -> i1s[i]).toArray(Pair[]::new);

    public final Exporter exporter = new Exporter();

    public final I1Visitor i1Visitor = new I1Visitor() {
    };
    public final A11Visitor a11Visitor = new A11Visitor() {
    };
    public final B111Visitor b111Visitor = new B111Visitor();
    public final B112Visitor b112Visitor = new B112Visitor();

    public final Pair<Class<? extends I1Visitor>, I1Visitor>[] i1Visitors = new Pair[]{
            new Pair(I1VisitorTypeErased.I1Visitor.class, i1Visitor),
            new Pair(I1VisitorTypeErased.A11Visitor.class, a11Visitor),
            new Pair(I1VisitorTypeErased.B111Visitor.class, b111Visitor),
            new Pair(I1VisitorTypeErased.B112Visitor.class, b112Visitor)
    };
    public final Pair<Class<? extends A11Visitor>, A11Visitor>[] a11Visitors =
            IntStream.range(1, i1Visitors.length).mapToObj(i -> i1Visitors[i]).toArray(Pair[]::new);
}
