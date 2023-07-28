package main;

import fibonacci.Fibonacci;
import input.read.demo.InputReadDemo;
import input.read.menu.Menu;
import input.read.menu.Option;
import interview.Interview;
import other.Syntax;
import regex.pattern.scanner.RegexPatternScannerDemo;
import stream.Streams;
import threads.Threads;
import utils.ArgUtils;
import utils.EnvUtils;
import utils.StaticOrMainMethodUtils;
import pattern.visitor.old.main.VisitorMain;

import java.lang.invoke.MethodHandles;

public class Main {

    private static final StaticOrMainMethodUtils staticOrMainMethodUtils = new StaticOrMainMethodUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        staticOrMainMethodUtils.printMainSignature(args.getClass());
        enterMenu(args);
    }

    private static void enterMenu(String[] args) {
        staticOrMainMethodUtils.printMethodSignature("enterMenu", args.getClass());
        Menu.open(new Option[]{
                new Option("Program arguments", () -> ArgUtils.printArgs(args)),
                new Option("Environment variables", EnvUtils::printEnvVars),
                new Option("Other demonstration", Syntax::main),
                new Option("Input read", InputReadDemo::main),
                new Option("Interview questions", () -> Interview.enterMenu(args)),
                new Option("Visitor demonstration", VisitorMain::main),
                new Option("Multi-threading demonstration", () -> Threads.main(args)),
                new Option("Fibonacci demonstration", () -> {
                    Fibonacci.Test.printGetMemberUntil(40);
                    Fibonacci.Test.testGetMemberUntil(40);
                    Fibonacci.Test.testIsMemberUntil(40);
                }),
                new Option("Solution demonstration", () -> other.Solution.main(args)),
                new Option("Streams", Streams::main),
                new Option("Regex Pattern Scanner", RegexPatternScannerDemo::main),
        });
    }
}
