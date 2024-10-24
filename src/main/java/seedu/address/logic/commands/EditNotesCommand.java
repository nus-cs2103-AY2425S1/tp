package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
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
 * Edits a note of an existing person in the address book.
 */
public class EditNotesCommand extends Command {

    public static final String COMMAND_WORD = "editnotes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edit the note of the person identified "
            + "by the person index number used on the left display panel. "
            + "The note will replace the currently stored note at the specified index.\n"
            + "Parameters: INDEX i/[NOTE_INDEX] n/[NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 i/1 n/High profile client.";

    public static final String MESSAGE_EDIT_NOTES_SUCCESS = "Edit note of Person: %1$s";

    private final Index personIndex;
    private final Index noteIndex;
    private final Note note;

    /**
     * @param personIndex of the person in the filtered person list to edit the notes
     * @param noteIndex   of the person in the filtered person list to edit the notes
     * @param note        of the person to be updated to
     */
    public EditNotesCommand(Index personIndex, Index noteIndex, Note note) {
        requireAllNonNull(personIndex, note, noteIndex);

        this.personIndex = personIndex;
        this.noteIndex = noteIndex;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        // Update notes with new note
        Set<Note> notesToEdit = new LinkedHashSet<>(personToEdit.getNotes());

        if (notesToEdit.contains(note)) {
            throw new CommandException(Note.DUPLICATE_MESSAGE_CONSTRAINTS);
        }
        if (noteIndex.getZeroBased() >= notesToEdit.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTED_INDEX);
        }
        Person editedPerson = updateNote(personToEdit, notesToEdit);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        model.getFocusedPerson().set(editedPerson);

        return new CommandResult(String.format(generateSuccessMessage(editedPerson), personIndex.getOneBased()),
                false, false);
    }

    /**
     * Generates a command execution success message the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_EDIT_NOTES_SUCCESS, Messages.format(personToEdit));
    }

    /**
     * Updates the notes of the given person with the given notes
     * {@code personToEdit, notesToEdit}.
     */
    private Person updateNote(Person personToEdit, Set<Note> notesToEdit) {
        // Convert Set to List for indexed access
        List<Note> notesList = new ArrayList<>(notesToEdit);
        notesList.get(noteIndex.getZeroBased()).setNote(note.getNote());
        Set<Note> editedNotes = new LinkedHashSet<>(notesList);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), editedNotes);
    }

}
