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
import javafx.beans.property.ObjectProperty;

/**
 * Deletes note of an existing person in the address book.
 */
public class DeleteNoteCommand extends Command {

    public static final String COMMAND_WORD = "deletenote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the note of the person identified "
            + "by the index number used in the person listing and the index of the note.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "i/NOTE_INDEX\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "i/1";

    public static final String MESSAGE_DELETE_NOTE_SUCCESS = "Deleted note of Person: %1$s";

    /**
     * Index of the person whose note is to be deleted
     */
    private final Index index;

    /**
     * Index of the note be deleted
     */
    private final Index noteIndex;

    /**
     * @param index of the person in the filtered person list to edit the notes
     * @param noteIndex of the note to be deleted
     */
    public DeleteNoteCommand(Index index, Index noteIndex) {
        requireAllNonNull(index, noteIndex);

        this.index = index;
        this.noteIndex = noteIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        assert model != null : "Model should not be null";

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        List<Note> notesList = new ArrayList<>(personToEdit.getNotes());

        assert notesList != null : "Notes list should not be null";
        assert noteIndex != null : "Note index should not be null";

        // if there are no notes with this index
        if (noteIndex.getOneBased() > notesList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_NOTE_INDEX);
        }

        // remove the specified note
        Note noteToRemove = notesList.get(noteIndex.getZeroBased());
        notesList.remove(noteToRemove);

        ArrayList<Note> updatedNotesList = new ArrayList<>(notesList);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), updatedNotesList);

        assert editedPerson != null : "Edited person should not be null";

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        ObjectProperty<Person> focusedPerson = model.getFocusedPerson();
        focusedPerson.set(editedPerson);

        return new CommandResult(String.format(generateSuccessMessage(editedPerson), index.getOneBased()),
                false, false);
    }

    /**
     * Generates a command execution success message
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_DELETE_NOTE_SUCCESS, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteNoteCommand)) {
            return false;
        }

        DeleteNoteCommand otherDeleteNoteCommand = (DeleteNoteCommand) other;
        return index.equals(otherDeleteNoteCommand.index) && noteIndex.equals(otherDeleteNoteCommand.noteIndex);
    }
}
