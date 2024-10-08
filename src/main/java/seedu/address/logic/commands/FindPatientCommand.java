package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all patients in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindPatientCommand extends Command {
    public static final String COMMAND_WORD = "find-patient";
    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "find-patient command not implemented yet";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;

    public FindPatientCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FindPatientCommand)) {
            return false;
        }

        FindPatientCommand otherFindPatientCommand = (FindPatientCommand) other;
        return predicate.equals(otherFindPatientCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
