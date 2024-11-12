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
 * Changes or adds the notes of an existing person in BizBook.
 */
public class AddNoteCommand extends Command {

    public static final String COMMAND_WORD = "addnote";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the note of the person identified "
            + "by the person index number used on the left display panel. "
            + "New note(letters and numbers) will be appended to the notes currently stored.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "n/NOTE\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "n/High profile client";

    public static final String MESSAGE_ADD_NOTE_SUCCESS = "Added note to Person: %1$s";
    public static final String DUPLICATE_MESSAGE_CONSTRAINTS = "There is already an existing note with this name.";

    private final Index index;
    private final Note note;


    /**
     * @param index of the person in the filtered person list to add a new note
     * @param note of the person to be added
     */
    public AddNoteCommand(Index index, Note note) {
        requireAllNonNull(index, note);

        this.index = index;
        this.note = note;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        assert model != null : "Model should not be null";

        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        ArrayList<Note> notesList = new ArrayList<>(personToEdit.getNotes());

        assert note != null : "Note to be added should not be null";

        if (notesList.contains(note)) {
            throw new CommandException(DUPLICATE_MESSAGE_CONSTRAINTS);
        }

        notesList.add(note);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(), notesList);

        assert editedPerson != null : "Edited person should be created and not be null";

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
        return String.format(MESSAGE_ADD_NOTE_SUCCESS, Messages.format(personToEdit));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddNoteCommand)) {
            return false;
        }

        AddNoteCommand otherAddNoteCommand = (AddNoteCommand) other;
        return note.equals(otherAddNoteCommand.note);
    }

}
