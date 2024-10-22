package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Represents a Student in teletutor.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student extends Person {

    private static final Email DUMMY_EMAIL = new Email("dummy@example.com");
    private static final Address DUMMY_ADDRESS = new Address("dummy address");
    private static final Set<Tag> DUMMY_TAG = new HashSet<>();

    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();


    // Identity fields
    private final TutorialGroup tutorialGroup;
    private final StudentNumber studentNumber;
    private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, TutorialGroup tutorialGroup, StudentNumber studentNumber) {
        super(name, phone, DUMMY_EMAIL, DUMMY_ADDRESS, DUMMY_TAG);
        requireAllNonNull(tutorialGroup, studentNumber);
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
    }

    /**
     * Overloaded constructor to include assignments. (Used for EditStudentCommand)
     */
    public Student(Name name, Phone phone, TutorialGroup tutorialGroup,
                   StudentNumber studentNumber, ObservableList<Assignment> assignments) {
        super(name, phone, DUMMY_EMAIL, DUMMY_ADDRESS, DUMMY_TAG);
        requireAllNonNull(tutorialGroup, studentNumber);
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
        this.assignments.addAll(assignments);
    }

    public TutorialGroup getTutorialGroup() {
        return tutorialGroup;
    }

    public StudentNumber getStudentNumber() {
        return studentNumber;
    }

    public ObservableList<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Returns true if both students have the same student number.
     * This defines a weaker notion of equality between two students.
     */
    public boolean isSameStudent(Student otherStudent) {
        if (otherStudent == this) {
            return true;
        }

        return otherStudent != null
                && otherStudent.studentNumber.equals(studentNumber);
    }

    /**
     * Returns true if both students have the same identity and data fields.
     * This defines a stronger notion of equality between two students.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student otherStudent)) {
            return false;
        }

        return otherStudent.getName().equals(getName())
                && otherStudent.getPhone().equals(getPhone())
                && otherStudent.tutorialGroup.equals(tutorialGroup)
                && otherStudent.studentNumber.equals(studentNumber)
                && otherStudent.attendanceRecords.equals(attendanceRecords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", getName())
                .add("contactNumber", getPhone())
                .add("tutorialGroup", tutorialGroup)
                .add("studentNumber", studentNumber)
                .toString();
    }
    /**
     * Marks the attendance for a specific date.
     *
     * @param date The date on which attendance is being recorded.
     * @param status The attendance status, either 'present' or 'absent'.
     * @throws IllegalArgumentException if the provided status is nvalid.
     */
    public void markAttendance(LocalDate date, String status) {
        Attendance attendance = new Attendance(status);
        AttendanceRecord record = new AttendanceRecord(date, attendance);
        attendanceRecords.add(record);
    }

    //getters

    public List<AttendanceRecord> getAttendanceRecord() {
        return attendanceRecords;
    }

    public String getAttendanceRecordsString() {
        List<AttendanceRecord> sortedRecords = attendanceRecords.stream()
                .sorted(Comparator.comparing(AttendanceRecord::getDate))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        for (AttendanceRecord record : sortedRecords) {
            sb.append(record.toString()).append("\n");
        }
        return sb.toString();
    }


    /**
     * Adds an assignment
     *
     * @param assignment A valid assignment
     */
    public void addAssignment(Assignment assignment) {
        requireAllNonNull(assignment);
        assignments.add(assignment);
    }

    /**
     * Deletes the first assignment matching the given assignment query.
     *
     * @param assignmentQuery A valid assignment query.
     * @return the deleted assignment
     */
    public Assignment deleteAssignment(AssignmentQuery assignmentQuery) throws CommandException {
        requireAllNonNull(assignmentQuery);
        for (Assignment assignment : assignments) {
            if (assignmentQuery.match(assignment)) {
                assignments.remove(assignment);
                return assignment;
            }
        }
        return null;
    }

}
