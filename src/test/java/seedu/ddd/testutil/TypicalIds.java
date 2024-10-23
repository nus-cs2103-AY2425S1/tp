package seedu.ddd.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.ddd.model.contact.common.Id;

/**
 * A utility class containing a list of {@code Id} objects to be used in tests.
 */
public class TypicalIds {
    public static final Id ID_FIRST = new Id(1);
    public static final Id ID_SECOND = new Id(2);
    public static final Id ID_THIRD = new Id(3);

    private TypicalIds() {} // prevents instantiation

    public static List<Id> getTypicalIdsAsList() {
        return new ArrayList<>(Arrays.asList(ID_FIRST, ID_SECOND, ID_THIRD));
    }

    public static Set<Id> getTypicalIdsAsSet() {
        return new HashSet<>(Arrays.asList(ID_FIRST, ID_SECOND, ID_THIRD));
    }
}
