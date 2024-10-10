package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.StudentNumber;

public class DeleteStudentCommand extends Command {
    public static final String COMMAND_WORD = "deletestu";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the student identified by the student number used in the displayed student list.\n"
            + "Parameters: STUDENT_NUMBER (must be a valid student number)\n"
            + "Example: " + COMMAND_WORD + " A1234567B";

    public static final String MESSAGE_DELETE_STUDENT_SUCCESS = "Deleted Student: %1$s";

    private final StudentNumber targetStudentNumber;

    public DeleteStudentCommand(StudentNumber targetStudentNumber) {
        this.targetStudentNumber = targetStudentNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteStudentCommand)) {
            return false;
        }

        DeleteStudentCommand otherDeleteStudentCommand = (DeleteStudentCommand) other;
        return targetStudentNumber.equals(otherDeleteStudentCommand.targetStudentNumber);
    }
}
