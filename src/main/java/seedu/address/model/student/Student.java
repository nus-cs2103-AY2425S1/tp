package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
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
                   StudentNumber studentNumber, ObservableList<Assignment> assignments,
                   List<AttendanceRecord> attendanceRecords) {
        super(name, phone, DUMMY_EMAIL, DUMMY_ADDRESS, DUMMY_TAG);
        requireAllNonNull(tutorialGroup, studentNumber);
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
        this.assignments.addAll(assignments);
        this.attendanceRecords.addAll(attendanceRecords);
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
     * @throws IllegalArgumentException if the provided status is invalid.
     */
    public void markAttendance(LocalDate date, String status) {
        Attendance attendance = new Attendance(status);
        for (AttendanceRecord ar : attendanceRecords) {
            if (ar.getDate().equals(date)) {
                ar.setAttendance(attendance);
                return;
            }
        }
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
     * Adds an assignment at the specified index.
     * @param index The index to add the assignment at.
     * @param assignment A valid assignment.
     */
    public void addAssignment(int index, Assignment assignment) {
        assert index >= 0 && index <= assignments.size();
        requireAllNonNull(assignment);
        assignments.add(index, assignment);
    }

    /**
     * Returns the first index matching the given assignment query. If no such assignment is found, returns -1.
     *
     * @param assignmentQuery A valid assignment query.
     * @return the index of the first assignment matching the query.
     */
    public int getAssignmentIndex(AssignmentQuery assignmentQuery) {
        requireAllNonNull(assignmentQuery);
        for (int i = 0; i < assignments.size(); i++) {
            if (assignmentQuery.match(assignments.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Deletes the assignment at the specified index.
     *
     * @param index A valid index.
     * @return the deleted assignment
     */
    public Assignment deleteAssignment(int index) {
        assert index >= 0 && index < assignments.size();
        return assignments.remove(index);
    }

    /**
     * Deletes the first assignment matching the given assignment query.
     *
     * @param assignmentQuery A valid assignment query.
     * @return the deleted assignment
     */
    public Assignment deleteAssignment(AssignmentQuery assignmentQuery) {
        requireAllNonNull(assignmentQuery);
        for (Assignment assignment : assignments) {
            if (assignmentQuery.match(assignment)) {
                assignments.remove(assignment);
                return assignment;
            }
        }
        return null;
    }

    /**
     * Adds the attendance record to the attendance records
     *
     * @param ar A valid attendance record
     */
    public void addAttendanceRecord(AttendanceRecord ar) {
        attendanceRecords.add(ar);
    }

    /**
     * Deletes the last assignment in the list.
     */
    public void deleteLastAssignment() {
        assignments.remove(assignments.size() - 1);
    }

    /**
     * Deletes the last attendance record in the list.
     * @param date The date of the attendance record to be deleted.
     */
    public void undoAttendance(LocalDate date) {
        Iterator<AttendanceRecord> iterator = attendanceRecords.iterator();
        while (iterator.hasNext()) {
            AttendanceRecord record = iterator.next();
            if (record.getDate().equals(date)) {
                iterator.remove();
                return;
            }
        }
    }
}
