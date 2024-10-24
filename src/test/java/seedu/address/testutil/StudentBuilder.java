package seedu.address.testutil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Grade;
import seedu.address.model.assignment.Status;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.Student;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;


/**
 * A utility class to help with building StudentTest objects.
 */
public class StudentBuilder {
    public static final String DEFAULT_NAME = "Hugh Jackman";
    public static final String DEFAULT_CONTACT_NUMBER = "91234567";
    public static final String DEFAULT_TUTORIAL_GROUP = "G01";
    public static final String DEFAULT_STUDENT_NUMBER = "A1234568A";

    //=====================================Assignment default values=================================================
    public static final String DEFAULT_ASSIGNMENT_NAME = "Assignment 1";
    public static final String DEFAULT_DEADLINE = "2021-10-10";
    public static final String DEFAULT_SUBMISSION_STATUS = "Y";
    public static final String DEFAULT_GRADING_STATUS = "Y";
    public static final String DEFAULT_GRADE = "95";

    //========================Attendance default values ====================
    public static final String DEFAULT_ATTENDANCE_DATE = "2020-01-01";
    public static final String DEFAULT_ATTENDANCE_STATUS = "p";

    //=====================Identity fields==========================
    private Name name;
    private Phone phone;
    private TutorialGroup tutorialGroup;
    private StudentNumber studentNumber;

    private List<Assignment> assignments;

    //=============Attendance Fields==============
    private List<AttendanceRecord> attendanceRecords;

    /**
     * Creates a {@code StudentBuilder} with the default details.
     */
    public StudentBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_CONTACT_NUMBER);
        tutorialGroup = new TutorialGroup(DEFAULT_TUTORIAL_GROUP);
        studentNumber = new StudentNumber(DEFAULT_STUDENT_NUMBER);
        assignments = new ArrayList<>();
        Assignment assignment = new Assignment(
                new AssignmentName(DEFAULT_ASSIGNMENT_NAME),
                new Deadline(DEFAULT_DEADLINE),
                new Status(DEFAULT_SUBMISSION_STATUS),
                new Status(DEFAULT_GRADING_STATUS),
                new Grade(DEFAULT_GRADE)
        );
        assignments.add(assignment);
        attendanceRecords = new ArrayList<>();
        Attendance attendance = new Attendance(DEFAULT_ATTENDANCE_STATUS);
        AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDate.parse(DEFAULT_ATTENDANCE_DATE), attendance);
        attendanceRecords.add(attendanceRecord);
    }

    /**
     * Initializes the StudentBuilder with the data of {@code studentToCopy}.
     */
    public StudentBuilder(Student studentToCopy) {
        name = studentToCopy.getName();
        phone = studentToCopy.getPhone();
        tutorialGroup = studentToCopy.getTutorialGroup();
        studentNumber = studentToCopy.getStudentNumber();
        assignments = studentToCopy.getAssignments();
        attendanceRecords = studentToCopy.getAttendanceRecord();
    }

    /**
     * Sets the {@code Name} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code TutorialGroup} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withTutorialGroup(String tutorialGroup) {
        this.tutorialGroup = new TutorialGroup(tutorialGroup);
        return this;
    }

    /**
     * Sets the {@code StudentNumber} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withStudentNumber(String studentNumber) {
        this.studentNumber = new StudentNumber(studentNumber);
        return this;
    }

    /**
     * Sets the {@code Assignment} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withAssignment(String assignmentName, String deadline, String submissionStatus,
                                         String gradingStatus, String grade) {
        Assignment assignment = new Assignment(
                new AssignmentName(assignmentName),
                new Deadline(deadline),
                new Status(submissionStatus),
                new Status(gradingStatus),
                new Grade(grade)
        );
        assignments.add(assignment);
        return this;
    }

    /**
     * Sets the {@code AttendanceRecord} of the {@code StudentTest} that we are building.
     */
    public StudentBuilder withAttendanceRecord(LocalDate date, String status) {
        Attendance attendance = new Attendance(status);
        AttendanceRecord attendanceRecord = new AttendanceRecord(date, attendance);
        attendanceRecords.add(attendanceRecord);
        return this;
    }


    public Student build() {
        return new Student(name, phone, tutorialGroup, studentNumber);
    }
}
