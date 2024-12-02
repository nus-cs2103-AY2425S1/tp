package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    /**
     * Returns a list of sample students.
     */
    public static Student[] getSampleStudents() {
        return new Student[] {
            new Student(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Address("Blk 30 Geylang Street 29, #06-40"), new Schedule("Tuesday-1300-1500"),
                    new Subject("Mathematics"), new Rate("120.50"), new PaidAmount(), new OwedAmount("120.50")),
            new Student(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), new Schedule("Wednesday-1300-1500"),
                    new Subject("Mathematics"), new Rate("400.0"), new PaidAmount("800.00"), new OwedAmount("400.0")),
            new Student(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), new Schedule("Wednesday-1600-1800"),
                    new Subject("Physics"), new Rate("380.22"), new PaidAmount("1140.66"), new OwedAmount("0")),
            new Student(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), new Schedule("Thursday-1300-1500"),
                    new Subject("Chemistry"), new Rate("90.15"), new PaidAmount("450.75"), new OwedAmount("180.30")),
            new Student(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Address("Blk 47 Tampines Street 20, #17-35"), new Schedule("Thursday-1600-1800"),
                    new Subject("Physics"), new Rate("400"), new PaidAmount("400"), new OwedAmount("0")),
            new Student(new Name("Alex Tan"), new Phone("92624417"), new Email("alext@example.com"),
                new Address("Blk 45 Aljunied Street 85, #11-31"), new Schedule("Friday-1300-1500"),
                    new Subject("Economics"), new Rate("550.15"), new PaidAmount(), new OwedAmount("0")),
        };
    }

    /**
     * Returns a {@code ReadOnlyAddressBook} containing the students in the {@code SampleDataUtil}.
     */
    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Student sampleStudent : getSampleStudents()) {
            sampleAb.addStudent(sampleStudent);
        }
        return sampleAb;
    }


}
