package seedu.address.testutil;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON;
    public static final Index INDEX_SECOND_PERSON;
    public static final Index INDEX_THIRD_PERSON;
    public static final Index INDEX_FOURTH_PERSON;
    public static final Index INDEX_FIFTH_PERSON;
    public static final Index INDEX_SIXTH_PERSON;
    public static final List<Index> INDEX_FIRST_AND_SECOND_PERSON;

    static {
        try {
            INDEX_FIRST_PERSON = Index.fromOneBased(1);
            INDEX_SECOND_PERSON = Index.fromOneBased(2);
            INDEX_THIRD_PERSON = Index.fromOneBased(3);
            INDEX_FOURTH_PERSON = Index.fromOneBased(4);
            INDEX_FIFTH_PERSON = Index.fromOneBased(5);
            INDEX_SIXTH_PERSON = Index.fromOneBased(6);
            INDEX_FIRST_AND_SECOND_PERSON = List.of(INDEX_SECOND_PERSON, INDEX_FIRST_PERSON);
        } catch (CommandException e) {
            throw new RuntimeException("Failed to initialize TypicalIndexes due to invalid index.", e);
        }
    }

}
