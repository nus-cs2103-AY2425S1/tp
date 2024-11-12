package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.lesson.StudentLessonInfo;
import seedu.address.model.student.Student;

/**
 * Jackson-friendly version of {@link StudentLessonInfo}.
 */
class JsonAdaptedStudentLessonInfo {
    public static final String STUDENT_NOT_FOUND_MESSAGE = "Student %s"
            + " does not exist in the address book, or details do not match!";
    private final JsonAdaptedStudent student;
    private final boolean attendance;
    private final int participationScore;

    /**
     * Constructs a {@code JsonAdaptedCourse} with the given arguments.
     */
    @JsonCreator
    public JsonAdaptedStudentLessonInfo(@JsonProperty("student") JsonAdaptedStudent student,
                                        @JsonProperty("attendance") boolean attendance,
                                        @JsonProperty("participation") int participationScore) {
        this.student = student;
        this.attendance = attendance;
        this.participationScore = participationScore;
    }

    /**
     * Converts a given {@code StudentLessonInfo} into this class for Jackson use.
     */
    public JsonAdaptedStudentLessonInfo(StudentLessonInfo studentLessonInfo) {
        this.student = new JsonAdaptedStudent(studentLessonInfo.getStudent());
        this.attendance = studentLessonInfo.getAttendance();
        this.participationScore = studentLessonInfo.getParticipationScore();
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Course} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public StudentLessonInfo toModelType(AddressBook addressBook) throws IllegalValueException {
        Student modelStudent = student.toModelType();

        if (!addressBook.hasStudent(modelStudent)) {
            throw new IllegalValueException(
                    String.format(STUDENT_NOT_FOUND_MESSAGE, modelStudent.getName().fullName));
        }
        return new StudentLessonInfo(student.toModelType(), attendance, participationScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof JsonAdaptedStudentLessonInfo)) {
            return false;
        }

        JsonAdaptedStudentLessonInfo otherInfo = (JsonAdaptedStudentLessonInfo) other;
        return student.equals(otherInfo.student)
                && attendance == otherInfo.attendance
                && participationScore == otherInfo.participationScore;
    }

    /**
     * Returns true if the student field is not null.
     * Required because sometimes the constructor is called with student = null when reading
     * from JSON if the file is corrupted, and there is no "default" value to give it
     * (in contrast to a List for example).
     */
    public boolean isNonNull() {
        return this.student != null;
    }
}
