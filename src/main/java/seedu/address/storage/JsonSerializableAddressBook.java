package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_STUDENT = "Students list contains duplicate student(s).";
    public static final String MESSAGE_DUPLICATE_LESSON = "Lessons list contains duplicate lesson(s).";

    private final List<JsonAdaptedStudent> students = new ArrayList<>();
    private final List<JsonAdaptedConsultation> consults = new ArrayList<>();
    private final List<JsonAdaptedLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given data.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("students") List<JsonAdaptedStudent> students,
            @JsonProperty("consults") List<JsonAdaptedConsultation> consults,
            @JsonProperty("lessons") List<JsonAdaptedLesson> lessons) {
        // Null checks are required in case an address book has students but no consults
        // or lessons.
        if (students != null) {
            this.students.addAll(students);
        }
        if (consults != null) {
            this.consults.addAll(consults);
        }
        if (lessons != null) {
            this.lessons.addAll(lessons);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created
     *               {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        students.addAll(source.getStudentList().stream().map(JsonAdaptedStudent::new).toList());
        consults.addAll(source.getConsultList().stream().map(JsonAdaptedConsultation::new).toList());
        lessons.addAll(source.getLessonList().stream().map(JsonAdaptedLesson::new).toList());
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedStudent jsonAdaptedStudent : students) {
            Student student = jsonAdaptedStudent.toModelType();
            if (addressBook.hasStudent(student)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STUDENT);
            }
            addressBook.addStudent(student);
        }
        for (JsonAdaptedConsultation jsonAdaptedConsultation : consults) {
            Consultation consult = jsonAdaptedConsultation.toModelType(addressBook);
            addressBook.addConsult(consult);
        }
        for (JsonAdaptedLesson jsonAdaptedLesson : lessons) {
            Lesson lesson = jsonAdaptedLesson.toModelType(addressBook);
            if (addressBook.hasLesson(lesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            addressBook.addLesson(lesson);
        }
        return addressBook;
    }
}
