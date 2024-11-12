package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.keywordspredicate.EmailContainsKeywordsPredicate;
/**
 * Finds and lists all persons in address book whose email contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching, including numbers and symbols.
 */
public class FindEmailCommand extends FindCommand {

    /**
     * Command to filter contacts in WedLinker based on names using partial matching.
     * This command allows users to search for contacts by providing one or more keywords.
     * The search is case-insensitive and matches any part of the contact names.
     *
     * @param predicate Keywords used to filter contacts by name.
     */
    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList((EmailContainsKeywordsPredicate) predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(
                    Messages.MESSAGE_FIND_EMAIL_PERSON_SUCCESS, predicate.getDisplayString()
            ));
        } else {
            return new CommandResult(Messages.MESSAGE_FIND_PERSON_UNSUCCESSFUL);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindEmailCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }
}
