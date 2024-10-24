package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;


/**
 * Changes or adds the notes of an existing person in BizBook.
 */
public class AddNotesCommand extends Command {

    public static final String COMMAND_WORD = "addnotes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Add the note of the person identified "
            + "by the person index number used on the left display panel. "
            + "New note(letters and numbers) will be appended to the notes currently stored.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "n/ [NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "n/ High profile client.";

    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added notes to Person: %1$s";

    private final Index index;
    private final Note note;


    /**
     * @param index of the person in the filtered person list to add a new note
     * @param note of the person to be added
     */
    public AddNotesCommand(Index index, Note note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        // Update notes with new note
        Set<Note> notesToEdit = new LinkedHashSet<>(personToEdit.getNotes());

        if (notesToEdit.contains(note)) {
            throw new CommandException(Note.DUPLICATE_MESSAGE_CONSTRAINTS);
        }

        notesToEdit.add(note);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), notesToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        model.getFocusedPerson().set(editedPerson);

        return new CommandResult(String.format(generateSuccessMessage(editedPerson), index.getOneBased()),
                false, false);
    }

    /**
     * Generates a command execution success message
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_ADD_NOTES_SUCCESS, Messages.format(personToEdit));
    }

}
