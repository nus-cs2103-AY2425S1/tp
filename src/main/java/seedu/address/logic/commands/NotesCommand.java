package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIEW;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Name;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;
import seedu.address.ui.NotesWindow;

/**
 * Views, adds, edits, or deletes notes of a person identified using the person's name or index in the address book.
 */
public class NotesCommand extends Command {

    public static final String COMMAND_WORD = "notes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views, adds, edits, or deletes the notes of the person identified by their name or index.\n"
            + "Parameters: \n"
            + "View: [" + PREFIX_VIEW + "NAME] or [" + PREFIX_VIEW + "INDEX]\n"
            + "Add: [" + PREFIX_ADD + "NAME " + PREFIX_NOTES + "NOTES] or ["
            + PREFIX_ADD + "INDEX " + PREFIX_NOTES + "NOTES]\n"
            + "Edit: [" + PREFIX_EDIT + "NAME] or [" + PREFIX_EDIT + "INDEX]\n"
            + "Delete: [" + PREFIX_DELETE + "NAME] or [" + PREFIX_DELETE + "INDEX]\n"
            + "Example: \n"
            + COMMAND_WORD + " " + PREFIX_VIEW + "John Doe OR " + PREFIX_VIEW + "1\n"
            + COMMAND_WORD + " " + PREFIX_ADD + "John Doe " + PREFIX_NOTES + "Prefers email contact OR "
            + PREFIX_ADD + "1 " + PREFIX_NOTES + "Prefers email contact\n"
            + COMMAND_WORD + " " + PREFIX_EDIT + "John Doe OR " + PREFIX_EDIT + "1\n"
            + COMMAND_WORD + " " + PREFIX_DELETE + "John Doe OR " + PREFIX_DELETE + "1";

    public static final String MESSAGE_VIEW_NOTES_SUCCESS = "Notes for %1$s: \n%2$s";
    public static final String MESSAGE_DELETE_NOTES_SUCCESS = "Deleted notes for %1$s";
    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added notes for %1$s: \n%2$s";
    public static final String MESSAGE_EDIT_NOTES_SUCCESS = "Edited notes for %1$s: \n%2$s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "No person found with name: %1$s";

    private final Mode mode;
    private final Notes newNotes;
    private final Index targetIndex;
    private final Name targetName;

    /**
     * Represents the different modes of operation for the NotesCommand.
     * VIEW - displays the notes of a person
     * ADD - adds or updates the notes of a person
     * DELETE - removes the notes of a person
     * EDIT - uses a pop-up window to edit notes
     */
    public enum Mode {
        /**
         * Displays the notes of the specified person.
         */
        VIEW,

        /**
         * Adds or updates the notes of the specified person.
         */
        ADD,

        /**
         * Removes the notes of the specified person.
         */
        DELETE,

        /**
         * Edit the notes of the specified person using a pop-up window.
         */
        EDIT
    }

    /**
     * Creates command to view, edit, or delete notes using index.
     */
    public NotesCommand(Index targetIndex, Mode mode) {
        this(targetIndex, mode, null);
    }

    /**
     * Creates command to view, edit, or delete notes using name.
     */
    public NotesCommand(Name targetName, Mode mode) {
        this(targetName, mode, null);
    }

    /**
     * Creates command to add notes using index.
     */
    public NotesCommand(Index targetIndex, Mode mode, Notes notes) {
        requireNonNull(targetIndex);
        requireNonNull(mode);
        this.targetIndex = targetIndex;
        this.targetName = null;
        this.mode = mode;
        this.newNotes = notes;
    }

    /**
     * Creates command to add notes using name.
     */
    public NotesCommand(Name targetName, Mode mode, Notes notes) {
        requireNonNull(targetName);
        requireNonNull(mode);
        this.targetIndex = null;
        this.targetName = targetName;
        this.mode = mode;
        this.newNotes = notes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Person personToEdit;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personToEdit = lastShownList.get(targetIndex.getZeroBased());
        } else {
            personToEdit = null;
            for (Person person : lastShownList) {
                if (person.getName().equals(targetName)) {
                    personToEdit = person;
                    break;
                }
            }
            if (personToEdit == null) {
                throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, targetName.toString()));
            }
        }

        switch (mode) {
        case VIEW:
            return new CommandResult(String.format(MESSAGE_VIEW_NOTES_SUCCESS,
                    personToEdit.getName(), personToEdit.getNotes().toString()));

        case DELETE:
            Person personWithDeletedNotes = new Person(personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getAddress(), Notes.createEmpty(),
                    personToEdit.getTags(), personToEdit.getIncome(), personToEdit.getAge());
            model.setPerson(personToEdit, personWithDeletedNotes);
            return new CommandResult(String.format(MESSAGE_DELETE_NOTES_SUCCESS,
                    personToEdit.getName()));

        case ADD:
            Person personWithNewNotes = new Person(personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getAddress(), newNotes,
                    personToEdit.getTags(), personToEdit.getIncome(), personToEdit.getAge());
            model.setPerson(personToEdit, personWithNewNotes);
            return new CommandResult(String.format(MESSAGE_ADD_NOTES_SUCCESS,
                    personToEdit.getName(), newNotes.toString()));

        case EDIT:
            NotesWindow notesWindow = new NotesWindow();
            Notes editedNotes = new Notes(notesWindow.showNotesWindow(personToEdit));
            Person personWithEditedNotes = new Person(personToEdit.getName(), personToEdit.getPhone(),
                    personToEdit.getEmail(), personToEdit.getAddress(), editedNotes,
                    personToEdit.getTags(), personToEdit.getIncome(), personToEdit.getAge());
            model.setPerson(personToEdit, personWithEditedNotes);
            return new CommandResult(String.format(MESSAGE_EDIT_NOTES_SUCCESS,
                    personWithEditedNotes.getName(), personWithEditedNotes.getNotes().toString()));

        default:
            throw new AssertionError("Unknown mode: " + mode);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof NotesCommand)) {
            return false;
        }

        NotesCommand otherNotesCommand = (NotesCommand) other;

        // Check if both have same identifier type (both index or both name)
        if ((targetIndex == null) != (otherNotesCommand.targetIndex == null)) {
            return false;
        }

        // Compare based on identifier type
        boolean identifierEquals = targetIndex != null
                ? targetIndex.equals(otherNotesCommand.targetIndex)
                : targetName.equals(otherNotesCommand.targetName);

        return identifierEquals
                && mode == otherNotesCommand.mode
                && (newNotes == null ? otherNotesCommand.newNotes == null
                        : newNotes.equals(otherNotesCommand.newNotes));
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this)
                .add("mode", mode)
                .add("newNotes", newNotes);

        if (targetIndex != null) {
            builder.add("targetIndex", targetIndex);
        } else {
            builder.add("targetName", targetName);
        }

        return builder.toString();
    }
}
