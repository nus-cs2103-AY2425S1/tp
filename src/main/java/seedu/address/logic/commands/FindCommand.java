package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.owner.OwnerNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds owners or pets in the address book whose "
            + "relevant fields contain the specified keywords (case-insensitive) and displays them as a list with "
            + "index numbers.\n"
            + "To find owners: find owner KEYWORD [MORE_KEYWORDS]...\n"
            + "To find pets: find pet KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: find owner bobby, find pet golden retriever";


    private final NameContainsKeywordsPredicate predicate;
    private final OwnerNameContainsKeywordsPredicate ownerPredicate;

    /* field for PetContainsKeywordsPredicate */
    private final boolean isOwnerSearch; // indicates if second word of argument is OWNER or PET

    public FindCommand(NameContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
        ownerPredicate = null;
        isOwnerSearch = false;
    }

    /**
     * Constructor for finding owners.
     * @param ownerPredicate Owner predicate.
     */
    public FindCommand(OwnerNameContainsKeywordsPredicate ownerPredicate) {
        predicate = null;
        this.ownerPredicate = ownerPredicate;
        isOwnerSearch = true;
    }

    /* Constructor for finding pets here */

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (isOwnerSearch) {
            model.updateFilteredOwnerList(ownerPredicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredOwnerList().size()));
        } else {
            /* PET search here */
            model.updateFilteredPersonList(predicate);
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
        }
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
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
