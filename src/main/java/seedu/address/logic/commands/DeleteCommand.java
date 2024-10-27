package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NricMatchesPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using their NRIC from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient identified by their NRIC used in the displayed patient list.\n"
            + "Parameter: NRIC (must be a valid NRIC in the system)\n"
            + "Example: " + COMMAND_WORD + " T1234567A";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Patient: %1$s";
    private static final Logger logger = LogsCenter.getLogger(DeleteCommand.class);
    private final NricMatchesPredicate predicate;

    public DeleteCommand(NricMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = model.fetchPersonIfPresent(predicate)
                .orElseThrow(() -> {
                    logger.warning("Unable to find the patient to delete with the parsed NRIC");
                    return new CommandException(Messages.MESSAGE_PERSON_NRIC_NOT_FOUND);
                });
        model.deletePerson(personToDelete);
        logger.info("Successfully deleted patient with the parsed NRIC: " + personToDelete.getNric());
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return predicate.equals(otherDeleteCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
