package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Detailed View of a patient's session log identified using it's displayed index from the patient's session log list.
 */
public class DetailedViewCommand extends Command{

    public static final String COMMAND_WORD = "detailed view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the specific session log identified by the index number used in the displayed list log list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DETAILED_VIEW_SUCCESS = "VIEW LOG: %1$s";

    private final Index targetIndex;

    public DetailedViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //TODO
        //Refer to DeleteCommand to show how to read based on the last index.
        return null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DetailedViewCommand)) {
            return false;
        }

        DetailedViewCommand otherDetailedViewCommand = (DetailedViewCommand) other;
        return targetIndex.equals(otherDetailedViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
