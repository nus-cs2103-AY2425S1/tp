package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_SCORE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTERVIEW_SCORE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_JOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_JAVA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILL_PYTHON;
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

    public static final Person ALICE = new PersonBuilder().withName("Alice Pauline").withJob("Software Engineer L1")
            .withEmail("alice@example.com")
            .withPhone("94351253").withSkills("python", "java").withInterviewScore("5.0")
            .withTags("friends", "pending").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier").withJob("Software Engineer L2")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withSkills("python").withInterviewScore("6.0")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withJob("Software Engineer L3")
            .withPhone("95352563").withEmail("heinz@example.com")
            .withSkills("python").withInterviewScore("5.0")
            .withTags("hired").build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withJob("Software Engineer L4")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withSkills("python", "java", "ruby").withInterviewScore("4.0")
            .withTags("friends", "rejected").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withJob("Software Engineer L5")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withSkills("python").withInterviewScore("9.1").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withJob("Software Engineer L6")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withSkills("html", "css").withInterviewScore("4.4").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withJob("Software Engineer L7")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withSkills("python", "java", "C").withInterviewScore("9.9").build();


    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withJob("Software Engineer L8")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withInterviewScore("6.5").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withJob("Software Engineer L9")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withSkills("java").withInterviewScore("5.2").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withJob(VALID_JOB_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withSkills(VALID_SKILL_PYTHON).withInterviewScore(VALID_INTERVIEW_SCORE_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withJob(VALID_JOB_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withSkills(VALID_SKILL_PYTHON, VALID_SKILL_JAVA).withInterviewScore(VALID_INTERVIEW_SCORE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
