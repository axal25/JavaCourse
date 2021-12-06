package fibonacci;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Fibonacci {

    static class V1 {

        static int getMember(int memberNumber) {
            if (memberNumber < 0) {
                throw new IllegalArgumentException("There's no fibonacci.Fibonacci's sequence member for number lower then 0. Your number: " + memberNumber + ".");
            }
            if (memberNumber == 0) {
                return 0;
            }
            if (memberNumber == 1) {
                return 1;
            }
            return getMember(memberNumber - 1) + getMember(memberNumber - 2);
        }

        static boolean isMember(int candidate) {
            return candidate == getMemberGreaterOrEqualTo(candidate);
        }

        static int getMemberGreaterOrEqualTo(int candidate) {
            if (candidate < 0) {
                throw new IllegalArgumentException("There's no fibonacci.Fibonacci's sequence member with value lower then 0.");
            }

            // prevPrevMember <=> n-2
            // n = 0, f(n) = 0
            int prevPrevMember = 0;
            if (candidate == 0) {
                return prevPrevMember;
            }
            // prevMember <=> n-1
            // n = 1, f(n) = 1
            int prevMember = 1;
            if (candidate == 1) {
                return prevMember;
            }

            // currentMember <=> n
            // n > 1, f(n) = f(n-2) + f(n-1)
            int currentMember;
            do {
                currentMember = prevPrevMember + prevMember;
                prevPrevMember = prevMember;
                prevMember = currentMember;
            } while (currentMember < candidate);

            return currentMember;
        }
    }

    static class V2 {
        private static final Map<Integer, Integer> fibonacciMembers;

        static {
            fibonacciMembers = new HashMap<>();
            fibonacciMembers.put(0, 0);
            fibonacciMembers.put(1, 1);
        }

        static int getMember(int memberNumber) {
            if (memberNumber < 0) {
                throw new IllegalArgumentException("There's no fibonacci.Fibonacci's sequence member for number lower then 0. Your number: " + memberNumber + ".");
            }
            if (fibonacciMembers.getOrDefault(memberNumber, null) == null) {
                fibonacciMembers.put(memberNumber, getMember(memberNumber - 2) + getMember(memberNumber - 1));
            }
            return fibonacciMembers.get(memberNumber);
        }

        static boolean isMember(int candidate) {
            return candidate == getMemberGreaterOrEqualTo(candidate);
        }

        static int getMemberGreaterOrEqualTo(int candidate) {
            if (candidate < 0) {
                throw new IllegalArgumentException("There's no fibonacci.Fibonacci's sequence member with value lower then 0.");
            }
            int i = 0;
            while (getMember(i) < candidate) {
                i++;
            }
            return getMember(i);
        }
    }

    static class V3 {

        static boolean isMember(int candidate) {
            int candidateNumber = getCandidateNumber(candidate);
            if (candidate < 0 || candidateNumber < 0) {
                return false;
            }
            if (candidate == 0 || candidateNumber == 0) {
                return true;
            }
            if (candidate == 1 || candidateNumber == 1) {
                return true;
            }
            int prevPrevMember = V1.getMember(candidateNumber - 2);
            int prevMember = V1.getMember(candidateNumber - 1);
            return prevPrevMember + prevMember == candidate
                    && isMember(prevPrevMember)
                    && isMember(prevMember);
        }

        static int getCandidateNumber(int candidate) {
            return IntStream.iterate(0, i -> i + 1)
                    .filter(i -> V1.getMember(i) >= candidate)
                    .findFirst()
                    .orElse(-1);
        }
    }

    public static class Test {

        public static void printGetMemberUntil(int max) {
            for (int i = 0; i < max; i++) {
                System.out.printf("getMemberV1(%d): %d, getMemberV2(%d): %d\n\r", i, V1.getMember(i), i, V2.getMember(i));
            }
        }

        public static void testIsMemberUntil(int max) {
            for (int i = 0; i < max; i++) {
                boolean isDoubtfulMember = V3.isMember(i);
                boolean isMemberV1 = V1.isMember(i);
                boolean isMemberV2 = V2.isMember(i);
                if (isDoubtfulMember != isMemberV1) {
                    throw new RuntimeException(
                            new StringBuilder()
                                    .append("My 2 methods do not agree on candidate: ")
                                    .append(i)
                                    .append(". - isDoubtful: ")
                                    .append(isDoubtfulMember)
                                    .append(" vs. isMemberV1: ")
                                    .append(isMemberV1)
                                    .append(" vs. isMemberV2: ")
                                    .append(isMemberV2)
                                    .append(".")
                                    .toString()
                    );
                }
            }
        }

        public static void testGetMemberUntil(int max) {
            for (int i = 0; i < max; i++) {
                int getMemberV1 = V1.getMember(i);
                int getMemberV2 = V2.getMember(i);
                if (getMemberV1 != getMemberV2) {
                    throw new RuntimeException(
                            new StringBuilder()
                                    .append("My 2 methods do not agree on candidate: ")
                                    .append(i)
                                    .append(". - getMemberV1: ")
                                    .append(getMemberV1)
                                    .append(" vs. getMemberV2: ")
                                    .append(getMemberV2)
                                    .append(".")
                                    .toString()
                    );
                }
            }
        }
    }
}
