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
 * Pins a person in address book to a pinned list.
 */
public class PinCommand extends Command {
    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Pins the person identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_ALREADY_PINNED = "This person is already pinned";

    private final Index targetIndex;

    /**
     * Creates a PinCommand to pin the specified person.
     *
     * @param targetIndex of the person in the filtered person list to be pinned
     */
    public PinCommand(Index targetIndex) {
        assert targetIndex != null;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToPin = lastShownList.get(targetIndex.getZeroBased());

        if (model.isPinned(personToPin)) {
            throw new CommandException(MESSAGE_ALREADY_PINNED);
        }

        model.pinPerson(personToPin);
        CommandResult commandResult = new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS,
                Messages.formatShort(personToPin)), false, false);
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PinCommand)) {
            return false;
        }

        PinCommand otherPinCommand = (PinCommand) other;
        return targetIndex.equals(otherPinCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
