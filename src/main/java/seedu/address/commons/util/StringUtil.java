package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and partial matches are supported.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param substring cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsSubstringIgnoreCase(String sentence, String substring) {
        requireNonNull(sentence);
        requireNonNull(substring);

        String preppedSubstring = substring.trim().toLowerCase();
        checkArgument(!preppedSubstring.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedSubstring.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence.toLowerCase();

        return preppedSentence.contains(preppedSubstring);
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Calculates the minimum number of single-character edits (insertions, deletions, or substitutions)
     * needed for the string {@code b} to become a substring of the string {@code a}.
     *
     * This code was generated with the assistance of GitHub Copilot, an AI-powered code completion tool.
     * 
     * @param a The string in which we want to find the substring.
     * @param b The string that we want to transform into a substring of {@code a}.
     * @return The minimum number of single-character edits needed for {@code b} to become a substring of {@code a}.
     */
    public static int getLevenshteinDistanceSubstring(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        // Initialize the dp array
        for (int i = 0; i <= a.length(); i++) {
            for (int j = 0; j <= b.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Math.min(
                        dp[i - 1][j - 1] + (a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1), // Substitution
                        Math.min(
                            dp[i - 1][j] + 1, // Deletion
                            dp[i][j - 1] + 1 // Insertion
                        )
                    );
                }
            }
        }

        // Find the minimum value in the last row of the dp array
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i <= a.length(); i++) {
            minDistance = Math.min(minDistance, dp[i][b.length()]);
        }

        return minDistance;
    }
}
