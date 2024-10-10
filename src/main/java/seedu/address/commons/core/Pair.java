package seedu.address.commons.core;

import seedu.address.commons.core.index.Index;

public class Pair {
    public Index first;
    public String second;
    public Pair(Index i, String s) {
        first = i;
        second = s;
    }
}
