package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Tutorial;

/**
 * Marks attendance of an existing person in the address book.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance for the contact "
            + "by the index number used in the last person listing and for the tutorial number inputted. "
            + "Parameters: INDEX (must be a positive integer) "
            + "t/TUTORIAL\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "t/1";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "Mark command has not be implemented";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Tutorial: %2$s";

    private final Index index;
    private final Tutorial tutorial;

    /**
     * @param index of the person in the display list
     * @param tutorial number to mark attendance for
     */
    public MarkCommand(Index index, Tutorial tutorial) {
        this.index = index;
        this.tutorial = tutorial;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), tutorial));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkCommand)) {
            return false;
        }

        // state check
        MarkCommand e = (MarkCommand) other;
        return index.equals(e.index)
                && tutorial.equals(e.tutorial);
    }
}