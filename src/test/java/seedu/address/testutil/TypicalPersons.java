package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_TYPE_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENT_TYPE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.ClientHub;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Reminder ALICE_REMINDER = new ReminderBuilder()
            .withPersonName("Alice Pauline")
            .withDescription("Reminder 1")
            .withDateTime(LocalDate.of(2024, 10, 10),
                    LocalTime.of(11, 11)).build();

    public static final Reminder BENSON_REMINDER = new ReminderBuilder()
            .withPersonName("Benson Meier")
            .withDescription("Reminder 2")
            .withDateTime(LocalDate.of(2024, 11, 10),
                    LocalTime.of(6, 11)).build();

    public static final Reminder CARL_REMINDER = new ReminderBuilder()
            .withPersonName("Carl Kurz")
            .withDescription("Reminder 3")
            .withDateTime(LocalDate.of(2024, 12, 10),
                    LocalTime.of(7, 11)).build();

    public static final Reminder ALICEY_REMINDER = new ReminderBuilder()
            .withPersonName("Alice Potter")
            .withDescription("Reminder 5")
            .withDateTime(LocalDate.of(2024, 12, 10),
                    LocalTime.of(7, 11)).build();

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withClientTypes("Investment")
            .withDescription("A type")
            .withReminders(ALICE_REMINDER)
            .build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withClientTypes("Investment", "Savings")
            .withReminders(BENSON_REMINDER)
            .build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withClientTypes("Investment", "Healthcare")
            .withReminders(CARL_REMINDER)
            .build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withClientTypes("B").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822241")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824271")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824421")
            .withEmail("anna@example.com").withAddress("4th street").build();
    public static final Person ALICEY = new PersonBuilder().withName("Alice Potter").withPhone("99924422")
            .withEmail("APotter@example.com").withAddress("china")
            .withReminders(ALICEY_REMINDER).build();


    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824246")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821317")
            .withEmail("hans@example.com").withAddress("chicago ave").build();
    public static final Person HARRY = new PersonBuilder().withName("Harry Potter").withPhone("99924427")
            .withEmail("harry@example.com")
            .withAddress("china")
            .withClientTypes("Insurance Plan").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withClientTypes(VALID_CLIENT_TYPE_A)
            .withDescription(VALID_DESCRIPTION_A).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withClientTypes(VALID_CLIENT_TYPE_B, VALID_CLIENT_TYPE_A)
            .withDescription(VALID_DESCRIPTION_B).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code ClientHub} with all the typical persons.
     */
    public static ClientHub getTypicalClientHub() {
        ClientHub ch = new ClientHub();
        for (Person person : getTypicalPersons()) {
            ch.addPerson(person);
        }
        return ch;
    }

    public static List<Person> getTypicalPersons() {
        // return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, ALICEY));
        return Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, ALICEY)
                .stream()
                .map(person -> new PersonBuilder(person).build()) // Create a deep copy
                .collect(Collectors.toList());
    }
}
