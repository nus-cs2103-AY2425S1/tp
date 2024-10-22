package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.consultation.Consultation;
import seedu.address.model.consultation.Date;
import seedu.address.model.consultation.Time;
import seedu.address.model.student.Student;

/**
 * Jackson-friendly version of {@link Consultation}.
 */
class JsonAdaptedConsultation {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Consultation's %s field is missing!";
    public static final String STUDENT_NOT_FOUND_MESSAGE =
            "Student %s does not exist in TAHub, or details do not match!";
    private final String date;
    private final String time;
    private final List<JsonAdaptedStudent> students = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedConsultation} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedConsultation(@JsonProperty("date") String date, @JsonProperty("time") String time,
                                   @JsonProperty("students") List<JsonAdaptedStudent> students) {
        this.date = date;
        this.time = time;
        if (students != null) {
            this.students.addAll(students);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedConsultation(Consultation consult) {
        date = consult.getDate().getValue();
        time = consult.getTime().getValue();
        students.addAll(consult.getStudents().stream()
                .map(JsonAdaptedStudent::new)
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted consultation object into the model's {@code Consultation} object.
     *
     * @param addressBook AddressBook instance to verify that the student(s) exist in.
     * @throws IllegalValueException if there were any data constraints violated in the adapted consultation.
     */
    public Consultation toModelType(AddressBook addressBook) throws IllegalValueException {

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        final List<Student> modelStudents = new ArrayList<>();

        for (JsonAdaptedStudent student : students) {
            Student modelStudent = student.toModelType();
            // check to ensure student data matches an existing student
            if (!addressBook.hasStudent(modelStudent)) {
                throw new IllegalValueException(
                        String.format(STUDENT_NOT_FOUND_MESSAGE, modelStudent.getName().fullName));
            }
            modelStudents.add(modelStudent);
        }

        return new Consultation(modelDate, modelTime, modelStudents);
    }

}
