package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.MedConContainsKeywordsPredicate;

/**
 * Finds and lists all patients in the address book who have medical conditions that contain any of the
 * argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindMedConCommand extends Command {
    public static final String COMMAND_WORD = "findMedCon";
    public static final String COMMAND_WORD_INSENSITIVE = "findmedcon";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients with medical conditions that "
            + "contains the specified keywords (case-insensitive) and displays them.\n"
            + "Parameters: KEYWORD [MORE KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " diabetes cancer";
    private final MedConContainsKeywordsPredicate predicate;
    public FindMedConCommand(MedConContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        //instanceof handles nulls
        if (!(other instanceof FindMedConCommand)) {
            return false;
        }

        FindMedConCommand otherFindMedConCommand = (FindMedConCommand) other;
        return predicate.equals(otherFindMedConCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
