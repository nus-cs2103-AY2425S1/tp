package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_IDENTITY_NUMBER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_FIRST_VISIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOG_SECOND_VISIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withIdentityNumber("S0211145D")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withStatus("HIGH").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withIdentityNumber("S6642654H")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com").withPhone("98765432")
            .withStatus("LOW")
            .withLogs("19 Oct 2024|Doctor's Appointment").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withIdentityNumber("S9844952C")
            .withPhone("95352563").withEmail("heinz@example.com").withAddress("wall street").withStatus("DISCHARGED")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withIdentityNumber("S6914918I")
            .withPhone("87652533").withEmail("cornelia@example.com").withAddress("10th street").withStatus("DISCHARGED")
            .build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withIdentityNumber("S5602975C")
            .withPhone("94822241").withEmail("werner@example.com").withAddress("michegan ave").withStatus("LOW")
            .build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withIdentityNumber("S7761327G")
            .withPhone("94824272").withEmail("lydia@example.com").withAddress("little tokyo").withStatus("HIGH")
            .build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withIdentityNumber("S7468515C")
            .withPhone("94824425").withEmail("anna@example.com").withAddress("4th street").withStatus("HIGH").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withIdentityNumber("S7717720E")
            .withPhone("84824246").withEmail("stefan@example.com").withAddress("little india").withStatus("HIGH")
            .build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withIdentityNumber("S8013760E")
            .withPhone("84821317").withEmail("hans@example.com").withAddress("chicago ave").withStatus("LOW").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY)
            .withIdentityNumber(VALID_IDENTITY_NUMBER_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY).withStatus(VALID_STATUS_AMY).withLogs(VALID_LOG_SECOND_VISIT).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withIdentityNumber(VALID_IDENTITY_NUMBER_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB).withStatus(VALID_STATUS_BOB)
            .withLogs(VALID_LOG_FIRST_VISIT, VALID_LOG_SECOND_VISIT).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
