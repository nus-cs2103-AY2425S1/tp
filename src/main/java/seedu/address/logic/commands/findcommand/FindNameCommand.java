package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching.
 */
public class FindNameCommand extends FindCommand {

    public static final String MESSAGE_FIND_NAME_PERSON_SUCCESS = "Search for name containing \"%s\" was successful. "
            + " Showing results:";

    /**
     * Command to filter contacts in WedLinker based on names using partial matching.
     * This command allows users to search for contacts by providing one or more keywords.
     * The search is case-insensitive and matches any part of the contact names.
     *
     * @param predicate Keywords used to filter contacts by name.
     */
    public FindNameCommand(NameContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList((NameContainsKeywordsPredicate) predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FIND_NAME_PERSON_SUCCESS, predicate.getDisplayString()));
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
        if (!(other instanceof FindNameCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }
}
