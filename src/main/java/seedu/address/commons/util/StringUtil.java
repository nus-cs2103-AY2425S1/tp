package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;

import seedu.address.model.task.Deadline;
import seedu.address.model.task.Event;
import seedu.address.model.task.Task;
import seedu.address.model.task.Todo;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, but a full word match is required.
     *   <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     *   Ignores case, and only a partial match is required.
     *   <br>examples:<pre>
     *       containsPartialWordIgnoreCase("ABc def", "abc") == true
     *       containsPartialWordIgnoreCase("ABc def", "DEF") == true
     *       containsPartialWordIgnoreCase("ABc def", "AB") == true
     *       containsPartialWordIgnoreCase("ABc def", "cd") == false // no partial match with a word
     *       </pre>
     * @param sentence cannot be null
     * @param word cannot be null, cannot be empty
     */
    public static boolean containsPartialWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedSentence = sentence.toLowerCase();
        String preppedWord = word.trim().toLowerCase();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        return preppedSentence.contains(preppedWord);
    }

    /**
     * Returns true if the {@code phoneNumber} contains the {@code searchNumber}.
     *   Only a partial match is required.
     *   <br>examples:<pre>
     *       containsPhoneNumber("99209378", "92") == true // partial match
     *       containsPhoneNumber("99209378", "9378") == true // partial match
     *       containsPhoneNumber("82810284", "82810284") == true // full match
     *       containsPhoneNumber("99209378", "86") == false // no partial match with number
     *       </pre>
     * @param phoneNumber cannot be null
     * @param searchNumber cannot be null, cannot be empty
     */
    public static boolean containsPhoneNumber(String phoneNumber, String searchNumber) {
        requireNonNull(phoneNumber);
        requireNonNull(searchNumber);
        checkArgument(!searchNumber.isEmpty(), "Phone number parameter cannot be empty");
        return phoneNumber.toLowerCase().contains(searchNumber.toLowerCase());
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
     * Formats the tasks nicely for output
     * @param tasks HashSet of tasks to be formatted
     * @return Properly formatted string for output
     */
    public static String tasksString(HashSet<Task> tasks) {
        StringBuilder sb = new StringBuilder();
        for (Task task : tasks) {
            if (task instanceof Todo) {
                sb.append("\nTodo: ").append(task.getDescription());
            } else if (task instanceof Deadline deadline) {
                // Cast to Deadline to access its specific methods
                sb.append("\nDeadline: ").append(deadline.getDescription())
                        .append(" by ").append(deadline.getBy());
            } else if (task instanceof Event event) {
                // Cast to Event to access its specific methods
                sb.append("\nEvent: ").append(event.getDescription())
                        .append(" from ").append(event.getFrom())
                        .append(" to ").append(event.getTo());
            }
        }
        return sb.toString();
    }
}
