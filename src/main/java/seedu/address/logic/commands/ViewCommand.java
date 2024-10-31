package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Name;
import seedu.address.model.student.Student;

/**
 * Views the details of an existing student in the address book.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views the details of the student identified.\n"
            + "Parameters: " + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe";

    public static final String MESSAGE_VIEW_SUCCESS = "Displayed details of Student: %1$s";

    public final Name name;

    /**
     * @param name of the student in the filtered student list to view details
     */
    public ViewCommand(Name name) {
        requireAllNonNull(name);
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Student> allStudents = model.getAddressBook().getStudentList();
        Student studentToView = null;

        for (Student student : allStudents) {
            if (student.getName().equals(name)) {
                studentToView = student;
            }
        }

        if (studentToView == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_NAME);
        }

        return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, name),
                studentToView);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }
        // state check
        ViewCommand e = (ViewCommand) other;
        return name.equals(e.name);
    }

}
