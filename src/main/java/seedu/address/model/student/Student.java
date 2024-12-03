package seedu.address.model.student;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.AssignmentQuery;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.AttendanceRecord;

/**
 * Represents a Student in teletutor.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Student {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final TutorialGroup tutorialGroup;
    private final StudentNumber studentNumber;
    private final ObservableList<Assignment> assignments = FXCollections.observableArrayList();
    private final ObservableList<AttendanceRecord> attendanceRecords = FXCollections.observableArrayList();

    /**
     * Every field must be present and not null.
     */
    public Student(Name name, Phone phone, TutorialGroup tutorialGroup, StudentNumber studentNumber) {
        requireAllNonNull(name, phone, tutorialGroup, studentNumber);
        this.name = name;
        this.phone = phone;
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
    }

    /**
     * Overloaded constructor to include assignments. (Used for EditStudentCommand)
     */
    public Student(Name name, Phone phone, TutorialGroup tutorialGroup,
                   StudentNumber studentNumber, ObservableList<Assignment> assignments,
                   List<AttendanceRecord> attendanceRecords) {
        requireAllNonNull(name, phone, tutorialGroup, studentNumber, assignments, attendanceRecords);
        this.name = name;
        this.phone = phone;
        this.tutorialGroup = tutorialGroup;
        this.studentNumber = studentNumber;
        this.assignments.addAll(assignments);
        this.attendanceRecords.addAll(attendanceRecords);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
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

        return otherStudent.name.equals(name)
                && otherStudent.phone.equals(phone)
                && otherStudent.tutorialGroup.equals(tutorialGroup)
                && otherStudent.studentNumber.equals(studentNumber)
                && otherStudent.attendanceRecords.equals(attendanceRecords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("contactNumber", phone)
                .add("tutorialGroup", tutorialGroup)
                .add("studentNumber", studentNumber)
                .add("assignments", assignments)
                .add("attendanceRecords", attendanceRecords)
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
        record.notifyListeners();
    }

    //getters
    public ObservableList<AttendanceRecord> getAttendanceRecord() {
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
    public boolean addAssignment(Assignment assignment) {
        requireAllNonNull(assignment);

        for (Assignment assignment1 : assignments) {
            if (assignment1.isSameAssignment(assignment)) {
                return false;
            }
        }

        assignments.add(assignment);
        return true;
    }

    /**
     * Deletes the assignment matching the given name
     *
     * @param assignmentName A valid assignment query.
     * @return the deleted assignment
     */
    public Assignment deleteAssignment(AssignmentName assignmentName) {
        requireAllNonNull(assignmentName);
        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentName().equals(assignmentName)) {
                assignments.remove(assignment);
                return assignment;
            }
        }
        return null;
    }

    /**
     * Returns the assignment matching the name, null if no match found
     *
     * @param assignmentName A valid assignment name
     * @return the matching assignment
     */
    public Assignment getAssignment(AssignmentName assignmentName) {
        for (Assignment assignment : assignments) {
            if (assignment.getAssignmentName().equals(assignmentName)) {
                return assignment;
            }
        }
        return null;
    }

    /**
     * Edits the assignment matching the given name to the parameters specified
     *
     * @param assignmentQuery A valid assignment query.
     * @return the deleted assignment
     */
    public Assignment editAssignment(AssignmentName assignmentName, AssignmentQuery assignmentQuery) {
        requireAllNonNull(assignmentName);
        for (int i = 0; i < assignments.size(); i++) {
            Assignment assignment = assignments.get(i);
            if (assignment.getAssignmentName().equals(assignmentName)) {
                assignments.set(i, assignment.edit(assignmentQuery));
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
     * Deletes the last attendance record in the list.
     * @param date The date of the attendance record to be deleted.
     */
    public void deleteAttendance(LocalDate date) {
        Iterator<AttendanceRecord> iterator = attendanceRecords.iterator();
        while (iterator.hasNext()) {
            AttendanceRecord record = iterator.next();
            if (record.getDate().equals(date)) {
                iterator.remove();
                record.notifyListeners();
                return;
            }
        }
    }
}
