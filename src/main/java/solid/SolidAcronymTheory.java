package solid;

import solid.example.*;
import utils.ClassMethodUtils;
import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class SolidAcronymTheory {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    private final Definitions definitions;

    private SolidAcronymTheory() {
        definitions = getDefinitions();
    }

    public static void main(String[] args) {
        // TODO: Remove
        main();
    }

    public static void main() {
        staticOrMainMethodUtils.printMethodSignature("main");
        System.out.println(new SolidAcronymTheory().definitions.toPrintableString());
    }

    private Definitions getDefinitions() {

        return new Definitions(
                "SOLID - Mnemonic Acronym - 5 design principles",
                new LinkedHashMap<>() {{
                    put(
                            "S = Single-responsibility principle",
                            new Definitions.Descriptions(
                                    "There should never be more than one reason for a class to change.",
                                    new ArrayList<>(Arrays.asList(
                                            "A class have exactly one job.",
                                            "A class should have one and only one responsibility.",
                                            "A class should have one and only one reason to change.",
                                            "For example while having:",
                                            new StringBuilder()
                                                    .append("\t\t interface: ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(";")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t\t classes: ")
                                                    .append(ClassMethodUtils.getClassSimpleName(Rectangle.class))
                                                    .append(", ")
                                                    .append(ClassMethodUtils.getClassSimpleName(Square.class))
                                                    .append(", ")
                                                    .append(ClassMethodUtils.getClassSimpleName(Circle.class))
                                                    .append(", ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaCalculator.class))
                                                    .append(", ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaPrinter.class))
                                                    .append(".")
                                                    .toString(),
                                            "\tWhere application should calculate sum of all areas taken by shapes from given collection.",
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaCalculator.class))
                                                    .append(" only responsibility should be to sum up the areas of all areas in given shape collection.")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("If we want to also print the summed up area we should include this logic inside another class - ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaPrinter.class))
                                                    .append(".")
                                                    .toString()
                                    ))
                            )
                    );
                    put(
                            "O = Open-closed principle",
                            new Definitions.Descriptions(
                                    "Software entities (Objects or entities) should be open for extension, but closed for modification.",
                                    new ArrayList<>(Arrays.asList(
                                            "Software entities should be extendable without modifying the class itself.",
                                            "Objects or entities should be implemented in such a way that if we want to extend functionally - add some new functionality - this would be done by adding something new.",
                                            "This should not entail changing existing functionality - modifying of something already working.",
                                            "For example while having the same classes as in previous example.",
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("Imagine a situation where we would like to expand the amount of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" types, by adding Triangle.")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("If the logic calculating the area for given type of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" would be connected to class ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaCalculator.class))
                                                    .append(", adding new type of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" would be problematic.")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("Each time we would like to add a new type of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" we would have to modify class ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaCalculator.class))
                                                    .append(", its logic calculating summing up the area.")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("The specific implementation of logic behind calculating area for given type of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" should not be tied to ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaCalculator.class))
                                                    .append(" but to each individual implementation of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" - like: ")
                                                    .append(ClassMethodUtils.getClassSimpleName(Rectangle.class))
                                                    .append(", ")
                                                    .append(ClassMethodUtils.getClassSimpleName(Square.class))
                                                    .append(", ")
                                                    .append(ClassMethodUtils.getClassSimpleName(Circle.class))
                                                    .append(".")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("This way ")
                                                    .append(ClassMethodUtils.getClassSimpleName(ShapeCollectionAreaCalculator.class))
                                                    .append(" remains unchanged when we add new type of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(".")
                                                    .toString(),
                                            new StringBuilder()
                                                    .append("\t")
                                                    .append("The only thing that would be changed is the new implementation of ")
                                                    .append(ClassMethodUtils.getClassSimpleName(AreaFul.class))
                                                    .append(" interface in form of Triangle class, which would implement ")
                                                    .append(ClassMethodUtils.getClassAndMethodAndArgs(AreaFul.class, "getArea"))
                                                    .append(" method.")
                                                    .toString()
                                    ))
                            )
                    );
                    put(
                            "L = Liskov substitution principle",
                            new Definitions.Descriptions(
                                    "Functions that use pointers or references to base classes must be able to use objects of derived classes without knowing it.",
                                    new ArrayList<>(Arrays.asList(
                                            "Every subclass or derived class should be substitutable for their base or parent class.",
                                            "Methods using base classes should not need to know which kind of implementation it is using, out of all classes deriving from base class.",
                                            "It should be enough for a method using base class to know that it is using some kind of implementation of base class."
                                    ))
                            )
                    );
                    put(
                            "I = Interface segregation principle",
                            new Definitions.Descriptions(
                                    "Many client-specific interfaces are better than one general-purpose interface.",
                                    new ArrayList<>(Arrays.asList(
                                            "Client should never be forced to implement an interface which would be unused.",
                                            "Client should not be forced to depend on methods that will not be used.",
                                            "Many smaller interfaces is better than one bloated.",
                                            "If the implementation of interface methods is often forced, they are left unused then interface is bloated."
                                    ))
                            )
                    );
                    put(
                            "D = Dependency inversion principle",
                            new Definitions.Descriptions(
                                    "Depend upon abstractions, not concretions.",
                                    new ArrayList<>(Arrays.asList(
                                            "High-level module must not depend on the low-level module.",
                                            "Modules should depend on abstractions.",
                                            "Decoupling - requiring parent class so there is ability to switch concrete implementations easily"

                                    ))
                            )
                    );
                    put(
                            "",
                            new Definitions.Descriptions(
                                    "",
                                    new ArrayList<>(Arrays.asList(""))
                            )
                    );
                }}
        );
    }
}
