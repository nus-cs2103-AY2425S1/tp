package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;

/**
 * Deletes Notes from the person identified using it's displayed index from the address book.
 */
public class DeleteNotesCommand extends Command {

    public static final String COMMAND_WORD = "delNotes";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient notes from the person identified by the index number used in the "
            + "displayed person list if they have notes.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_NOTES_SUCCESS = "Removed notes from Patient: %1$s";
    public static final String MESSAGE_NO_NOTES = "This person does not have any notes";

    private final Index index;
    private final Notes emptyNotes;

    /**
     * Creates an DeleteNotesCommand to delete notes from the specified {@code Person}
     */
    public DeleteNotesCommand(Index targetIndex) {
        this.index = targetIndex;
        this.emptyNotes = new Notes("");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        if (personToEdit.getNotes().equals(emptyNotes)) {
            throw new CommandException(MESSAGE_NO_NOTES);
        }

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getId(), personToEdit.getWard(),
                personToEdit.getDiagnosis(), personToEdit.getMedication(), emptyNotes);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_NOTES_SUCCESS, Messages.format(editedPerson)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteNotesCommand)) {
            return false;
        }

        DeleteNotesCommand otherDeleteNotesCommand = (DeleteNotesCommand) other;
        return index.equals(otherDeleteNotesCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", index)
                .toString();
    }
}
