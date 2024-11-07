package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.AttendanceCount;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;


/**
 * Marks the attendance of all students in the current list.
 */
public class BatchMarkCommand extends Command {
    public static final String COMMAND_WORD = "batch-mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of all students in the current list";

    public static final String MESSAGE_BATCH_MARK_SUCCESS = "Marked attendance for: %1$s";
    public static final String MESSAGE_BATCH_MARK_NO_STUDENT_LIST = "There is no student in this list";

    private boolean hasStudent;
    private List<Student> students = new ArrayList<>();


    public BatchMarkCommand() {
        this.hasStudent = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        for (Person p : lastShownList) {
            if (p instanceof Student) {
                this.hasStudent = true;
                Student studentToMark = (Student) p;
                students.add(studentToMark);
            }
        }

        if (!this.hasStudent) {
            throw new CommandException(String.format(MESSAGE_BATCH_MARK_NO_STUDENT_LIST));
        }

        for (Student p : students) {
            Student markedStudent = createNewStudentWithMarkedAttendance(p);
            model.setPerson(p, markedStudent);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String studentsMarked = formatMarkedStudents(students);

        return new CommandResult(String.format(MESSAGE_BATCH_MARK_SUCCESS, studentsMarked));
    }

    /**
     * Creates and returns a {@code Student} with incremented AttendanceCount.
     */
    public Student createNewStudentWithMarkedAttendance(Student studentToMark) {
        assert studentToMark != null;

        this.hasStudent = true;
        Name name = studentToMark.getName();
        Role role = studentToMark.getRole();
        Phone phone = studentToMark.getPhone();
        Email email = studentToMark.getEmail();
        Address address = studentToMark.getAddress();
        Set<Tag> tags = studentToMark.getTags();
        Integer newAttendanceCountInt = studentToMark.getAttendanceCount().integerCount() + 1;
        String newAttendanceCountStr = newAttendanceCountInt.toString();
        AttendanceCount newAttendanceCount = new AttendanceCount(newAttendanceCountStr);
        return new Student(name, role, phone, email, address, tags, newAttendanceCount);
    }

    /**
     * Formats a list of student names into a comma-separated string for display.
     *
     * @param students The list of students marked.
     * @return A comma-separated string of selected persons' names, or "none" if the list is empty.
     */
    public static String formatMarkedStudents(List<Student> students) {
        return students.stream()
                .map(person -> person.getName().toString()) // Convert Name object to String
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("none"); // Fallback if no persons are selected
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }

}
