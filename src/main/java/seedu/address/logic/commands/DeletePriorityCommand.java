package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PriorityLevel;

/**
 * Deletes the priority level of an existing person in the address book.
 */
public class DeletePriorityCommand extends Command {

    public static final String COMMAND_WORD = "deletelevel";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Resets the priority level for a patient identified by "
            + "the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final int index;

    /**
     * Constructs a new {@code DeletePriorityCommand} with the specified index.
     *
     * @param index the index of the person in the list whose priority level will be reset.
     */
    public DeletePriorityCommand(int index) {
        this.index = index;
    }

    /**
     * Executes the deletion of the priority level on the specified person in the address book.
     *
     * @param model the model in which the person will be updated.
     * @return a command result that includes a success message.
     * @throws CommandException if the index provided is out of bounds.
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
                    new PriorityLevel(3)); // reset to default priority level

            model.updateTasksForPerson(personToEdit, editedPerson);
            model.setPerson(personToEdit, editedPerson);
            return new CommandResult(String.format("Priority level reset to default for %s",
                    editedPerson.getName()));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Invalid patient ID. Please enter a valid patient identifier.");
        }
    }

    /**
     * Compares this object with another for equality, based on index.
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
        DeletePriorityCommand that = (DeletePriorityCommand) other;
        return index == that.index;
    }
}

