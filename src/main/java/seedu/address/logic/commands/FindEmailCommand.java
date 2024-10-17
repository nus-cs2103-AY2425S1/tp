package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.EmailContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose email contains any of the argument keywords.
 * Keyword matching is case-insensitive and allows partial matching, including numbers and symbols.
 */
public class FindEmailCommand extends FindCommand {
    public static final String MESSAGE_FIND_EMAIL_PERSON_SUCCESS = "Search for email containing \"%s\" was successful. "
            + " Showing results:";

    private final EmailContainsKeywordsPredicate predicate;

    /**
     * Command to filter contacts in WedLinker based on names using partial matching.
     * This command allows users to search for contacts by providing one or more keywords.
     * The search is case-insensitive and matches any part of the contact names.
     *
     * @param predicate Keywords used to filter contacts by name.
     */
    public FindEmailCommand(EmailContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FIND_EMAIL_PERSON_SUCCESS, predicate.getDisplayString()));
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
        if (!(other instanceof FindEmailCommand)) {
            return false;
        }

        FindEmailCommand otherFindCommand = (FindEmailCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
