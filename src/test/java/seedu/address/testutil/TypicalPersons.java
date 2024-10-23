package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOC_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOC_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOC_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOC_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOC_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOC_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECPHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECRS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ECRS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
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
            .withPhone("94351253").withEmergencyContact("Sarah Lim", "98761234", "Parent")
            .withDoctorName("Tan Wei Ming").withDoctorPhone("82345678").withDoctorEmail("tanweiming@gmail.com")
            .withTags("friends").build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withEmergencyContact("Kevin Goh", "98764123", "Husband")
            .withDoctorName("Koh Jia Jia").withDoctorPhone("81818211").withDoctorEmail("jjkoh@hotmail.com")
            .withTags("owesMoney", "friends").build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street")
            .withEmergencyContact("Kevin Goh", "98764123", "Husband")
            .withDoctorName("Ben Po").withDoctorPhone("61231231").withDoctorEmail("benpo@gmail.com")
            .build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street")
            .withEmergencyContact("Haziq Bin Abudllah", "98763412", "Brother")
            .withDoctorName("Sebastian Sim").withDoctorPhone("62353535").withDoctorEmail("sebsim@hotmail.com")
            .withTags("friends").build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmergencyContact("Amanda Lee", "98762341", "Cousin")
            .withDoctorName("Wong See Toh").withDoctorPhone("88338483").withDoctorEmail("wongst@hotmail.com")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmergencyContact("Nurul Ain", "98761243", "Grandmother")
            .withDoctorName("Bing Qi Ling").withDoctorPhone("81818211").withDoctorEmail("bingqiling@hotmail.com")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmergencyContact("Anjali Devi", "98763124", "Daughter")
            .withDoctorName("Peh Boon Hao").withDoctorPhone("96161616").withDoctorEmail("boonhao@hotmail.com")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
            .withEmergencyContact(VALID_ECNAME_AMY, VALID_ECPHONE_AMY, VALID_ECRS_AMY)
            .withDoctorName(VALID_DOC_NAME_AMY).withDoctorPhone(VALID_DOC_PHONE_AMY)
            .withDoctorEmail(VALID_DOC_EMAIL_AMY)
            .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
            .withEmergencyContact(VALID_ECNAME_BOB, VALID_ECPHONE_BOB, VALID_ECRS_BOB)
            .withDoctorName(VALID_DOC_NAME_BOB).withDoctorPhone(VALID_DOC_PHONE_BOB)
            .withDoctorEmail(VALID_DOC_EMAIL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

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
