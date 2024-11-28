package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unpins contact at a given index to the top of the list
 */
public class UnpinContactCommand extends Command {

    public static final String COMMAND_WORD = "unpin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unpins contact at a particular  "
            + "index to the top of the list.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + " 5";

    public static final String MESSAGE_UNPIN_PERSON_SUCCESS = "Unpinned Person: %1$s";

    public static final String MESSAGE_UNPIN_PERSON_ALREADY_UNPINNED = "This contact is already unpinned.";

    private static Logger logger = LogsCenter.getLogger(UnpinContactCommand.class);

    private final Index targetIndex;

    /**
     * Creates a UnpinContactCommand to unpin contact at {@code Index}
     *
     * @param targetIndex the contact at this index has to be unpinned
     * */
    public UnpinContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Attempted to unpin a contact with an invalid index" + targetIndex);
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnpin = lastShownList.get(targetIndex.getZeroBased());
        if (!personToUnpin.isPinned()) {
            logger.warning("Attempted to unpin a contact that is already unpinned: " + targetIndex);
            throw new CommandException(MESSAGE_UNPIN_PERSON_ALREADY_UNPINNED);
        }

        model.unpinPerson(personToUnpin);
        logger.info("Unpinned person info: " + Messages.format(personToUnpin));
        return new CommandResult(String.format(MESSAGE_UNPIN_PERSON_SUCCESS, Messages.format(personToUnpin)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnpinContactCommand)) {
            return false;
        }

        UnpinContactCommand otherUnpinContactCommand = (UnpinContactCommand) other;
        return targetIndex.equals(otherUnpinContactCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
