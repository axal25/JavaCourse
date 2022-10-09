package interview;

import collections.theory.CollectionFrameworkMain;
import input.read.menu.Menu;
import input.read.menu.Option;
import reverse.polish.notation.ReversePolishNotationAkaPostfixNotationDemo;
import solid.SolidAcronym;
import utils.CollectionUtils;
import utils.StaticOrMainMethodUtils;

import java.lang.invoke.MethodHandles;

public class Interview {
    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        // TODO: Delete
        printInterviewQuestions1();
        printInterviewQuestions2();
    }

    public static void enterMenu(String[] args) {
        staticOrMainMethodUtils.printMethodSignature("enterMenu", args.getClass());
        Menu.open(new Option[]{
                new Option("SOLID Acronym", SolidAcronym::main),
                new Option("Collection framework", CollectionFrameworkMain::main),
                new Option("Reverse Polish / PostFix Notation", ReversePolishNotationAkaPostfixNotationDemo::main),
                new Option("printInterviewQuestions1", Interview::printInterviewQuestions1),
                new Option("printInterviewQuestions2", Interview::printInterviewQuestions2),
        });
    }

    private static void printInterviewQuestions1() {
        String[] interviewQuestions1 = new String[]{
                "JVM - Garbage Collector",
                "JVM - Stack vs. Heap",
                "JVM - Memory leak",
                "Builder - String vs. StringBuilder",
                "StringBuilder vs StringBuffer vs StringJoiner (?) - parallel",
                "SOLID",
                "Collection framework",
                "SQL - Inner/Outer Join",
                "SQL - NVL, Aggregate operations (count, average), Group by, Having, Limit",
                "Design Patterns: Decorator, Visitor, Builder, Factory - When used?",
                "Tests - Mojito - What cannot be mocked?\n" +
                        "\t\tfinal or anonymous classes, primitive types\n" +
                        "\t\tfinal or static methods",
                "Tests - JUnit",
                "Multithreading - Atomic variables: AtomicInteger, AtomicLong, AtomicBoolean, AtomicReference\n" +
                        "\t\tnon-blocking algorithms for concurrent exploit low-level atomic machine instruction such as CAS to ensure data integrity.\n" +
                        "\t\t\t\tCAS - Compare And Swap\n" +
                        "\t\t\t\t\t\tConsists of 3 operands:\n" +
                        "\t\t\t\t\t\t\t\tM - memory location on which to operate\n" +
                        "\t\t\t\t\t\t\t\tA - expected variable value\n" +
                        "\t\t\t\t\t\t\t\tB - new value to be set\n" +
                        "\t\t\t\t\t\tif M matches A then M is updated to B otherwise M is unaffected\n" +
                        "\t\t1 thread winds and updates value\n" +
                        "\t\t\t\tother threads loose (do not update value), are informed update failed, are not suspended, context switch avoided\n" +
                        "\t\t\t\t\t\twe have to handle when CAS did not succeed - either: try again and again until success, or do nothing and move on",
                "Multithreading - volatile (keyword) variable used in synchronized method - unsure proper reference visibility between threads",
                "Multithreading - Semaphore",
                "Multithreading - Lock - read, write lock",
                "Multithreading - Zmienne warunkowe",
                "Multithreading - Collection - 2 threads try to access, one reading, one modifying",
                ">> Code Wars <<",
                "Webservices - what it is? - Server (Backend) vs. Client (Frontend)",
                "Webservice types: SOAP, REST",
                "SOAP - WDSL?",
                "Code responses:\n" +
                        "\t\t100 (1XX) - information - request still processing - wait for final response\n" +
                        "\t\t200 (2XX) - success\n" +
                        "\t\t\t\t200 - OK\n" +
                        "\t\t\t\t201 - Created\n" +
                        "\t\t300 (3XX) - redirect\n" +
                        "\t\t400 (4XX) - client error\n" +
                        "\t\t\t\t400 - bad request\n" +
                        "\t\t\t\t401 - unauthorized\n" +
                        "\t\t\t\t403 - forbidden\n" +
                        "\t\t\t\t404 - not found\n" +
                        "\t\t\t\t405 - method not allowed\n" +
                        "\t\t\t\t408 - request timeout\n" +
                        "\t\t500 (5XX) - server error\n" +
                        "\t\t\t\t500 - internal server error" +
                        "\t\t\t\t501 - not implemented" +
                        "\t\t\t\t502 - bad gateway" +
                        "\t\t\t\t503 - service unavailable" +
                        "\t\t\t\t504 - gateway timeout",
                "Logger\n"
                        + "\t\timport org.slf4j.Logger\n"
                        + "\t\timport org.slf4j.LoggerFactory\n"
                        + "\t\tLogger LOGGER = LoggerFactory.getLogger(CallerClassGetter.getCallerClass())",
                "Annotations",
                "HashCode and Equals, Comparable",
                "Encryption, Hashing, Salting",
                // https://www.thesslstore.com/blog/difference-encryption-hashing-salting/
                "\t Asymmetric encryption - Public (encrypt) vs. Private (decrypt) key - SSL/TLS (stronger, one-way, 2048 bit)",
                "\t\t Symmetric encryption - 2 Private keys (encrypt and decrypt) - after SSL handshake - Symmetric session key (weaker, two-way, 256 bit)",
                "\t Hashing / Hash value / Hash sum / Hash digest / Check-sum - mapping any size data to fixed length. One-way. Data/file verification against alteration.",
                "\t\t Algorithms output fixed length: SHA-256/SHA-2 - 256 bits. MD4 - 128 bits. MD5 - ?. Deprecated/broke by Google: SHA-1.",
                "\t\t Collision - Hash value for each data should be unique. If 2 hash values are the same for 2 different data sources it's called collision.",
                "\t Salting - adding unique value (salt) to the end of password and then hash coding it.",
                "Hashing vs Index - SQL (?)",
        };

        CollectionUtils.printAsSemiJson(interviewQuestions1, "Interview Questions 1");
    }

    private static void printInterviewQuestions2() {
        String[] interviewQuestions2 = new String[]{
                "Abstract class vs. Interface - differences",
                "Passing parameters to method in Java - is it by value or reference? When which? - Always by value. You cannot modify the passed parameter variable inside method so it would change outside of the method.",
                "Atomic operations\n" +
                        "\t\tIn Java machine code single operation.\n" +
                        "\t\t\t\tIs var1+=var2; an atomic operation?\n" +
                        "\t\t\t\t\t\tNo, it isn't. It consists of 4 operations:\n" +
                        "\t\t\t\t\t\t\t\tretrieve var1 value, retrieve var2 value, sum values, substitute new value to var1\n" +
                        "\t\t\t\tIs var3++; an atomic operation?\n" +
                        "\t\t\t\t\t\tNo, it isn't. It consists of 3 operations:\n" +
                        "\t\t\t\t\t\t\t\tretrieve value, increment value, substitute new value",
                "JVM",
                "Synchronize",
                "IoC (Inversion of Control), DI (Dependency Injection) - we do this to achieve [Loose coupling]",
                "Patterns - Describe pattern, when to use, what is the result",
                "Hibernate - N+1 problem",
                "Relationship between hashCode and equals - If equals is true the hashCodes should be the same. If hashCodes are the same the equals can be false because the capacity of hash table can be too small and multiple different objects can be places in same bucket.",
                "Exception class hierarchy - Object > Throwable > Exception > RuntimeException",
        };

        CollectionUtils.printAsSemiJson(interviewQuestions2, "Interview Questions 2");
    }
}
