package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {

    public static final Wedding WEDDING_ONE = new Wedding("John and Jane Wedding", "12/12/2024");
    public static final Wedding WEDDING_TWO = new Wedding("Mike and Anna Wedding", "13/01/2025");

    private TypicalWeddings() {}

    /**
     * Returns a list of typical weddings.
     */
    public static List<Wedding> getTypicalWeddings() {
        return new ArrayList<>(Arrays.asList(WEDDING_ONE, WEDDING_TWO));
    }
}
