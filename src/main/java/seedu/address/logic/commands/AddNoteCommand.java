package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

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
    
    private final String noteText;
    private final Nric nric;
    
    public AddNoteCommand(Nric nric, String noteText) {
        this.noteText = noteText;
        this.nric = nric;
    }
    
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person person = model.getPerson(nric);
        if (person == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        if (noteText.isEmpty()) {
            throw new CommandException(MESSAGE_NOTE_TEXT_EMPTY);
        }

        Note note = new Note(noteText);
        
        model.addNoteToPerson(note, person);
        return new CommandResult(String.format(MESSAGE_SUCCESS, nric, noteText));
    }
    

}
