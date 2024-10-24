package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDUSTRY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MADE_PAYMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_YEAR1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.company.Company;
import seedu.address.model.person.student.Student;

/**
 * A utility class containing a list of typical {@code Student} and {@code Company} objects to be used in tests.
 */
public class TypicalContacts {

    // Typical students
    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
            .withStudentID("A1234567X")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
            .withStudentID("A7654321X")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("benson@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz")
            .withStudentID("A1234567Y")
            .withPhone("95352563")
            .withEmail("carl@example.com").withAddress("Wall Street").build();

    // Typical companies
    public static final Company HOON = new CompanyBuilder().withName("Hoon Meier")
            .withIndustry("Technology")
            .withPhone("8482424")
            .withEmail("hoon@example.com").withAddress("Little India").build();
    public static final Company IDA = new CompanyBuilder().withName("Ida Mueller")
            .withIndustry("Finance")
            .withPhone("8482131")
            .withEmail("ida@example.com").withAddress("Chicago Ave").build();

    public static final Company ABANK = new CompanyBuilder().withName("Amazing Bank")
            .withIndustry("Banking")
            .withPhone("84871319")
            .withEmail("abank@example.com").withAddress("Money Fly").withTags("loyalPartner", "reliable").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
            .withStudentID(VALID_STUDENTID_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_MADE_PAYMENT).build();
    public static final Company BOB = new CompanyBuilder().withName(VALID_NAME_BOB)
            .withIndustry(VALID_INDUSTRY_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_YEAR1, VALID_TAG_MADE_PAYMENT)
            .build();

    private TypicalContacts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students and companies.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addPerson(student);
        }
        for (Company company : getTypicalCompanies()) {
            ab.addPerson(company);
        }
        return ab;
    }

    /**
     * Returns a list of typical students.
     */
    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
    }

    /**
     * Returns a list of typical companies.
     */
    public static List<Company> getTypicalCompanies() {
        return new ArrayList<>(Arrays.asList(HOON, IDA, ABANK));
    }
}
