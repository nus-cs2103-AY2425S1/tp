package seedu.address.logic.commands;

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

import java.util.List;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class BatchUnmarkCommand extends Command{
    public static final String COMMAND_WORD = "batch-unmark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unmarks the attendance of all students in the current list";

    public static final String MESSAGE_BATCH_UNMARK_SUCCESS = "Unmarked attendance for all students in this list";
    public static final String MESSAGE_BATCH_UNMARK_NO_STUDENT_LIST = "There is no student in this list";

    private boolean hasStudent;

    public BatchUnmarkCommand() {
        this.hasStudent = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException{
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        for (Person p : lastShownList) {
            if (p instanceof Student) {
                Student studentToUnmark = (Student) p;
                Student unmarkedStudent = createNewStudentWithMarkedAttendance(studentToUnmark);
                model.setPerson(p, unmarkedStudent);
            }
        }

        if (!this.hasStudent) {
            throw new CommandException(String.format(MESSAGE_BATCH_UNMARK_NO_STUDENT_LIST));
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_BATCH_UNMARK_SUCCESS));
    }

    /**
     * Creates and returns a {@code Student} with decremented AttendanceCount.
     */
    public Student createNewStudentWithMarkedAttendance(Student studentToUnmark) {
        assert studentToUnmark != null;

        this.hasStudent = true;
        Name name = studentToUnmark.getName();
        Role role = studentToUnmark.getRole();
        Phone phone = studentToUnmark.getPhone();
        Email email = studentToUnmark.getEmail();
        Address address = studentToUnmark.getAddress();
        Set<Tag> tags = studentToUnmark.getTags();
        AttendanceCount currentAttendanceCount = studentToUnmark.getAttendanceCount();
        Integer currentAttendanceCountInt = currentAttendanceCount.integerCount();
        if (currentAttendanceCountInt == 0) {
            return studentToUnmark;
        }
        Integer newAttendanceCountInt= studentToUnmark.getAttendanceCount().integerCount() - 1;
        String newAttendanceCountStr = newAttendanceCountInt.toString();
        AttendanceCount newAttendanceCount = new AttendanceCount(newAttendanceCountStr);
        return new Student(name, role, phone, email, address, tags, newAttendanceCount);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }

}
