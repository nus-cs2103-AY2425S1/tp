package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public abstract class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the employee (e) or potential hire (ph) type "
            + "identified by the index number used in the employee list.\n"
            + "Parameters: TYPE INDEX\n"
            + "Employee Example: " + COMMAND_WORD + " e 1\n"
            + "Potential Hire Example: " + COMMAND_WORD + " ph 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted %1$s: %2$s";

    public static final String MESSAGE_DELETE_PERSON_WRONG_TYPE =
            "The selected index is not an %1$s. You've likely selected a person of another type.";

    protected final Index targetIndex;

    /**
     * @param targetIndex of the person in the displayed list to delete.
     */
    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }
}
