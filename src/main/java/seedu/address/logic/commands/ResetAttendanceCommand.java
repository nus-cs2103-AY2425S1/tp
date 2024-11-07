package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;

/**
 * Resets the AttendanceCount of a person to 0.
 */
public class ResetAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "reset-att";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets attendance of a students selected students.";

    public static final String MESSAGE_RESET_ATTENDANCE_SUCCESS = "Attendance set to 0 for: %1$s";

    public static final String MESSAGE_BATCH_MARK_NO_STUDENT_LIST = "There is no student in this list";
    private boolean hasStudent = false;
    private List<Student> students = new ArrayList<>();



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
            Student markedStudent = createNewStudentWithZeroAttendance(p);
            model.setPerson(p, markedStudent);
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        String studentsReset = formatResetStudents(students);
        return new CommandResult(String.format(MESSAGE_RESET_ATTENDANCE_SUCCESS, studentsReset));

    }

    /**
     * Creates and returns a {@code STUDENT} with AttendanceCount of 0.
     */
    public static Student createNewStudentWithZeroAttendance(Student studentToReset) {
        assert studentToReset != null;

        Name name = studentToReset.getName();
        Role role = studentToReset.getRole();
        Phone phone = studentToReset.getPhone();
        Email email = studentToReset.getEmail();
        Address address = studentToReset.getAddress();
        Set<Tag> tags = studentToReset.getTags();
        return new Student(name, role, phone, email, address, tags);
    }


    /**
     * Formats a list of student names into a comma-separated string for display.
     *
     * @param students The list of students reset.
     * @return A comma-separated string of student names, or "none" if the list is empty.
     */
    private static String formatResetStudents(List<Student> students) {
        return students.stream()
                .map(person -> person.getName().toString())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("none");
    }
}
