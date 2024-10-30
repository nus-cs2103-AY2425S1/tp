package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityLevel;

/**
 * Changes the priority level of an existing person in the address book.
 */
public class PriorityCommand extends Command {

    public static final String COMMAND_WORD = "priority";
    public static final String MESSAGE_USAGE =
            COMMAND_WORD + ": Sets the priority level for a patient identified by "
            + "the index number used in the displayed person list.\n"
            + "Parameters: INDEX l/LEVEL (LEVEL must be 1, 2, or 3 or 'reset' for default level)\n"
            + "Example: priority 1 l/2\n"
            + "Example: priority 2 l/reset";

    public static final String MESSAGE_SUCCESS_RESET = "Priority level reset to default for %s";
    public static final String MESSAGE_SUCCESS_SET = "Priority level %d successfully set for %s";
    public static final String MESSAGE_INVALID_PATIENT_ID =
            "Invalid patient ID. Please enter a valid patient identifier.";
    public static final String MESSAGE_INVALID_PRIORITY_LEVEL =
            "Invalid priority level. Please enter 1, 2, or 3 as the priority level.";

    private static final int DEFAULT_PRIORITY_LEVEL = 3;

    private final int index;
    private final int priorityLevel;
    private final boolean isReset;

    /**
     * Constructs a new {@code PriorityCommand} with the specified index and priority level.
     *
     * @param index the index of the person in the list to which the priority level will be assigned.
     * @param priorityLevel the priority level to be assigned to the person.
     *                      This should be a valid priority level as per the application's standards.
     * @param isReset whether the priority level is to be reset to the default level.
     */
    public PriorityCommand(int index, int priorityLevel, boolean isReset) {
        this.index = index;
        this.priorityLevel = priorityLevel;
        this.isReset = isReset;
    }

    /**
     * Executes the priority level update on the specified person in the address book.
     *
     * @param model the model in which the person will be updated.
     * @return a command result that includes a success message.
     * @throws CommandException if the index provided is out of bounds or the priority level is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Person personToEdit = model.getFilteredPersonList().get(index - 1);
            int updatedPriorityLevel = isReset ? DEFAULT_PRIORITY_LEVEL : priorityLevel;

            Person editedPerson = new Person(
                    personToEdit.getName(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getAddress(),
                    personToEdit.getEmergencyContact(),
                    personToEdit.getTags(),
                    new PriorityLevel(updatedPriorityLevel));

            model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            model.setPerson(personToEdit, editedPerson);
            model.updateTasksForPerson(personToEdit, editedPerson);

            // Dynamic success message based on reset or custom priority level
            String successMessage = isReset
                    ? String.format(MESSAGE_SUCCESS_RESET, editedPerson.getName())
                    : String.format(MESSAGE_SUCCESS_SET, priorityLevel, editedPerson.getName());
            return new CommandResult(successMessage);

        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(MESSAGE_INVALID_PATIENT_ID);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_INVALID_PRIORITY_LEVEL);
        }
    }

    /**
     * Compares this object with another for equality, based on index and priority level.
     *
     * @param other the object to compare this command against.
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
