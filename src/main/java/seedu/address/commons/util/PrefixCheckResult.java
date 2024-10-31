package seedu.address.commons.util;

import java.util.List;

import seedu.address.logic.parser.Prefix;

public class PrefixCheckResult {
    private final boolean isAllPrefixPresent;
    private final List<Prefix> missingPrefixes;

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
