package interview;

import menu.Menu;
import menu.Option;
import solid.SolidAcronym;
import utils.CollectionUtils;
import utils.StaticUtils;

import java.lang.invoke.MethodHandles;

public class Interview {
    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void enterMenu(String[] args) {
        staticUtils.printMethodSignature("enterMenu", args.getClass());
        Menu.open(new Option[]{
                new Option("SOLID Acronym", SolidAcronym::main),
                new Option("printInterviewQuestions1", Interview::printInterviewQuestions1),
                new Option("printInterviewQuestions2", Interview::printInterviewQuestions2),
        });
    }

    private static void printInterviewQuestions1() {
        String[] interviewQuestions1 = new String[]{
                "JVM - Garbage Collector",
                "JVM - Stack vs. Heap",
                "JVM - Memory leak",
                "SOLID",
                "SQL - Inner/Outer Join",
                "SQL - NVL, Aggregate operations (count, average), Group by, Having, Limit",
                "Design Patterns: Decorator, Visitor, Builder, Factory - When used?",
                "Builder - String vs. StringBuilder",
                "Collections - List vs. Set vs. Map - implementations, differences",
                "Tests - Mojito - What cannot be mocked?",
                "Tests - JUnit",
                "Multithreading - Atomic variables, Volatile variables",
                "Multithreading - Semaphore",
                "Multithreading - Lock - read, write lock",
                "Multithreading - Zmienne warunkowe",
                "Multithreading - Collection - 2 threads try to access, one reading, one modifying",
                ">> Code Wars <<",
                "Webservices - what it is? - Server (Backend) vs. Client (Frontend)",
                "Webservice types: SOAP, REST",
                "SOAP - WDSL?",
                "Code responses: 100(?), 200, 300(?), 400, 500 - meaning",
                "StringBuilder vs StringBuffer vs StringJoiner (?) - parallel",
                "Logger\n"
                        + "\t\timport org.slf4j.Logger"
                        + "\t\timport org.slf4j.LoggerFactory"
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
                "Atomic operations - in Java machine code single operation. Is var1 += var2 an atomic operation? - No, it isn't.",
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
