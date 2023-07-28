package template.test;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class UsernameGenerator {
    private boolean doAddHostname;

    String getGeneratedUsername(String firstName, String lastName) {
        return String.format(
                "%s%s%s",
                !isNullOrEmptyOrBlank(firstName) && firstName.length() > 0
                        ? firstName.substring(0, 1)
                        : "",
                toEmptyIfNullOrEmptyOrBlank(lastName),
                doAddHostname ? "@joles.pl" : "");
    }

    private static boolean isNullOrEmptyOrBlank(String input) {
        return input == null || input.isEmpty() || input.trim().isEmpty() || input.isBlank();
    }

    private static String toEmptyIfNullOrEmptyOrBlank(String input) {
        return isNullOrEmptyOrBlank(input) ? "" : input;
    }
}
