package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.FindPatientPredicate;

/**
 * Finds and lists all patients in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindPatientCommand extends Command {
    public static final String COMMAND_WORD = "find-patient";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose names contain "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "The order of the keywords matter and only patients matching all keywords will be returned "
            + "(i.e AND search). \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " Hans Bo\n"
            + "Note: KEYWORDS must only contain alphabets and spaces";

    private final FindPatientPredicate predicate;

    public FindPatientCommand(FindPatientPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
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
