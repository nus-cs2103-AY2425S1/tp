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
            + "Parameters: id INDEX (must be a positive integer) /level LEVEL (must be 1, 2, or 3)\n"
            + "Parameters: deletelevel INDEX or INDEX /level LEVEL\n"
            + "Example: priority deletelevel 1\n"
            + "Example: " + COMMAND_WORD + " /id 1 /level 2";
    private final int index;
    private final int priorityLevel;
    private final boolean isReset;

    /**
     * Constructs a new {@code PriorityCommand} with the specified index and priority level.
     *
     * @param index the index of the person in the list to which the priority level will be assigned.
     * @param priorityLevel the priority level to be assigned to the person.
     *                      This should be a valid priority level as per the application's standards.
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
            Person editedPerson = new Person(
                    personToEdit.getName(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getAddress(),
                    personToEdit.getEmergencyContact(),
                    personToEdit.getTags(),
                    new PriorityLevel(isReset ? 3 : priorityLevel));

            model.setPerson(personToEdit, editedPerson);
            model.updateTasksForPerson(personToEdit, editedPerson);
            return new CommandResult(String.format("Priority level %d successfully set for %s",
                    priorityLevel, editedPerson.getName()));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Invalid patient ID. Please enter a valid patient identifier.");
        } catch (IllegalArgumentException e) {
            throw new CommandException("Invalid priority level. Please enter 1/2/3 as the priority level.");
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
