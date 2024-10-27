package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Deletes all students, clearing the list of students.
 */
public class DeleteAllStudentsCommand extends Command {
    public static final String COMMAND_WORD = "deleteallstudents";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all students from the student list.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted all students.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.deleteAllStudents();
        return new CommandResult(MESSAGE_DELETE_STUDENT_SUCCESS);
    }
}
