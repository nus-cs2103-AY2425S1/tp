package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SEX_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {
    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253").withAge("24").withSex("Female")
            .withTags("Obesity", "Diet Plan").addAppointment("11/11/2025 1200").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432").withAge("37").withSex("Male")
            .addAppointment("01/01/2026 1200").withTags("diabetes", "stroke")
            .withStarredStatus("true")
            .build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withAge("29").withSex("Female").withTags("Insomnia").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("diabetes")
            .withAge("40").withSex("Male").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("94822245")
            .withEmail("werner@example.com").withAddress("michegan ave")
            .withAge("16").withSex("Female").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("94824275")
            .withEmail("lydia@example.com").withAddress("little tokyo")
            .withAge("70").withSex("Female").withTags("Surgery").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("94824425")
            .withEmail("anna@example.com").withAddress("4th street")
            .withAge("88").withSex("Male").build();

    public static final Person MARADONA = new PersonBuilder().withName("Diego Maradona").withPhone("94824221")
            .withEmail("maradona@example.com").withAddress("Argentina")
            .withAge("60").withSex("Female").withTags("footballer")
            .withAppointments("01/01/2020 1000").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("84824245")
            .withEmail("stefan@example.com").withAddress("little india")
            .withAge("26").withSex("Male").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("84821315")
            .withEmail("hans@example.com").withAddress("chicago ave")
            .withAge("90").withSex("Female").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND)
            .withAge(VALID_AGE_AMY).withSex(VALID_SEX_AMY).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withAge(VALID_AGE_BOB).withSex(VALID_SEX_BOB).build();

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
