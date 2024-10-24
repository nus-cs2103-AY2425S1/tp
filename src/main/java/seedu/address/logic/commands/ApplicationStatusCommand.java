package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the application status of an existing company in the address book
 */
public class ApplicationStatusCommand extends Command {
    public static final String COMMAND_WORD = "status";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the application status of the company identified "
            + "by the index number used in the last company listing. "
            + "Existing application status will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "as/ [STATUS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "status/ Ongoing";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "This command has yet to be implemented";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Status: %2$s";

    private final Index index;
    private final String status;

    /**
     * @param index of the company in the filtered company list to edit the remark
     * @param status of the company to be updated to
     */
    public ApplicationStatusCommand(Index index, String status) {
        requireAllNonNull(index, status);

        this.index = index;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), status));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof ApplicationStatusCommand)) {
            return false;
        }
        // state check
        ApplicationStatusCommand e = (ApplicationStatusCommand) other;
        return index.equals(e.index)
                && status.equals(e.status);
    }
}
