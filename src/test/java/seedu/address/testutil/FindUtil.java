package seedu.address.testutil;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.parser.Prefix;

/**
 * A utility class for Find command.
 */
public class FindUtil {
    /**
     * Returns a criteria for find command using provided {@code Prefix} and
     * keywords.
     */
    public static String getFindCriteria(Prefix prefix, List<String> keywords) {
        return prefix + keywords.stream().collect(Collectors.joining(" "));
    }
}
