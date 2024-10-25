package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOBWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_AMYWEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_BOBWEDDING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {

    public static final Wedding ALICE_WEDDING = new WeddingBuilder()
            .withName("Alice and Bob Wedding")
            .withDate("2024-06-15")
            .withVenue("Garden Ballroom")
            .withClient(TypicalPersons.ALICE)
            .build();

    public static final Wedding BENSON_WEDDING = new WeddingBuilder()
            .withName("Benson and Mary Wedding")
            .withDate("2024-07-20")
            .withVenue("Grand Hotel")
            .withClient(TypicalPersons.BENSON)
            .build();

    public static final Wedding CARL_WEDDING = new WeddingBuilder()
            .withName("Carl and Sarah Wedding")
            .withDate("2024-08-10")
            .withVenue("Beach Resort")
            .withClient(TypicalPersons.CARL)
            .build();

    public static final Wedding DANIEL_WEDDING = new WeddingBuilder()
            .withName("Daniel and Emma Wedding")
            .withDate("2024-09-05")
            .withVenue("Mountain View")
            .withClient(TypicalPersons.DANIEL)
            .build();

    public static final Wedding ELLE_WEDDING = new WeddingBuilder()
            .withName("Elle and James Wedding")
            .withDate("2024-10-15")
            .withVenue("City Hall")
            .withClient(TypicalPersons.ELLE)
            .build();

    // Manually added
    public static final Wedding HOON_WEDDING = new WeddingBuilder()
            .withName("Hoon and Lisa Wedding")
            .withDate("2024-11-20")
            .withVenue("Country Club")
            .withClient(TypicalPersons.HOON)
            .build();

    public static final Wedding IDA_WEDDING = new WeddingBuilder()
            .withName("Ida and Tom Wedding")
            .withDate("2024-12-25")
            .withVenue("Winter Garden")
            .withClient(TypicalPersons.IDA)
            .build();

    // Manually added - Wedding's details found in {@code CommandTestUtil}
    public static final Wedding AMY_WEDDING = new WeddingBuilder()
            .withName(VALID_NAME_AMY)
            .withDate(VALID_DATE_AMYWEDDING)
            .withVenue(VALID_VENUE_AMYWEDDING)
            .withClient(TypicalPersons.AMY)
            .build();

    public static final Wedding BOB_WEDDING = new WeddingBuilder()
            .withName(VALID_NAME_BOB)
            .withDate(VALID_DATE_BOBWEDDING)
            .withVenue(VALID_VENUE_BOBWEDDING)
            .withClient(TypicalPersons.BOB)
            .build();

    public static final String KEYWORD_MATCHING_GARDEN = "Garden"; // A keyword that matches Garden venues

    private TypicalWeddings() {} // prevents instantiation

    /**
     * Returns a list of typical weddings.
     */
    public static List<Wedding> getTypicalWeddings() {
        return new ArrayList<>(Arrays.asList(ALICE_WEDDING, BENSON_WEDDING, CARL_WEDDING,
                DANIEL_WEDDING, ELLE_WEDDING));
    }

    /**
     * Returns a list of typical weddings for filtering tests.
     */
    public static List<Wedding> getTypicalWeddingsFilter() {
        return new ArrayList<>(Arrays.asList(ALICE_WEDDING, BENSON_WEDDING, CARL_WEDDING,
                DANIEL_WEDDING, ELLE_WEDDING, HOON_WEDDING));
    }

    /**
     * Returns a list of weddings with additional test cases.
     */
    public static List<Wedding> getAdditionalWeddings() {
        return new ArrayList<>(Arrays.asList(ALICE_WEDDING, BENSON_WEDDING, CARL_WEDDING,
                DANIEL_WEDDING, ELLE_WEDDING, AMY_WEDDING, BOB_WEDDING));
    }
}