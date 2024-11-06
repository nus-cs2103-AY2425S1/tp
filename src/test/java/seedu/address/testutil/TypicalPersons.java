package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_AGE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DETAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_1A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDY_GROUP_TAG_2B;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in
 * tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com").withGender("F").withAge("20").withDetail("To be assigned")
            .withStudyGroupTags("1A").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withEmail("benson@example.com").withGender("M").withAge("40").withDetail("Study completed")
            .withStudyGroupTags("Control", "1A").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withEmail("carl@example.com")
            .withGender("M").withAge("30").withDetail("To be assigned").withStudyGroupTags("1A")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withEmail("daniel@example.com").withGender("M").withAge("50").withDetail("To be assigned")
            .withStudyGroupTags("1B").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer")
            .withEmail("elle@example.com").withGender("F").withAge("60")
            .withStudyGroupTags("2A").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withEmail("fiona@example.com").withGender("F").withAge("35").withDetail("To follow up").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best")
            .withEmail("george@example.com").withGender("M").withAge("36").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier")
            .withEmail("hoon@example.com").withGender("M").withAge("17").withDetail("To follow up").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller")
            .withEmail("ida@example.com").withGender("F").withAge("21")
            .withStudyGroupTags("Treatment", "1A").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withDetail(VALID_DETAIL_AMY)
            .withStudyGroupTags(VALID_STUDY_GROUP_TAG_1A).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB).withGender(VALID_GENDER_BOB).withAge(VALID_AGE_BOB)
            .withDetail(VALID_DETAIL_BOB).withStudyGroupTags(VALID_STUDY_GROUP_TAG_1A, VALID_STUDY_GROUP_TAG_2B)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {
    } // prevents instantiation

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
