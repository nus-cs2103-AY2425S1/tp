package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.student.Student;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_DATE = "2024-10-20";
    public static final String DEFAULT_TIME = "14:00";
    private Date date;
    private Time time;
    private final List<Student> students;
    private final Map<Student, Boolean> attendanceMap;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        students = new ArrayList<>();
        attendanceMap = new HashMap<>();
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        date = lessonToCopy.getDate();
        time = lessonToCopy.getTime();
        students = new ArrayList<>(lessonToCopy.getStudents());
        attendanceMap = new HashMap<>(lessonToCopy.getAttendanceMap());
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Adds a {@code Student} to the {@code Lesson} that we are building.
     */
    public LessonBuilder withStudent(Student student) {
        requireNonNull(student);
        this.students.add(student);
        this.attendanceMap.put(student, false); // Student must also be added to the attendanceMap
        return this;
    }

    /**
     * Sets the {@code Student}'s attendance in the {@code Lesson} we are building.
     */
    public LessonBuilder withAttendance(Student student, boolean attendance) {
        requireNonNull(student);
        this.attendanceMap.put(student, attendance);
        return this;
    }

    /**
     * Builds and returns the Lesson.
     */
    public Lesson build() {
        return new Lesson(date, time, students, attendanceMap);
    }
}
