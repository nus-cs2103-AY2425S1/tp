package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Checks In/ Out a specific Client in the MATER address book.
 */
public class CheckClientCommand extends Command {

    public static final String COMMAND_WORD = "check-client";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Checks In/ Out the indexed Client.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_CHECK_IN_CLIENT_SUCCESS = "Checked In Client: %s (VRN: %s).";
    public static final String MESSAGE_CHECK_OUT_CLIENT_SUCCESS = "Checked Out Client: %s (VRN: %s).";
    public static final String MESSAGE_NO_CAR_TO_CHECK = "No Car associated to Client to Check In.";

    private final Index targetIndex;

    public CheckClientCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person clientToCheck = lastShownList.get(targetIndex.getZeroBased());

        if (clientToCheck.getCar() == null) {
            return new CommandResult(MESSAGE_NO_CAR_TO_CHECK);
        }

        clientToCheck.setServicing();
        model.setPerson(clientToCheck, clientToCheck); // Line required to update GUI.
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (clientToCheck.isServicing()) {
            return new CommandResult(String.format(MESSAGE_CHECK_IN_CLIENT_SUCCESS,
                    clientToCheck.getName()));
        }
        return new CommandResult(String.format(MESSAGE_CHECK_OUT_CLIENT_SUCCESS,
                clientToCheck.getName(), clientToCheck.getVrn()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CheckClientCommand)) {
            return false;
        }

        CheckClientCommand otherCheckClientCommand = (CheckClientCommand) other;
        return targetIndex.equals(otherCheckClientCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
