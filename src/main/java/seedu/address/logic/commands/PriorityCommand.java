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
            + "Example: " + COMMAND_WORD + " /id 1 /level 2";

    private final int index;
    private final int priorityLevel;

    /**
     * Constructs a new {@code PriorityCommand} with the specified index and priority level.
     *
     * @param index the index of the person in the list to which the priority level will be assigned.
     * @param priorityLevel the priority level to be assigned to the person.
     *                      This should be a valid priority level as per the application's standards.
     */
    public PriorityCommand(int index, int priorityLevel) {
        this.index = index;
        this.priorityLevel = priorityLevel;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        try {
            Person personToEdit = model.getFilteredPersonList().get(index - 1);
            Person editedPerson = new Person(
                    personToEdit.getName(),
                    personToEdit.getPhone(),
                    personToEdit.getEmail(),
                    personToEdit.getAddress(),
                    personToEdit.getTags(),
                    new PriorityLevel(priorityLevel));

            model.setPerson(personToEdit, editedPerson);
            return new CommandResult(String.format("Priority level %d successfully set for %s", priorityLevel,
                    editedPerson.getName()));
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Invalid patient ID. Please enter a valid patient identifier.");
        } catch (IllegalArgumentException e) {
            throw new CommandException("Invalid priority level. Please enter 1/2/3 as the priority level.");
        }
    }
}
