package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Unpins a person identified using its displayed index.
 */
public class PinCommand extends Command {

    public static final String COMMAND_WORD = "pin";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Pins the persons identified by the index numbers used in the displayed persons list.\n"
            + "Parameters: INDEX (one or more, all must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_PIN_PERSON_SUCCESS = "Pinned Person: %1$s";
    public static final String MESSAGE_PIN_PEOPLE_SUCCESS = "Pinned People: \n%1$s";

    private final List<Index> targetIndices;

    public PinCommand(List<Index> targetIndices) {
        this.targetIndices = targetIndices;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> peopleToPin = new ArrayList<>();
        for (Index index : targetIndices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person personToPin = lastShownList.get(index.getZeroBased());
            peopleToPin.add(personToPin);
        }

        assert peopleToPin.size() == targetIndices.size();

        List<String> resultMessages = new ArrayList<>();
        for (Person person : peopleToPin) {
            model.pinPerson(person);
            resultMessages.add(Messages.format(person));
        }

        assert resultMessages.size() == targetIndices.size();

        model.sortByPin();

        if (resultMessages.size() == 1) {
            return new CommandResult(String.format(MESSAGE_PIN_PERSON_SUCCESS, resultMessages.get(0)));
        } else {
            return new CommandResult(String.format(MESSAGE_PIN_PEOPLE_SUCCESS,
                    String.join("\n", resultMessages)));
        }
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
        return targetIndices.equals(otherPinCommand.targetIndices);
    }
}
