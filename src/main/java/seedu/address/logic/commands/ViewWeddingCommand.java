package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonInWeddingPredicate;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * Displays a list of contacts involved in the specified wedding.
 */
public class ViewWeddingCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": views contacts involved in a wedding of given index \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS =
            "Listed all contacts in wedding: %1$s \n"
                    + "Type \"list\" to see all contacts.";

    private final Index targetIndex;

    /**
     * Creates a ViewWeddingCommand object with the specified tag.
     * @param targetIndex
     */
    public ViewWeddingCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Wedding> lastShownList = model.getFilteredWeddingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WEDDING_DISPLAYED_INDEX);
        }

        Wedding targetWedding = lastShownList.get(targetIndex.getZeroBased());
        WeddingName targetWeddingName = targetWedding.getWeddingName();
        PersonInWeddingPredicate predicate = new PersonInWeddingPredicate(targetWedding);

        model.updateFilteredPersonList(predicate);
        model.setCurrentWeddingName(targetWeddingName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetWeddingName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewWeddingCommand)) {
            return false;
        }

        ViewWeddingCommand otherViewWeddingCommand = (ViewWeddingCommand) other;
        return targetIndex.equals(otherViewWeddingCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
