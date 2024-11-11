package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes multiple persons identified using their displayed indices from the address book.
 */
public class MassDeleteCommand extends Command {

    public static final String COMMAND_WORD = "mass_delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the persons identified by the index numbers used in the displayed person list.\n"
            + "Parameters: INDEX1 INDEX2 ... INDEXN (must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 3 5";

    public static final String MESSAGE_DELETE_PERSONS_SUCCESS = "Contacts with indexes %1$s "
            + "have been successfully deleted.";
    public static final String MESSAGE_NO_VALID_IDS = "No valid contact indexes provided for deletion.";

    private final List<Index> targetIndices;
    private final List<String> invalidInputs;

    /**
     * Constructs a {@code MassDeleteCommand} with the specified target indices and invalid inputs.
     *
     * @param targetIndices List of indices representing the persons to be deleted.
     * @param invalidInputs List of inputs that could not be parsed as valid indices.
     */
    public MassDeleteCommand(List<Index> targetIndices, List<String> invalidInputs) {
        this.targetIndices = targetIndices;
        this.invalidInputs = invalidInputs;
    }

    /**
     * Executes the mass delete command, deleting the persons identified by the target indices
     * from the address book.
     *
     * @param model {@code Model} which the command should operate on.
     * @return Feedback message of the operation result for display.
     * @throws CommandException If no valid indices are provided or an error occurs during deletion.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        List<Person> personsToDelete = new ArrayList<>();

        for (Index index : targetIndices) {
            if (index.getZeroBased() < lastShownList.size()) {
                personsToDelete.add(lastShownList.get(index.getZeroBased()));
            }
        }

        if (personsToDelete.isEmpty()) {
            throw new CommandException(MESSAGE_NO_VALID_IDS);
        }

        for (Person person : personsToDelete) {
            model.deletePerson(person);
        }

        List<Integer> deletedIndices = targetIndices.stream()
                .map(Index::getOneBased)
                .collect(Collectors.toList());

        StringBuilder resultMessage = new StringBuilder();
        resultMessage.append(String.format(MESSAGE_DELETE_PERSONS_SUCCESS, deletedIndices));

        if (!invalidInputs.isEmpty()) {
            resultMessage.append("\nInvalid inputs: ").append(invalidInputs);
        }

        return new CommandResult(resultMessage.toString());
    }

    /**
     * Checks if another object is equal to this MassDeleteCommand.
     *
     * @param other The object to compare with.
     * @return True if the other object is equal to this command, otherwise false.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MassDeleteCommand)) {
            return false;
        }

        MassDeleteCommand otherMassDeleteCommand = (MassDeleteCommand) other;
        return targetIndices.equals(otherMassDeleteCommand.targetIndices)
                && invalidInputs.equals(otherMassDeleteCommand.invalidInputs);
    }

    /**
     * Returns a string representation of this MassDeleteCommand.
     *
     * @return A string representation of the command.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndices", targetIndices)
                .add("invalidInputs", invalidInputs)
                .toString();
    }
}
