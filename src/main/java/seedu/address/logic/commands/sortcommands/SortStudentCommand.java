package seedu.address.logic.commands.sortcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.ListMarkers.LIST_STUDENT_MARKER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.VersionHistory;
import seedu.address.model.student.Student;

/**
 * Sorts all the students in the address book.
 */
public class SortStudentCommand extends Command {

    public static final String COMMAND_WORD = "sort_s";
    public static final String COMMAND_WORD_ALIAS = "ss";

    public static final String MESSAGE_USAGE = COMMAND_WORD + "/" + COMMAND_WORD_ALIAS
        + ": Sorts all students by student name in alphabetical order.\n"
        + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all students by student name in alphabetical order";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.sortPersonList(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().getFullName().compareTo(s2.getName().getFullName());
            }
        });
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.setStateStudents();
        return new CommandResult(MESSAGE_SUCCESS, LIST_STUDENT_MARKER);
    }

    @Override
    public VersionHistory updateVersionHistory(VersionHistory versionHistory, Model model) throws CommandException {
        versionHistory.addVersion(model);
        return versionHistory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .toString();
    }
}
