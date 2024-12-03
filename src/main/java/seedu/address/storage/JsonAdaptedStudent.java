package seedu.address.storage;

import static seedu.address.logic.commands.AddAssignmentCommand.MESSAGE_DUPLICATE_ASSIGNMENT_FOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.student.Name;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;
import seedu.address.storage.attendance.JsonAdaptedAttendanceRecord;

/**
 * Jackson-friendly version of {@link Student}.
 */
public class JsonAdaptedStudent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";
    private final String name;
    private final String phone;
    private final String studentNumber;
    private final String tutorialGroup;

    private final List<JsonAdaptedAssignment> assignments = new ArrayList<>();
    private final List<JsonAdaptedAttendanceRecord> attendanceRecord = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedStudent} with the given student details.
     */
    @JsonCreator
    public JsonAdaptedStudent(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("tutorialGroup") String tutorialGroup,
                              @JsonProperty("studentNumber") String studentNumber,
                              @JsonProperty("assignments") List<JsonAdaptedAssignment> assignments,
                              @JsonProperty("attendanceRecord") List<JsonAdaptedAttendanceRecord> attendanceRecord) {
        this.name = name;
        this.phone = phone;
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
        if (assignments != null) {
            this.assignments.addAll(assignments);
        }
        if (attendanceRecord != null) {
            this.attendanceRecord.addAll(attendanceRecord);
        }
    }

    /**
     * Converts a given {@code Student} into this class for Jackson use.
     */
    public JsonAdaptedStudent(Student source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        studentNumber = source.getStudentNumber().value;
        tutorialGroup = source.getTutorialGroup().value;
        assignments.addAll(source.getAssignments().stream()
                .map(JsonAdaptedAssignment::new)
                .collect(Collectors.toList()));
        attendanceRecord.addAll(source.getAttendanceRecord().stream()
                .map(JsonAdaptedAttendanceRecord::new)
                .collect(Collectors.toList()));
    }


    /**
     * Converts this Jackson-friendly adapted student object into the model's {@code Student} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted student.
     */
    public Student toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }

        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }

        final Phone modelPhone = new Phone(phone);

        if (tutorialGroup == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TutorialGroup.class.getSimpleName()));
        }
        if (!TutorialGroup.isValidTutorialGroup(tutorialGroup)) {
            throw new IllegalValueException(TutorialGroup.MESSAGE_CONSTRAINTS);
        }
        final TutorialGroup modelTutorialGroup = new TutorialGroup(tutorialGroup);

        if (studentNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    StudentNumber.class.getSimpleName()));
        }

        if (!StudentNumber.isValidStudentNumber(studentNumber)) {
            throw new IllegalValueException(StudentNumber.MESSAGE_CONSTRAINTS);
        }
        final StudentNumber modelStudentNumber = new StudentNumber(studentNumber);

        Student student = new Student(modelName, modelPhone, modelTutorialGroup, modelStudentNumber);

        for (JsonAdaptedAssignment assignment : assignments) {
            Assignment modelAssignment = assignment.toModelType();
            if (!student.addAssignment(modelAssignment)) {
                throw new IllegalValueException(String.format(MESSAGE_DUPLICATE_ASSIGNMENT_FOUND,
                        modelAssignment.getAssignmentName(), student.getName()));
            }
        }

        for (JsonAdaptedAttendanceRecord record : attendanceRecord) {
            AttendanceRecord modelAttendanceRecord = record.toModelType();
            student.addAttendanceRecord(modelAttendanceRecord);
        }


        return student;


    }

}
