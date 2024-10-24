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
 * Pins contact at a given index to the top of the list
 */
public class PinContactCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins contact at a particular  "
            + "index to the top of the list.\n"
            + "Parameters: INDEX \n"
            + "Example: " + COMMAND_WORD + "5";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";

    private static Logger logger = LogsCenter.getLogger(PinContactCommand.class);

    private final Index targetIndex;

    /**
     * Creates a PinContactCommand to pin contact at {@code Index}
     * to the top of the list
     *
     * @param targetIndex the contact at this index has to be pinned
     * */
    public PinContactCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            logger.warning("Attempted to pin a contact with an invalid index" + targetIndex);
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPin = lastShownList.get(targetIndex.getZeroBased());
        model.pinPerson(personToPin);
        logger.info("Pinned person info: " + Messages.format(personToPin));
        return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, Messages.format(personToPin)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinContactCommand)) {
            return false;
        }

        PinContactCommand otherFindTagCommand = (PinContactCommand) other;
        return targetIndex.equals(otherFindTagCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
