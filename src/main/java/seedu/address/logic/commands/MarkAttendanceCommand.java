package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.person.Person;

/**
 * Marks the attendance of all students.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the attendance of all students.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Attendance marked successfully.";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    @Override
    public CommandResult executeCommand(Model model) throws CommandException {
        requireNonNull(model);
        logger.info("Starting to execute mark attendance command.");

        List<Person> filteredPersonList = model.getFilteredPersonList();
        if (filteredPersonList.isEmpty()) {
            logger.warning("No students found in the model to mark attendance.");
            throw new CommandException(Messages.MESSAGE_NO_STUDENTS);
        }

        model.markAttendance();

        logger.info("Attendance marked successfully.");
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof MarkAttendanceCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
