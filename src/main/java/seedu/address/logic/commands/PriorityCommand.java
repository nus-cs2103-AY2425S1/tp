package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityLevel;

/**
 * Changes the priority level of an existing person in the address book.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the priority level for a patient identified by "
            + "the index number used in the displayed person list.\n"
            + "Parameters: INDEX l/LEVEL (LEVEL must be 1, 2, or 3 or 'reset' for default level)\n"
            + "Example: priority 1 l/2\n"
            + "Example: priority 2 l/reset";

    public static final String MESSAGE_SUCCESS_RESET = "Priority level reset to default for %s";
    public static final String MESSAGE_SUCCESS_SET = "Priority level %d successfully set for %s";
    public static final String MESSAGE_INVALID_PATIENT_ID =
            "Invalid patient ID. Please enter a valid patient identifier.";
    private static final int DEFAULT_PRIORITY_LEVEL = 3;

    private final int index;
    private final int priorityLevel;
    private final boolean isReset;

    /**
     * Constructs a new {@code PriorityCommand} to update the specified person's priority level.
     *
     * @param index the index of the person to update in the list.
     * @param priorityLevel the priority level to assign.
     * @param isReset true if the priority level should be reset to the default level.
     */
    public PriorityCommand(int index, int priorityLevel, boolean isReset) {
        this.index = index;
        this.priorityLevel = priorityLevel;
        this.isReset = isReset;
    }

    /**
     * Executes the priority level update on the specified person in the address book.
     *
     * @param model the model containing the person data.
     * @return a CommandResult with a success message.
     * @throws CommandException if the index is out of bounds or priority level is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        Person personToEdit = getPersonToEdit(model);
        Person editedPerson = createEditedPerson(personToEdit);

        model.updatePersonAndTasks(personToEdit, editedPerson);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private Person getPersonToEdit(Model model) throws CommandException {
        try {
            return model.getFilteredPersonList().get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_ID);
        }
    }

    private Person createEditedPerson(Person personToEdit) {
        int updatedPriorityLevel = isReset ? DEFAULT_PRIORITY_LEVEL : priorityLevel;
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getEmergencyContact(),
                personToEdit.getTags(),
                new PriorityLevel(updatedPriorityLevel)
        );
    }

    private String generateSuccessMessage(Person editedPerson) {
        return isReset
                ? String.format(MESSAGE_SUCCESS_RESET, editedPerson.getName())
                : String.format(MESSAGE_SUCCESS_SET, priorityLevel, editedPerson.getName());
    }

    /**
     * Checks if two PriorityCommand objects are equal based on their data fields.
     *
     * @param other the other object to compare against.
     * @return true if both objects have the same data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        PriorityCommand that = (PriorityCommand) other;
        return index == that.index && priorityLevel == that.priorityLevel && isReset == that.isReset;
    }
}
