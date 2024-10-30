package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Notes;
import seedu.address.model.person.Person;

/**
 * Adds or updates the notes for a specific patient identified by an index in the patient listing.
 * The existing notes will be overwritten by the provided input.
 */
public class AddNotesCommand extends Command {

    public static final String COMMAND_WORD = "addnotes";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the patient notes of the patient identified "
            + "by the index number used in the last patient listing. "
            + "Existing notes will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NOTES + "[NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NOTES + "Patient is prone to falling.";
    public static final String MESSAGE_ADD_NOTES_SUCCESS = "Added notes to Patient: %1$s";
    public static final String MESSAGE_DELETE_NOTES_SUCCESS = "Removed notes from Patient: %1$s";

    private final Index index;
    private final Notes notes;

    /**
     * Constructs an AddNotesCommand.
     *
     * @param index Index of the patient whose notes are to be modified.
     * @param notes The new notes to replace the existing ones.
     */
    public AddNotesCommand(Index index, Notes notes) {
        requireAllNonNull(index, notes);
        this.index = index;
        this.notes = notes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getId(), personToEdit.getWard(),
                personToEdit.getDiagnosis(), personToEdit.getMedication(), notes);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !notes.value.isEmpty() ? MESSAGE_ADD_NOTES_SUCCESS : MESSAGE_DELETE_NOTES_SUCCESS;
        return String.format(message, Messages.format(personToEdit));
    }



    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddNotesCommand)) {
            return false;
        }
        // state check
        AddNotesCommand e = (AddNotesCommand) other;
        return index.equals(e.index)
                && notes.equals(e.notes);
    }
}
