package seedu.address.testutil;

import static seedu.address.testutil.TypicalConsultations.getTypicalConsultations;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;

import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.student.Student;

/**
 * A utility class containing a typical AddressBook with sample data for testing.
 */
public class TypicalAddressBook {

    private TypicalAddressBook() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with typical students and consults.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Student student : getTypicalStudents()) {
            ab.addStudent(student);
        }
        for (Consultation consult : getTypicalConsultations()) {
            ab.addConsult(consult);
        }
        return ab;
    }
}
