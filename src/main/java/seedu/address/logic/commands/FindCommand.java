package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TelContainsNumberPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) or find all persons whose phone number contain any of "
            + "the specified phone number and displays them as a list with index numbers.\n"
            + "Parameters: [KEYWORD] [MORE_KEYWORDS] [p/ PHONE] (either keyword or phone number must be present\n"
            + "Example: " + COMMAND_WORD + " alice bob\n"
            + "Example: " + COMMAND_WORD + " p/12345678";
    public static final String NUM_USAGE = COMMAND_WORD + " p/ [PHONE] \n"
            + "Phone number requires a non-empty number input\n"
            + "Example: " + COMMAND_WORD + " p/12345678";
    public static final String ARG_USAGE = COMMAND_WORD + " [KEYWORD] [MORE_KEYWORDS] [p/ PHONE] \n"
            + "Only one argument should be present (i.e. either keyword or phone number) \n"
            + "Example: " + COMMAND_WORD + " p/12345678\n"
            + "Example: " + COMMAND_WORD + " john";
    private final NameContainsKeywordsPredicate namePredicate;
    private final TelContainsNumberPredicate numberPredicate;

    /**
     * Initialise the two predicates
     * @param namePredicate
     * @param numberPredicate
     */
    public FindCommand(NameContainsKeywordsPredicate namePredicate, TelContainsNumberPredicate numberPredicate) {
        this.namePredicate = namePredicate;
        this.numberPredicate = numberPredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // if name predicate is not null
        if (namePredicate != null) {
            model.updateFilteredPersonList(namePredicate);
        } else {
            model.updateFilteredPersonList(numberPredicate);
        }
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return namePredicate.equals(otherFindCommand.namePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", namePredicate)
                .toString();
    }
}
