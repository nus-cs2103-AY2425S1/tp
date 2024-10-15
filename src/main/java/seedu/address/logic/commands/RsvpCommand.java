package seedu.address.logic.commands;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

/**
 * Enable rsvp toggling for guests
 */
public class RsvpCommand extends Command {
    public static final String RSVP_COMMAND_WORD = "rsvp";
    public static final String UNRSVP_COMMAND_WORD = "unrsvp";


    public static final String MESSAGE_RSVP_SUCCESS = "Guest RSVP'ed: %1$s (%2$s)";
    public static final String MESSAGE_UNRSVP_SUCCESS = "Guest unRSVP'ed: %1$s (%2$s)";
    public static final String MESSAGE_USAGE = "Toggle /Untoggle RSVP for a guest\n";
    public static final String MESSAGE_INVALID_FORMAT = "ERROR: Format Error (Expected: rsvp/unrsvp {index})";
    public static final String MESSAGE_INVALID_INDEX = "ERROR: Please enter a valid index (from 1 to %1$d)";

    private final Index index;
    private final boolean rsvp;


    /**
     * Creates a RsvpCommand to toggle rsvp status
     * @param index
     * @param rsvp
     */
    public RsvpCommand(Index index, boolean rsvp) {
        this.index = index;
        this.rsvp = rsvp;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if index is valid
        if (index.getZeroBased() >= model.getFilteredPersonList().size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX, model.getFilteredPersonList().size()));
        }

        Person personToRsvp = model.getFilteredPersonList().get(index.getZeroBased());
        Person updatedPerson = new Person(
                personToRsvp.getName(),
                personToRsvp.getPhone(),
                personToRsvp.getEmail(),
                rsvp,
                personToRsvp.getTags()

        );

        model.setPerson(personToRsvp, updatedPerson);
        String message = rsvp ? MESSAGE_RSVP_SUCCESS : MESSAGE_UNRSVP_SUCCESS;
        return new CommandResult(String.format(message, updatedPerson));
    }

}
