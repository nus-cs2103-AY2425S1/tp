package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * Adds a note to a person.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "addnote";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a note to a person.\n"
            + "Parameters: "
            + PREFIX_NRIC + "PERSON_NRIC "
            + PREFIX_NOTE + "NOTE_TEXT\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S8484131E "
            + PREFIX_NOTE + "Note text";

    public static final String MESSAGE_SUCCESS = "Added note to %1$s: %2$s";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Person not found";
    public static final String MESSAGE_NOTE_TEXT_EMPTY = "Note text cannot be empty";

    private final Note note;
    private final Nric nric;

    /**
     * @param nric the nric of the person to add the note to
     * @param noteText the text of the note
     */
    public AddNoteCommand(Nric nric, Note noteText) {
        this.note = noteText;
        this.nric = nric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person = model.getPerson(nric);
        if (person == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        model.addNoteToPerson(note, person);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric, note.getContent()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // Check if the other object is an instance of AddNoteCommand
        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        // Cast and check if the fields are equal
        AddNoteCommand otherCommand = (AddNoteCommand) other;
        return nric.equals(otherCommand.nric)
                && note.equals(otherCommand.note);
    }

}
