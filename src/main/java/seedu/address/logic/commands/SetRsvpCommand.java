package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;

/**
 * Set RSVP status for guests
 */
public class SetRsvpCommand extends Command {
    public static final String COMMAND_WORD = "setrsvp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the RSVP status of a guest. "
            + "Parameters: INDEX (must be a positive integer) "
            + "STATUS (1 - Coming, 2 - Not Coming, 3 - Pending)\n";

    public static final String MESSAGE_SET_SUCCESS = "RSVP status updated for guest: ";
    public static final String MESSAGE_INVALID_INDEX = "ERROR: Please enter a valid index (from 1 to ";
    public static final String MESSAGE_INVALID_ACTION = "ERROR: Please enter a valid action (1, 2, or 3)";

    private final Index index;
    private final int action;

    /**
     * Creates a SetCommand to update the RSVP status of the specified guest.
     *
     * @param index  Index of the guest in the list.
     * @param action Action representing the new RSVP status (1 - Coming, 2 - Not Coming, 3 - Pending).
     */
    public SetRsvpCommand(Index index, int action) {
        this.index = index;
        this.action = action;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if index is valid
        int listSize = model.getFilteredPersonList().size();
        if (index.getZeroBased() >= listSize) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX + listSize + ")"));
        }

        // Get the person to update
        Person personToUpdate = model.getFilteredPersonList().get(index.getZeroBased());

        // Map action to the corresponding RSVP status
        RsvpStatus newRsvpStatus;
        switch (action) {
        case 1:
            newRsvpStatus = RsvpStatus.COMING;
            break;
        case 2:
            newRsvpStatus = RsvpStatus.NOT_COMING;
            break;
        case 3:
            newRsvpStatus = RsvpStatus.PENDING;
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_ACTION);
        }

        // Create a new person object with the updated RSVP status
        Person updatedPerson = new Person(
                personToUpdate.getName(),
                personToUpdate.getPhone(),
                personToUpdate.getEmail(),
                newRsvpStatus,
                personToUpdate.getTags()
        );

        // Update the model
        model.setPerson(personToUpdate, updatedPerson);
        String message = MESSAGE_SET_SUCCESS + updatedPerson.getName().fullName + " (" + newRsvpStatus + ")";
        return new CommandResult(message);
    }
}
