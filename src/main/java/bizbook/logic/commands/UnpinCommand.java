package bizbook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import bizbook.commons.core.index.Index;
import bizbook.commons.util.ToStringBuilder;
import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.person.Person;

/**
 * Unpins a person in pinned list.
 */
public class UnpinCommand extends Command {
    public static final String COMMAND_WORD = "unpin";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unpins the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";

    private final Index targetIndex;

    /**
     * Creates a Unpin Command to unpin the specified person.
     *
     * @param targetIndex of the person in the pinned person list.
     */
    public UnpinCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getPinnedPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnpin = lastShownList.get(targetIndex.getZeroBased());

        model.unpinPerson(personToUnpin);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS,
                Messages.formatShort(personToUnpin)), false, false);
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpinCommand)) {
            return false;
        }

        UnpinCommand otherUnpinCommand = (UnpinCommand) other;
        return targetIndex.equals(otherUnpinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
