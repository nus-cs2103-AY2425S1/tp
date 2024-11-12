package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withStudent("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Student} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withStudent(Student student) {
        addressBook.addStudent(student);
        return this;
    }

    /**
     * Adds a new {@code Consultation} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withConsultation(Consultation consultation) {
        addressBook.addConsult(consultation);
        return this;
    }

    /**
     * Adds a new {@code Lesson} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withLesson(Lesson lesson) {
        addressBook.addLesson(lesson);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
