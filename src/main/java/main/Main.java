package main;

import fibonacci.Fibonacci;
import input.read.InputRead;
import interview.Interview;
import menu.Menu;
import menu.Option;
import other.Syntax;
import stream.Streams;
import threads.Threads;
import utils.ArgUtils;
import utils.EnvUtils;
import utils.StaticUtils;
import visitor.main.VisitorMain;

import java.lang.invoke.MethodHandles;

public class Main {
    public static final int SCREEN_WIDTH_IN_CHARS = 200;
    private static final StaticUtils staticUtils = new StaticUtils(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        staticUtils.printMainSignature(args.getClass());
        enterMenu(args);
    }

    private static void enterMenu(String[] args) {
        staticUtils.printMethodSignature("enterMenu", args.getClass());
        Menu.open(new Option[]{
                new Option("Program arguments", () -> ArgUtils.printArgs(args)),
                new Option("Environment variables", EnvUtils::printEnvVars),
                new Option("Other demonstration", Syntax::main),
                new Option("Input read", InputRead::main),
                new Option("Interview questions", () -> Interview.enterMenu(args)),
                new Option("Visitor demonstration", VisitorMain::main),
                new Option("Multi-threading demonstration", () -> Threads.main(args)),
                new Option("Fibonacci demonstration", () -> {
                    Fibonacci.Test.printGetMemberUntil(40);
                    Fibonacci.Test.testGetMemberUntil(40);
                    Fibonacci.Test.testIsMemberUntil(40);
                }),
                new Option("Solution demonstration", () -> other.Solution.main(args)),
                new Option("Streams", Streams::main)
        });
    }
}
