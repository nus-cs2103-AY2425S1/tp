package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.keywordspredicate.WeddingContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose weddings contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindWeddingCommand extends FindCommand {

    public static final String MESSAGE_FIND_WEDDING_PERSON_SUCCESS = "Search for wedding(s) containing "
            + "\"%s\" was successful. Showing results:";

    public FindWeddingCommand(WeddingContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonListByWedding((WeddingContainsKeywordsPredicate) predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FIND_WEDDING_PERSON_SUCCESS, predicate.getDisplayString()));
        } else {
            return new CommandResult(MESSAGE_FIND_PERSON_UNSUCCESSFUL);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindWeddingCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }

}
