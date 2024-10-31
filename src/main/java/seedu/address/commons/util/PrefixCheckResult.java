package seedu.address.commons.util;

import java.util.List;

import seedu.address.logic.parser.Prefix;

/**
 * Contains the results of checking for missing prefixes.
 */
public class PrefixCheckResult {
    private final boolean isAllPrefixPresent;
    private final List<Prefix> missingPrefixes;

    /**
     * Constructs a {@code PrefixCheckResult} object.
     * @param isAllPrefixPresent boolean value that is true when all mandatory prefixes are present.
     * @param missingPrefixes a {@code List} object that contains all the missing prefixes in the invalid add command.
     */
    public PrefixCheckResult(boolean isAllPrefixPresent, List<Prefix> missingPrefixes) {
        this.isAllPrefixPresent = isAllPrefixPresent;
        this.missingPrefixes = missingPrefixes;
    }

    public boolean isAllPrefixPresent() {
        return this.isAllPrefixPresent;
    }

    public List<Prefix> getMissingPrefixes() {
        return this.missingPrefixes;
    }

}
