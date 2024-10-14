package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book whose phone number contains any of the argument keywords.
 * Keyword matching allows partial matching.
 */
public class FindPhoneCommand extends FindCommand {

    public static final String MESSAGE_FIND_PHONE_PERSON_SUCCESS = "Search for phone number containing \"%s\" "
            + " was successful. Showing results:";

    private final PhoneContainsKeywordsPredicate predicate;

    /**
     * Command to filter contacts in WedLinker based on phone numbers.
     * The search matches any parts of the phone numbers.
     *
     * @param predicate Keywords used to filter contacts by their phone number.
     */
    public FindPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);

        if (!model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_FIND_PHONE_PERSON_SUCCESS, predicate.getDisplayString()));
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
        if (!(other instanceof FindPhoneCommand)) {
            return false;
        }

        FindPhoneCommand otherFindCommand = (FindPhoneCommand) other;
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }


}
