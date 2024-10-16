package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Enable rsvp toggling for guests
 */
public class RsvpCommand extends Command {
    public static final String RSVP_COMMAND_WORD = "rsvp";

    public static final String MESSAGE_RSVP_SUCCESS = "Guest RSVP'ed: ";

    public static final String MESSAGE_USAGE = "Toggle RSVP for a guest\n";
    public static final String MESSAGE_INVALID_INDEX = "ERROR: Please enter a valid index (from 1 to ";
    private final Index index;

    /**
     * Creates a RsvpCommand to toggle rsvp status
     * @param index
     */
    public RsvpCommand(Index index) {
        this.index = index;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if index is valid
        int listSize = model.getFilteredPersonList().size();
        if (index.getZeroBased() >= listSize) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX + listSize + ")"));
        }

        Person personToRsvp = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToRsvp.getName(),
                personToRsvp.getPhone(),
                personToRsvp.getEmail(),
                true,
                personToRsvp.getTags()

        );

        model.setPerson(personToRsvp, updatedPerson);
        String message = MESSAGE_RSVP_SUCCESS;
        return new CommandResult(String.format(message + updatedPerson.getName().fullName));
    }
}
