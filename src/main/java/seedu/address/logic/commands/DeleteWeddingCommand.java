package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.wedding.Wedding;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a wedding identified using its wedding name in the Wedlinker.
 */
public class DeleteWeddingCommand extends Command {
    public static final String COMMAND_WORD = "delete-wedding";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the wedding identified by the wedding name.\n"
            + "Parameters: WEDDING_NAME (must exist in the Wedlinker)\n"
            + "Example: " + COMMAND_WORD + " Timothy's Wedding";

    public static final String MESSAGE_DELETE_WEDDING_SUCCESS = "Deleted Wedding: %1$s";
    public static final String MESSAGE_DELETE_WEDDING_FAILURE_STILL_USED = "The Wedding: %1$s is still used";
    public static final String MESSAGE_DELETE_WEDDING_FAILURE_NOT_FOUND = "The Wedding: %1$s does not exist";

    private final Wedding targetWedding;

    public DeleteWeddingCommand(Wedding wedding) {
        targetWedding = wedding;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> allWeddings = model.getFilteredWeddingList();

        for (Wedding wedding : allWeddings) {
            if (wedding.getWeddingName().equals(targetWedding.getWeddingName())) {
                if (wedding.canBeDeleted()) {
                    model.deleteWedding(wedding);
                    return new CommandResult(String.format(MESSAGE_DELETE_WEDDING_SUCCESS, Messages.format(targetWedding)));
                } else {
                    throw new CommandException(
                            String.format(MESSAGE_DELETE_WEDDING_FAILURE_STILL_USED, Messages.format(targetWedding)));
                }
            }
        }
        throw new CommandException(String.format(MESSAGE_DELETE_WEDDING_FAILURE_NOT_FOUND, Messages.format(targetWedding)));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof DeleteWeddingCommand)) {
            return false;
        }

        DeleteWeddingCommand otherDeleteWeddingCommand = (DeleteWeddingCommand) obj;
        return targetWedding.equals(otherDeleteWeddingCommand.targetWedding);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetWedding", targetWedding)
                .toString();
    }
}
