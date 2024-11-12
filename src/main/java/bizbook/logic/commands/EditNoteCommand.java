package bizbook.logic.commands;

import static bizbook.commons.util.CollectionUtil.requireAllNonNull;
import static bizbook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import bizbook.commons.core.index.Index;
import bizbook.logic.Messages;
import bizbook.logic.commands.exceptions.CommandException;
import bizbook.model.Model;
import bizbook.model.person.Note;
import bizbook.model.person.Person;

/**
 * Edits a note of an existing person in the address book.
 */
public class EditNoteCommand extends Command {

    public static final String COMMAND_WORD = "editnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the note of the person identified "
            + "by the person index number used on the left display panel. "
            + "The note will replace the currently stored note at the specified index.\n"
            + "Parameters: INDEX i/NOTE_INDEX n/NOTE\n"
            + "Example: " + COMMAND_WORD + " 1 i/1 n/High profile client";

    public static final String MESSAGE_EDIT_NOTE_SUCCESS = "Edit note of Person: %1$s";
    public static final String DUPLICATE_MESSAGE_CONSTRAINTS = "There is already an existing note with this name.";

    private final Index personIndex;
    private final Index noteIndex;
    private final Note note;

    /**
     * @param personIndex of the person in the filtered person list to edit the note
     * @param noteIndex   of the person in the filtered person list to edit the note
     * @param note        of the person to be updated to
     */
    public EditNoteCommand(Index personIndex, Index noteIndex, Note note) {
        requireAllNonNull(personIndex, note, noteIndex);

        this.personIndex = personIndex;
        this.noteIndex = noteIndex;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        assert model != null : "Model should not be null";

        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(personIndex.getZeroBased());

        ArrayList<Note> notesList = new ArrayList<>(personToEdit.getNotes());

        assert notesList != null : "Notes list should not be null";
        assert note != null : "Note to be edited to should not be null";

        if (notesList.contains(note)) {
            throw new CommandException(DUPLICATE_MESSAGE_CONSTRAINTS);
        } else if (noteIndex.getZeroBased() >= notesList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_INDEX);
        }

        Person editedPerson = updateNote(personToEdit, notesList);

        assert editedPerson != null : "Edited person should not be null";

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
        return String.format(MESSAGE_EDIT_NOTE_SUCCESS, Messages.format(personToEdit));
    }

    /**
     * Updates the notes of the given person with the given note
     * {@code personToEdit, notesToEdit}.
     */
    private Person updateNote(Person personToEdit, ArrayList<Note> notesToEdit) {

        List<Note> notesList = new ArrayList<>(notesToEdit);
        notesList.set(noteIndex.getZeroBased(), note);
        ArrayList<Note> editedNotes = new ArrayList<>(notesList);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), editedNotes);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditNoteCommand otherEditNotesCommand)) {
            return false;
        }

        return personIndex.equals(otherEditNotesCommand.personIndex)
                && noteIndex.equals(otherEditNotesCommand.noteIndex)
                && note.equals(otherEditNotesCommand.note);
    }

}
