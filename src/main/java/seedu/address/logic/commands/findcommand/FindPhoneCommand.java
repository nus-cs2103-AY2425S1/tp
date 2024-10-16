package seedu.address.logic.commands.findcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
/**
 * Finds and lists all persons in address book whose phone number contains any of the argument keywords.
 * Keyword matching allows partial matching.
 */
public class FindPhoneCommand extends FindCommand {

    public static final String MESSAGE_FIND_PHONE_PERSON_SUCCESS = "Search for phone number containing \"%s\" "
            + " was successful. Showing results:";


    /**
     * Command to filter contacts in WedLinker based on phone numbers.
     * The search matches any parts of the phone numbers.
     *
     * @param predicate Keywords used to filter contacts by their phone number.
     */
    public FindPhoneCommand(PhoneContainsKeywordsPredicate predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList((PhoneContainsKeywordsPredicate) predicate);

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
        if (!(other instanceof FindPhoneCommand otherFindCommand)) {
            return false;
        }

        return predicate.equals(otherFindCommand.predicate);
    }


}
