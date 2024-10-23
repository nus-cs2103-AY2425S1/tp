package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPERIENCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPERIENCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILLS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SKILLS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
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
        .withPhone("94351253").withSkills("DevOps, python").withExperience("Intern at Meta from 2022-2023")
        .withStatus("Applied").withNote("Super Confident").withDesiredRole("Software Engineer")
        .withTags("friends").build();

    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
        .withAddress("311, Clementi Ave 2, #02-25")
        .withEmail("johnd@example.com").withPhone("98765432").withSkills("DevOps, python")
        .withExperience("Intern at Apple from 2022-2023").withStatus("Applied")
        .withNote("Dislike micro-managing").withDesiredRole("Project Manager")
        .withTags("owesMoney", "friends").build();

    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
        .withEmail("heinz@example.com").withAddress("wall street").withSkills("DevOps, python")
        .withExperience("Intern at Google from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("Data Analyst").build();

    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
        .withEmail("cornelia@example.com").withAddress("10th street").withSkills("DevOps, python")
        .withExperience("Intern at Grab from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("Product Owner").withTags("friends").build();

    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
        .withEmail("werner@example.com").withAddress("michegan ave").withSkills("DevOps, python")
        .withExperience("Intern at NUS from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("UX Designer").build();

    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
        .withEmail("lydia@example.com").withAddress("little tokyo").withSkills("DevOps, python")
        .withExperience("Intern at NTU from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("Marketing Manager").build();

    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
        .withEmail("anna@example.com").withAddress("4th street").withSkills("DevOps, python")
        .withExperience("Intern at SIM from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("Business Analyst").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
        .withEmail("stefan@example.com").withAddress("little india").withSkills("DevOps, python")
        .withExperience("Intern at SMU from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("Software Engineer").build();

    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
        .withEmail("hans@example.com").withAddress("chicago ave").withSkills("DevOps, python")
        .withExperience("Intern at SUSS from 2022-2023").withStatus("Applied").withNote("")
        .withDesiredRole("Data Scientist").build();

    // Manually added - Person's details found in {@code FilterStatusCommandTest}
    public static final Person ADIADI = new PersonBuilder().withName("Adiadi Hih").withPhone("99119911")
            .withEmail("adiadin@example.com").withAddress("little india 2").withSkills("DevOps, python, C")
            .withExperience("Intern at IMH from 2022-2023").withStatus("Rejected").withNote("Funny")
            .withDesiredRole("Network Engineer").build();

    public static final Person DOMI = new PersonBuilder().withName("Domi Tan").withPhone("83323323")
            .withEmail("domidomi@example.com").withAddress("20th street").withSkills("DevOps, Duolingo")
            .withExperience("Intern at DSTA from 2022-2023").withStatus("Rejected").withNote("Smart")
            .withDesiredRole("Business Specialist").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
        .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withSkills(VALID_SKILLS_AMY)
        .withExperience(VALID_EXPERIENCE_AMY).withStatus(VALID_STATUS_AMY)
        .withNote("Optimistic").withDesiredRole("Software Engineer").withTags(VALID_TAG_FRIEND).build();

    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
        .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withSkills(VALID_SKILLS_BOB)
        .withExperience(VALID_EXPERIENCE_BOB).withStatus(VALID_STATUS_BOB)
        .withNote("Charismatic").withDesiredRole("Data Analyst").withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, ADIADI, DOMI));
    }
}
