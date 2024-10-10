package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NOTES;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds or updates the notes for a specific patient identified by an index in the patient listing.
 * The existing notes will be overwritten by the provided input.
 */
public class AddNotesCommand extends Command {

    public static final String COMMAND_WORD = "addNotes";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the patient notes of the patient identified "
            + "by the index number used in the last patient listing. "
            + "Existing notes will be overwritten by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NOTES + "[NOTES]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NOTES + "Patient is prone to falling.";
    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Notes: %2$s";

    private final Index index;
    private final String notes;

    /**
     * Constructs an AddNotesCommand.
     *
     * @param index Index of the patient whose notes are to be modified.
     * @param notes The new notes to replace the existing ones.
     */
    public AddNotesCommand(Index index, String notes) {
        requireAllNonNull(index, notes);
        this.index = index;
        this.notes = notes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), notes));
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
