package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Lesson's %s field is missing!";
    public static final String STUDENT_NOT_FOUND_MESSAGE = "Student %s"
            + " does not exist in the address book, or details do not match!";

    private final String date;
    private final String time;
    private final List<JsonAdaptedStudent> students;
    /**
     * Note: This is using a custom Pair class, not the one by JavaFX.
     * Attempting to use JavaFX's Pair class causes InaccessibleObjectException on GitHub CI.
     * Using a Map is not feasible because there is no map key deserializer available.
     */
    private final List<Pair<JsonAdaptedStudent, Boolean>> attendanceList;

    /**
     * Constructs a {@code JsonAdaptedLesson} with the given lesson details.
     */
    @JsonCreator
    public JsonAdaptedLesson(@JsonProperty("date") String date,
            @JsonProperty("time") String time,
            @JsonProperty("students") List<JsonAdaptedStudent> students,
            @JsonProperty("attendanceList") List<Pair<JsonAdaptedStudent, Boolean>> attendanceList) {
        this.date = date;
        this.time = time;
        this.students = new ArrayList<>();
        this.attendanceList = new ArrayList<>();
        // Null check required because the JsonCreator constructor may be called with null if the fields do not exist
        if (students != null) {
            this.students.addAll(students);
        }
        if (attendanceList != null) {
            this.attendanceList.addAll(attendanceList);
        }
    }

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    public JsonAdaptedLesson(Lesson source) {
        date = source.getDate().toString();
        time = source.getTime().toString();
        students = new ArrayList<>();
        attendanceList = new ArrayList<>();

        for (Student student : source.getStudents()) {
            JsonAdaptedStudent jsonAdaptedStudent = new JsonAdaptedStudent(student);
            boolean attendance = source.getAttendance(student);
            students.add(jsonAdaptedStudent);
            attendanceList.add(new Pair<>(jsonAdaptedStudent, attendance));
        }
    }

    /**
     * Returns the date of the lesson.
     *
     * @return A string representing the date of the lesson.
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the time of the lesson.
     *
     * @return A string representing the time of the lesson.
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns a list of students attending the lesson.
     *
     * @return A new list containing the {@code JsonAdaptedStudent} objects
     *         representing the students.
     */
    public List<JsonAdaptedStudent> getStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's
     * {@code Lesson} object.
     *
     * @param addressBook AddressBook instance to verify that the student(s) exist
     *                    in.
     * @throws IllegalValueException if there were any data constraints violated in
     *                               the adapted lesson.
     */
    public Lesson toModelType(AddressBook addressBook) throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Date"));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Time"));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);

        final List<Student> modelStudents = new ArrayList<>();
        final HashMap<Student, Boolean> modelAttendanceList = new HashMap<>();

        // The loop only checks using students in the students list, thus ignoring any "extra" entries
        // in the attendanceList.
        for (JsonAdaptedStudent student : students) {
            Student modelStudent = student.toModelType();
            // Check to ensure student data matches an existing student
            if (!addressBook.hasStudent(modelStudent)) {
                throw new IllegalValueException(
                        String.format(STUDENT_NOT_FOUND_MESSAGE, modelStudent.getName().fullName));
            }
            modelStudents.add(modelStudent);
            modelAttendanceList.put(modelStudent, getAttendance(student));
        }

        return new Lesson(modelDate, modelTime, modelStudents, modelAttendanceList);
    }

    /**
     * Returns the attendance of this student as stored in the attendanceList.
     * If this student's entry is not found in the attendanceList, defaults to false.
     *
     * @param jsonStudent JsonAdaptedStudent representing the student to search for.
     * @return Boolean representing the student's attendance for this lesson, or false if no entry is found.
     */
    private boolean getAttendance(JsonAdaptedStudent jsonStudent) {
        for (Pair<JsonAdaptedStudent, Boolean> pair : attendanceList) {
            if (pair.getKey().equals(jsonStudent)) {
                return pair.getValue();
            }
        }
        return false; // the default attendance is false if data is not found
    }
}
