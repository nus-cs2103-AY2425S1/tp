package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Creates an UnRsvpCommand to toggle rsvp status
 */
public class UnRsvpCommand extends Command {
    public static final String UNRSVP_COMMAND_WORD = "unrsvp";
    public static final String MESSAGE_UNRSVP_SUCCESS = "Guest unRSVP'ed: ";
    public static final String MESSAGE_USAGE = "Untoggle RSVP for a guest\n";
    public static final String MESSAGE_INVALID_INDEX = "ERROR: Please enter a valid index (from 1 to ";
    private final Index index;

    /**
     * Creates a UnRsvpCommand to toggle rsvp status
     * @param index
     */
    public UnRsvpCommand(Index index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        int listSize = model.getFilteredPersonList().size();
        // Check if index is valid
        if (index.getZeroBased() >= listSize) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX + listSize + ")"));
        }

        Person personToRsvp = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToRsvp.getName(),
                personToRsvp.getPhone(),
                personToRsvp.getEmail(),
                false,
                personToRsvp.getTags()

        );

        model.setPerson(personToRsvp, updatedPerson);
        String message = MESSAGE_UNRSVP_SUCCESS;
        return new CommandResult(String.format(message + updatedPerson.getName().fullName));
    }
}
