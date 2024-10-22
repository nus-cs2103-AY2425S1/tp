package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.student.Student;

/**
 * Views an existing student in the address book.
 */
public class ViewStudentCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all students whose names are exactly "
            + "the specified name (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " Alice Yeoh";

    public static final String MESSAGE_SUCCESS = "Viewing %1$d students with name %2$s";
    public static final String MESSAGE_NO_SUCH_STUDENT = "There is no such student with that name.";

    private final Name name;

    /**
     * Creates a ViewStudentCommand to view students with the specified {@code Name}.
     * @param name Name of student to view.
     */
    public ViewStudentCommand(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Student studentWithSpecifiedName = model.getStudentByName(name);

        if (studentWithSpecifiedName == null) {
            throw new CommandException(MESSAGE_NO_SUCH_STUDENT);
        }

        Predicate<Student> predicate = student -> student.getName().equals(name);
        model.updateFilteredStudentList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getFilteredStudentList().size(), name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewStudentCommand)) {
            return false;
        }

        ViewStudentCommand otherViewStudentCommand = (ViewStudentCommand) other;
        return name.equals(otherViewStudentCommand.name);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .toString();
    }

    @Override
    public boolean undo(Model model) {
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return true;
    }
}
