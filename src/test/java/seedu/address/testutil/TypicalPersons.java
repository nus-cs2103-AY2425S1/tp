package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTRACT_END_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTRACT_END_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEPARTMENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;

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
            .withPhone("94351253").withDepartment("IT").withRole("SWE").withIsEmployee(false).build();
    public static final Person BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withEmail("johnd@example.com")
            .withDepartment("IT").withRole("SWE").withPhone("98765432").withIsEmployee(false).build();
    public static final Person CARL = new PersonBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").withDepartment("IT")
            .withRole("SWE").withIsEmployee(false).build();
    public static final Person DANIEL = new PersonBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withDepartment("IT").withRole("SWE")
            .withIsEmployee(false).build();
    public static final Person ELLE = new PersonBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").withDepartment("IT").withRole("SWE")
            .withIsEmployee(false).build();
    public static final Person FIONA = new PersonBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").withDepartment("IT").withRole("SWE")
            .withIsEmployee(false).build();
    public static final Person GEORGE = new PersonBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withAddress("4th street").withDepartment("IT").withRole("SWE")
            .withIsEmployee(false).build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withAddress("little india").withDepartment("IT").withRole("SWE")
            .withIsEmployee(false).build();
    public static final Person IDA = new PersonBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").withDepartment("IT").withRole("SWE")
            .withIsEmployee(false).build();

    //Added for testing Roles and Departments
    public static final Person BJUNKANG = new PersonBuilder().withName("Jun Kang").withPhone("12345678")
            .withEmail("junkang@email.com").withAddress("admiralty").withDepartment("Board of Directors")
            .withRole("Board chairman").withContractEndDate("2020-01-02").withIsEmployee(true).build();
    public static final Person AJUNWEI = new PersonBuilder().withName("Jun Wei").withPhone("12345678")
            .withEmail("junwei@email.com").withAddress("admiralty").withDepartment("Accounting")
            .withRole("Accountant").withContractEndDate("2020-01-01").withIsEmployee(true).build();
    public static final Person CJUNYU = new PersonBuilder().withName("Jun Yu").withPhone("12345678")
            .withEmail("junyu@email.com").withAddress("admiralty").withDepartment("Customer Service")
            .withRole("Customer Service Manager").withContractEndDate("2020-01-03").withIsEmployee(true).build();
    public static final Person DJUNHONG = new PersonBuilder().withName("Jun Hong").withPhone("12345678")
            .withEmail("junhong@email.com").withAddress("admiralty").withDepartment("Development")
            .withRole("Developer").withContractEndDate("2020-01-04").withIsEmployee(true).build();

    // Potentials
    public static final Person JEVAN = new PersonBuilder().withName("Jevan Lim").withPhone("12345678")
            .withEmail("jevan@example.com").withAddress("hougang ave").withDepartment("IT")
            .withRole("SWE").withContractEndDate("2020-01-01").withIsEmployee(true).build();

    public static final Person KEVIN = new PersonBuilder().withName("Kevin Lee").withPhone("87654321")
            .withEmail("kevin@example.com").withAddress("sengkang street").withDepartment("IT2")
            .withRole("SWE2").withContractEndDate("2020-01-02").withIsEmployee(true).build();

    public static final Person LILY = new PersonBuilder().withName("Lily B").withPhone("12348765")
            .withEmail("lily@example.com").withAddress("punggol cove").withDepartment("IT3")
            .withRole("SWE3").withContractEndDate("2020-01-03").withIsEmployee(true).build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withDepartment(VALID_DEPARTMENT_AMY)
            .withRole(VALID_ROLE_AMY).withContractEndDate(VALID_CONTRACT_END_DATE_AMY).withIsEmployee(true).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withDepartment(VALID_DEPARTMENT_BOB)
            .withRole(VALID_ROLE_BOB).withContractEndDate(VALID_CONTRACT_END_DATE_BOB).withIsEmployee(true).build();

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
        return new ArrayList<>(Arrays.asList(
                ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA, AMY, BOB, JEVAN, KEVIN, LILY));
    }
}
