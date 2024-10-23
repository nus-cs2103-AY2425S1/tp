package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static final Wedding WEDDING_ONE = new Wedding(new WeddingName("John and Jane Wedding"),
            new WeddingDate(LocalDate.parse("12/12/2026", formatter)),
            new ArrayList<>(List.of(ALICE.getId(), ELLE.getId(), BENSON.getId())));
    public static final Wedding WEDDING_TWO = new Wedding(new WeddingName("Mike and Anna Wedding"),
            new WeddingDate(LocalDate.parse("13/01/2027", formatter)),
            new ArrayList<>(List.of(FIONA.getId(), ELLE.getId(), GEORGE.getId())));
    public static final Wedding WEDDING_THREE = new Wedding(new WeddingName("Phoebe and Jay Wedding"),
            new WeddingDate(LocalDate.parse("13/11/2025", formatter)),
            new ArrayList<>(List.of(CARL.getId(), FIONA.getId(), BENSON.getId())));;

    private TypicalWeddings() {}

    /**
     * Returns a list of typical weddings.
     */
    public static List<Wedding> getTypicalWeddings() {
        return new ArrayList<>(Arrays.asList(WEDDING_ONE, WEDDING_TWO));
    }
}
