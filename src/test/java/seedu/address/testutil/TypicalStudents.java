package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWED_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OWED_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAID_AMOUNT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PAID_AMOUNT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.student.Student;

/**
 * A utility class containing a list of {@code Student} objects to be used in tests.
 */
public class TypicalStudents {

    public static final Student ALICE = new StudentBuilder().withName("Alice Pauline")
                                                            .withAddress("123, Jurong West Ave 6, #08-111")
                                                            .withEmail("alice@example.com")
                                                            .withPhone("94351253").withSchedule("Sunday-1800-1900")
                                                            .withSubject("Economics")
                                                            .withRate("250.50").withPaidAmount("0")
                                                            .withOwedAmount("250.50").build();
    public static final Student BENSON = new StudentBuilder().withName("Benson Meier")
                                                             .withAddress("311, Clementi Ave 2, #02-25")
                                                             .withEmail("johnd@example.com")
                                                             .withPhone("98765432").withSchedule("Monday-1800-1900")
                                                             .withSubject("Mathematics").withRate("100.80")
                                                             .withPaidAmount("201.6").withOwedAmount("0").build();
    public static final Student CARL = new StudentBuilder().withName("Carl Kurz").withPhone("95352563")
                                                           .withEmail("heinz@example.com").withAddress("wall street")
                                                           .withSchedule("Tuesday-1800-1900").withSubject("Mathematics")
                                                           .withRate("300")
                                                           .withPaidAmount("1200").withOwedAmount("900").build();
    public static final Student DANIEL = new StudentBuilder().withName("Daniel Meier").withPhone("87652533")
                                                             .withEmail("cornelia@example.com")
                                                             .withAddress("10th street")
                                                             .withSchedule("Wednesday-1800-1900")
                                                             .withSubject("Mathematics").withRate("450.5")
                                                             .withPaidAmount("450.5").withOwedAmount("901.00").build();
    public static final Student ELLE = new StudentBuilder().withName("Elle Meyer").withPhone("94802224")
                                                           .withEmail("werner@example.com").withAddress("michegan ave")
                                                           .withSchedule("Thursday-1800-1900")
                                                           .withSubject("Mathematics").withRate("350")
                                                           .withPaidAmount("2800").withOwedAmount("0").build();
    public static final Student FIONA = new StudentBuilder().withName("Fiona Kunz").withPhone("94824270")
                                                            .withEmail("lydia@example.com").withAddress("little tokyo")
                                                            .withSchedule("Friday-1800-1900").withSubject("Mathematics")
                                                            .withRate("260.25")
                                                            .withPaidAmount("0").withOwedAmount("520.50").build();
    public static final Student GEORGE = new StudentBuilder().withName("George Best").withPhone("94824420")
                                                             .withEmail("anna@example.com").withAddress("4th street")
                                                             .withSchedule("Saturday-1800-1900")
                                                             .withSubject("Mathematics").withRate("120")
                                                             .withPaidAmount("1200").withOwedAmount("0").build();

    // Manually added
    public static final Student HOON = new StudentBuilder().withName("Hoon Meier").withPhone("84820424")
                                                           .withEmail("stefan@example.com").withAddress("little india")
                                                           .withSchedule("Sunday-1800-1900")
                                                           .withSubject("Science").withRate("200")
                                                           .withPaidAmount("8000").withOwedAmount("1000").build();
    public static final Student IDA = new StudentBuilder().withName("Ida Mueller").withPhone("84820131")
                                                          .withEmail("hans@example.com").withAddress("chicago ave")
                                                          .withSchedule("Monday-1800-1900")
                                                          .withSubject("Economics").withRate("450")
                                                          .withPaidAmount("900").withOwedAmount("900").build();

    // Manually added - Student's details found in {@code CommandTestUtil}
    public static final Student AMY = new StudentBuilder().withName(VALID_NAME_AMY)
                                                          .withPhone(VALID_PHONE_AMY)
                                                          .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                                                          .withSchedule(VALID_SCHEDULE_AMY)
                                                          .withRate(VALID_RATE_AMY)
                                                          .withPaidAmount(VALID_PAID_AMOUNT_AMY)
                                                          .withOwedAmount(VALID_OWED_AMOUNT_AMY).build();
    public static final Student BOB = new StudentBuilder().withName(VALID_NAME_BOB)
                                                          .withPhone(VALID_PHONE_BOB)
                                                          .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                                                          .withSchedule(VALID_SCHEDULE_BOB)
                                                          .withRate(VALID_RATE_BOB)
                                                          .withPaidAmount(VALID_PAID_AMOUNT_BOB)
                                                          .withOwedAmount(VALID_OWED_AMOUNT_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalStudents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical students.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        return ab;
    }

    public static List<Student> getTypicalStudents() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
